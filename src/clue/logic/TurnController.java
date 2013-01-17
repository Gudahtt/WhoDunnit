package clue.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import clue.api.EventBroker;
import clue.model.Card;
import clue.model.Card.Type;
import clue.model.ComputerPlayer;
import clue.model.Difficulty;
import clue.model.GNode;
import clue.model.Gameboard;
import clue.model.HallwayNode;
import clue.model.Notebook;
import clue.model.Path;
import clue.model.Player;
import clue.model.Position;
import clue.model.RoomNode;
import clue.model.SessionData;
import clue.model.Turn;

public class TurnController {
	private Random rand;
	private SessionData data;
	private EventBroker broker;
	private Gameboard board;
	private EasyAI easyAI;
	private MediumAI mediumAI;
	private HardAI hardAI;
	private static final int SUGGESTION_PAUSE = 1000;
	private static final int END_SUGGESTION_PAUSE = 1000;
	private static final int ACCUSATION_PAUSE = 1000;
	private static final int END_TURN_PAUSE = 1000;

	public TurnController(SessionData data, EventBroker broker, Gameboard board) {
		this.data = data;
		this.broker = broker;
		this.rand = new Random();
		this.easyAI = new EasyAI(this);
		this.mediumAI = new MediumAI(this);
		this.hardAI = new HardAI(this);
		this.board = board;

		broker.registerEvent("StartTurn");
		broker.registerEvent("EndTurn");
		broker.registerEvent("Disprove");
		broker.registerEvent("UpdateTokens");
		broker.registerEvent("UpdateDie");
		broker.registerEvent("UpdateSuggestion");
		broker.registerEvent("UpdateRetractMovement");
		broker.registerEvent("UpdatePaths");
		broker.registerEvent("UpdateSaveButton");
	}

	public void startTurn() {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();

		String startEvent = "It is " + currentPlayer.getCharacter().toString()
				+ "'s turn.\n";
		data.setEventLog(data.getEventLog().concat(startEvent));
		broker.notify("EventLog");

		// check for if player was moved to room by suggestion
		if (currentPlayer.wasMoved()) {
			currentTurn.setCanMakeSuggestion(true);
			broker.notify("UpdateSuggestion");
		}
		currentTurn.setCanRollDie(true);

		// Highlighting room accessible by secret passage, if any
		highlightSecretPassage();

		broker.notify("StartTurn");
		broker.notify("UpdateSaveButton");

		// see if the current player is an AI and if so start it's turn
		if (!currentPlayer.isHuman()) {
			switch (((ComputerPlayer) currentPlayer).getDifficulty()) {
			case EASY:
				easyAI.takeTurn();
				break;
			case MEDIUM:
				mediumAI.takeTurn();
				break;
			case HARD:
				hardAI.takeTurn();
				break;
			}
		}

	}

	public void endTurn() {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();

		ArrayList<Player> allPlayers = data.getPlayers();

		String endTurn = currentPlayer.getCharacter().toString()
				+ "'s turn has ended.\n";
		data.setEventLog(data.getEventLog().concat(endTurn));
		broker.notify("EventLog");
		
		if (!currentTurn.getPlayer().isHuman()) {
			try {
				Thread.sleep(END_TURN_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// if wasMoved=true, it now no longer applies
		if (currentPlayer.wasMoved())
			currentPlayer.setMoved(false);

		int i = allPlayers.indexOf(currentPlayer) + 1;
		i %= allPlayers.size();

		for (; i != allPlayers.indexOf(currentPlayer); ++i, i %= allPlayers
				.size()) {
			if (allPlayers.get(i).getAlive()) {
				currentPlayer = allPlayers.get(i);
				break;
			}
		}

		currentTurn.clear();
		currentTurn.setPlayer(currentPlayer);

		broker.notify("EndTurn");
		broker.notify("UpdateSaveButton");

		// if we could slow down the AI this would handle starting the next AI
		// turn

		if (!data.getCurrentTurn().getPlayer().isHuman()) {
			this.startTurn();
		}
	}

	private void highlightSecretPassage() {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();
		GNode currentNode = currentPlayer.getLocation();

		if (currentNode.isRoom()) {
			for (GNode n : currentNode.getEdges()) {
				if (n.isRoom()) {
					Path path = new Path(currentNode);
					path.add(n);
					currentTurn.getSelectablePaths().add(path);
					broker.notify("UpdatePaths");
					break;
				}
			}

		}
	}

	public void rollDie() {
		Turn currentTurn = data.getCurrentTurn();
		if (!currentTurn.canRollDie()) {
			System.out.println("Error: Die has already been rolled");
			return;
		}

		currentTurn.setCanRollDie(false);

		int roll = rand.nextInt(6) + 1;
		currentTurn.setRemainingSteps(roll);

		broker.notify("UpdateDie");

		// scenario where player was moved by suggestion
		if (currentTurn.canMakeSuggestion()) {
			currentTurn.setCanMakeSuggestion(false);
			broker.notify("UpdateSuggestion");
		}

		String rollEvent = currentTurn.getPlayer().getCharacter()
				+ " has rolled a " + roll + ".\n";
		data.setEventLog(data.getEventLog().concat(rollEvent));
		broker.notify("EventLog");

		updateSelectable();
	}

	public ArrayList<Position> getSelectablePositions() {
		Turn currentTurn = data.getCurrentTurn();

		ArrayList<Position> selectablePositions = new ArrayList<Position>();

		for (GNode n : currentTurn.getSelectableNodes()) {
			if (n.isRoom()) {
				selectablePositions.addAll(board.getRoomPositions(n));
			} else {
				selectablePositions.add(((HallwayNode) n).getLocation());
			}
		}

		return selectablePositions;
	}

	public void updateSelectable() {
		Turn currentTurn = data.getCurrentTurn();

		ArrayList<Path> selectablePaths = currentTurn.getSelectablePaths();
		selectablePaths.clear();

		if (currentTurn.canMove())
			updateSelectablePaths();

		broker.notify("UpdatePaths");

	}

	public void updateSelectablePaths() {
		Turn currentTurn = data.getCurrentTurn();
		int remainingSteps = currentTurn.getRemainingSteps();
		ArrayList<Path> selectablePaths = currentTurn.getSelectablePaths();

		if (!selectablePaths.isEmpty())
			selectablePaths.clear();

		selectablePaths.addAll(generateSelectablePaths(remainingSteps));
	}

	public ArrayList<Path> generateSelectablePaths(int remainingSteps) {
		Turn currentTurn = data.getCurrentTurn();
		GNode startNode = currentTurn.getPlayer().getLocation();
		ArrayList<Path> selectablePaths = new ArrayList<Path>();

		ArrayList<Position> playerPositions = new ArrayList<Position>();
		for (Card c : Card.getTypes(Type.SUSPECT)) {
			playerPositions.add(data.getTokenPositions().get(c));
		}

		ArrayList<ArrayList<GNode>> visitedList = new ArrayList<ArrayList<GNode>>();
		for (int i = 0; i <= remainingSteps; i++) {
			visitedList.add(new ArrayList<GNode>());
		}

		// dealing with first step separately, to ensure secret passages aren't
		// made selectable
		visitedList.get(0).add(startNode);

		int currentStep = 1;
		for (GNode n : startNode.getEdges()) {

			if (!currentTurn.getPreviousLocations().contains(n)) {
				if (!n.isRoom()) {
					if (!playerPositions.contains(((HallwayNode) n)
							.getLocation())) {
						visitedList.get(1).add(n);
						Path path = new Path(startNode);
						path.add(n);
						selectablePaths.add(path);
					}
				} else {
					// ensures this node isn't the secret passage
					if (!startNode.isRoom()) {
						Path path = new Path(startNode);
						path.add(n);
						selectablePaths.add(path);
					}
				}
			}
		}

		for (; currentStep < remainingSteps
				&& !visitedList.get(currentStep).isEmpty(); currentStep++) {
			for (GNode currentNode : visitedList.get(currentStep)) {
				for (GNode nextNode : currentNode.getEdges()) {
					// check to see if it's already been searched
					boolean explored = false;
					for (ArrayList<GNode> list : visitedList) {
						for (GNode n : list) {
							if (nextNode == n)
								explored = true;
						}
					}

					if (!explored
							&& !currentTurn.getPreviousLocations().contains(
									nextNode)) {
						Path oldPath = null;
						for (Path p : selectablePaths) {
							if (p.getDestination().equals(currentNode)) {
								oldPath = p;
								break;
							}
						}
						if (oldPath == null) {
							// TODO: exception; path finding broken
						}

						if (nextNode.isRoom()) {
							Path path = oldPath.clone();
							path.add(nextNode);

							selectablePaths.add(path);
						} else {
							HallwayNode nextTile = (HallwayNode) nextNode;
							if (!playerPositions.contains(nextTile
									.getLocation())) {
								// find path to add to
								Path path = oldPath.clone();
								path.add(nextNode);

								selectablePaths.add(path);
								visitedList.get(currentStep + 1).add(nextNode);
							}
						}
					}

				}
			}
		}

		return selectablePaths;
	}

	public void moveCurrentPlayer(Position destination) {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();
		GNode destinationNode = board.getGNode(destination);

		boolean secretPassage = false;

		// get path
		Path path = null;

		for (Path p : currentTurn.getSelectablePaths()) {

			if (p.getNode(p.size() - 1).equals(destinationNode)) {
				path = p;
				break;
			}
		}

		if (path == null) {
			// TODO: exception
			System.err.println("Error: cannot find path");
			System.err.println("Current location: "
					+ currentPlayer.getLocation() + ", destination: "
					+ destination);
			System.err.println(currentTurn.toString());
			return;
		}

		// special case: secret passage, no change in remainingSteps
		if (path.size() == 2 && path.getNode(0).isRoom()
				&& path.getNode(1).isRoom()) {
			secretPassage = true;
			currentTurn.setCanRollDie(false);
			broker.notify("UpdateDie");
		}

		if (!secretPassage) {
			currentTurn.setRemainingSteps(currentTurn.getRemainingSteps()
					- (path.size() - 1));
		}

		// add path to previousPaths;
		currentTurn.getPreviousPaths().add(path);

		currentPlayer.setLocation(destinationNode);

		if (destinationNode.isRoom()) {
			// determine new token position
			destination = getNextTokenPosition(board
					.getRoomCard((RoomNode) destinationNode));

			// overwrite old token position
			data.getTokenPositions().put(currentPlayer.getCharacter(),
					destination);
		} else {
			// overwrite old token position
			data.getTokenPositions().put(currentPlayer.getCharacter(),
					destination);
		}

		// special case: entering a room. No more moving.
		if (destinationNode.isRoom()) {
			currentTurn.setCanMakeSuggestion(true);
			broker.notify("UpdateSuggestion");
		}

		String rollEvent;

		if (!secretPassage) {
			rollEvent = currentTurn.getPlayer().getCharacter()
					+ " has moved " + (path.size() - 1) + " steps.  "
					+ currentTurn.getRemainingSteps()
					+ " steps remaining.\n";
		} else {
			rollEvent = currentTurn.getPlayer().getCharacter()
					+ " has taken the secret passage to the "
					+ board.getRoomCard((RoomNode) destinationNode)
							.toString() + "\n";
		}
		data.setEventLog(data.getEventLog().concat(rollEvent));
		broker.notify("EventLog");
		broker.notify("UpdateTokens");
		broker.notify("UpdateRetractMovement");

		updateSelectable();
	}

	public void retractMovement() {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();
		boolean secretPassage = false;

		Path previousPath;

		if (!currentTurn.hasPreviousPath()) {
			// TODO: exception
			System.err.println("Error: no movements to retract");
			return;
		} else
			previousPath = currentTurn.removePreviousPath();

		if (!previousPath.getDestination().equals(currentPlayer.getLocation())) {
			// TODO: exception
			System.err.println("Error: Cannot find previous path.");
			return;
		}

		if (currentPlayer.getLocation().isRoom()
				&& !currentTurn.canMakeSuggestion()) {
			// TODO: exception
			System.err
					.println("You cannot retract movement after making suggestion");
			return;
		}

		if (previousPath.size() == 2 && previousPath.getNode(0).isRoom()
				&& previousPath.getNode(1).isRoom())
			secretPassage = true;

		GNode previousLocation = previousPath.getOrigin();
		ArrayList<Path> allPreviousPaths = currentTurn.getPreviousPaths();

		// get previousRemaningSteps
		int previousRemainingSteps;
		// special case: secret passage
		if (secretPassage) {
			previousRemainingSteps = 0;
		} else {
			previousRemainingSteps = currentTurn.getRemainingSteps()
					+ (previousPath.size() - 1);
		}

		currentPlayer.setLocation(previousLocation);
		currentTurn.setRemainingSteps(previousRemainingSteps);

		String retractEvent;

		if (!secretPassage) {
			retractEvent = currentPlayer.getCharacter()
					+ " has retracted their movement.  "
					+ previousRemainingSteps + " steps remaining.\n";
		} else {
			retractEvent = currentPlayer.getCharacter()
					+ " has retracted their movement through the secret passage\n";
		}
		data.setEventLog(data.getEventLog().concat(retractEvent));
		broker.notify("EventLog");

		if (allPreviousPaths.isEmpty()) {
			broker.notify("UpdateRetractMovement");
		}

		// update token
		Position previousPosition;
		if (previousLocation.isRoom()) {
			previousPosition = this.getNextTokenPosition(board
					.getRoomCard((RoomNode) previousLocation));
		} else {
			previousPosition = ((HallwayNode) previousLocation).getLocation();
		}
		data.getTokenPositions().put(currentPlayer.getCharacter(),
				previousPosition);
		broker.notify("UpdateTokens");

		// special case: player was moved by suggestion
		if (allPreviousPaths.isEmpty() && currentPlayer.wasMoved()) {
			currentTurn.setCanMakeSuggestion(true);
			broker.notify("UpdateSuggestion");
		} else {
			if (currentTurn.canMakeSuggestion()) {
				currentTurn.setCanMakeSuggestion(false);
				broker.notify("UpdateSuggestion");
			}
		}
		// special case: secret passage
		if (previousRemainingSteps == 0) {
			// user never actually rolled die,
			currentTurn.setCanRollDie(true);
			broker.notify("UpdateDie");
			this.highlightSecretPassage();
		} else {
			this.updateSelectable();
		}

	}

	public boolean makeSuggestion(Card suspect, Card weapon) {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();

		RoomNode currentRoom;

		// temporary, should be handled by exception.
		if (!currentTurn.canMakeSuggestion())
			System.out
					.println("Error: cannot make suggestion.  Proceeding regardless.");

		// temporary, should be handled by exception. Helpful for testing.
		if (!currentPlayer.getLocation().isRoom()) {
			System.out
					.println("Error: suggestion initiated from outside room.  Room set to Hall.");
			currentRoom = (RoomNode) board.getRooms().get(0);
		} else
			currentRoom = (RoomNode) currentPlayer.getLocation();

		// special case: player was moved, and can make suggestion right away
		if (currentTurn.canRollDie()) {
			currentTurn.setCanRollDie(false);
			broker.notify("UpdateDie");
		}

		currentTurn.setCanMakeSuggestion(false);
		broker.notify("UpdateSuggestion");

		Card room = board.getRoomCard(currentRoom);

		String event = currentPlayer.getCharacter().toString()
				+ " has suggested that " + suspect
				+ " murdered Mr.Body in the " + room.toString() + " with "
				+ weapon + "\n";

		data.setEventLog(data.getEventLog().concat(event));
		broker.notify("EventLog");
		
		try {
			Thread.sleep(SUGGESTION_PAUSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Player> players = data.getPlayers();

		// move suspect Player to currentRoom
		if (!currentPlayer.getCharacter().equals(suspect)) {
			for (Player p : players) {
				if (p.getCharacter().equals(suspect)) {
					Position tokenPosition = getNextTokenPosition(room);

					data.getTokenPositions().put(p.getCharacter(),
							tokenPosition);
					p.setLocation(currentRoom);
					p.setMoved(true);
					break;
				}
			}
		}

		// move weapon token into currentRoom
		Position weaponTokenPosition = getNextTokenPosition(room);
		data.getTokenPositions().put(weapon, weaponTokenPosition);

		broker.notify("UpdateTokens");

		// get Disproving player
		int i = players.indexOf(currentPlayer);
		i++;
		i %= players.size();
		Player disprovingPlayer = null;

		for (; i != players.indexOf(currentPlayer); ++i, i %= players.size()) {
			ArrayList<Card> playerHand = players.get(i).getHand();
			if (playerHand.contains(room) || playerHand.contains(suspect)
					|| playerHand.contains(weapon)) {
				disprovingPlayer = players.get(i);
				break;
			}
		}

		boolean wasDisproved = false;

		if (disprovingPlayer != null) {
			// get Disproving cards
			ArrayList<Card> disprovingCards = new ArrayList<Card>();
			for (Card c : disprovingPlayer.getHand()) {
				if (c.equals(room) || c.equals(suspect) || c.equals(weapon)) {
					disprovingCards.add(c);
				}
			}
			startDisproveSuggestion(disprovingPlayer, disprovingCards);
			wasDisproved = true;

		} else {
			event = "Nobody could disprove the suggestion\n";
			data.setEventLog(data.getEventLog().concat(event));
			
			broker.notify("EventLog");
			
			if (!currentPlayer.isHuman()) {
				try {
					Thread.sleep(END_SUGGESTION_PAUSE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		broker.notify("UpdateRetractMovement");

		currentTurn.getSelectablePaths().clear();
		broker.notify("UpdatePaths");

		return wasDisproved;
	}

	private void startDisproveSuggestion(Player disprovingPlayer,
			ArrayList<Card> disprovingCards) {

		String event = disprovingPlayer.getCharacter().toString()
				+ " can disprove "
				+ data.getCurrentTurn().getPlayer().getCharacter().toString()
				+ "'s suggestion.\n";
		data.setEventLog(data.getEventLog().concat(event));

		broker.notify("EventLog");

		data.getCurrentTurn().setDisprovingPlayer(disprovingPlayer);
		data.getCurrentTurn().setDisprovingCards(disprovingCards);
		broker.notify("Disprove");
	}

	public void endDisproveSuggestion() {
		Turn currentTurn = data.getCurrentTurn();
		Player disprovingPlayer = data.getCurrentTurn().getDisprovingPlayer();

		String event = disprovingPlayer.getCharacter().toString()
				+ " has shown "
				+ data.getCurrentTurn().getPlayer().getCharacter().toString()
				+ " a card.\n";
		data.setEventLog(data.getEventLog().concat(event));

		broker.notify("EventLog");
		
		if (!currentTurn.getPlayer().isHuman()) {
			try {
				Thread.sleep(END_SUGGESTION_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		currentTurn.getDisprovingCards().clear();
		currentTurn.setDisprovingPlayer(null);

		if (!currentTurn.getPlayer().isHuman()) {
			this.endTurn();
		}
	}

	public void makeAccusation(Card room, Card suspect, Card weapon) {
		Player currentPlayer = this.data.getCurrentTurn().getPlayer();
		ArrayList<Card> caseFile = data.getCaseFile();
		
		
		String accusationEvent = currentPlayer.getCharacter()
				+ " has accused " + suspect + " of murdering Mr. Body in the " + room + " with a " + weapon + "!\n";
		data.setEventLog(data.getEventLog().concat(accusationEvent));
		broker.notify("EventLog");
		
		if (!currentPlayer.isHuman()) {
			try {
				Thread.sleep(ACCUSATION_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		if (room == caseFile.get(0) && weapon == caseFile.get(1)
				&& suspect == caseFile.get(2)) {
			String Event = currentPlayer.getCharacter()
					+ " has guessed correctly.  Game over, " + currentPlayer.getCharacter() + " is the winner!\n";
			data.setEventLog(data.getEventLog().concat(Event));
			broker.notify("EventLog");
			broker.notify("EndGame"); // disable buttons for human players
			return;
		} else {
			currentPlayer.setAlive(false); // kill player
			Card currentPlayerCard = currentPlayer.getCharacter();
			
			Position startPosition = null;
			// move back to start position
			switch (currentPlayerCard) {
			case MISS_SCARLET:
				startPosition = new Position(7, 24);
				break;
			case COLONEL_MUSTARD:
				startPosition = new Position(0, 17);
				break;
			case MRS_WHITE:
				startPosition = new Position(9, 0);
				break;
			case MR_GREEN:
				startPosition = new Position(14, 0);
				break;
			case MRS_PEACOCK:
				startPosition = new Position(23, 6);
				break;
			default:
				startPosition = new Position(23, 19);
			}
			
			String Event = currentPlayerCard
					+ " made an  accusation. " + currentPlayerCard + " is now dead.\n";
			data.setEventLog(data.getEventLog().concat(Event));
			broker.notify("EventLog");
			
			currentPlayer.setLocation(board.getGNode(startPosition));
			data.getTokenPositions().put(currentPlayerCard, startPosition);
			broker.notify("UpdateTokens");
			
			int alivePlayers = 0;
			Player alive = null;
			for (Player p : data.getPlayers()) {
				if (p.getAlive()) {
					alivePlayers += 1;
					alive = p;
				}
			}
			
			if (alivePlayers < 2) {
				String gameOverEvent = "Only one player remaining.  Game over, " + alive.getCharacter() + " is the winner!\n";
				data.setEventLog(data.getEventLog().concat(gameOverEvent));
				broker.notify("EventLog");
				broker.notify("EndGame");
			}
			else {
				this.endTurn();
			}
		}

	}

	private Position getNextTokenPosition(Card room) {

		Position nextTokenPosition = null;
		ArrayList<Position> roomTokenPositions = board
				.getRoomTokenPositions(room);
		HashMap<Card, Position> currentTokenPositions = data
				.getTokenPositions();

		for (Position p : roomTokenPositions) {
			if (!currentTokenPositions.containsValue(p)) {
				nextTokenPosition = p;
				break;
			}
		}

		// TODO: room overflow
		if (nextTokenPosition == null) {
			Card nextRoom = null;
			ArrayList<Card> allRooms = Card.getTypes(Type.ROOM);
			allRooms.remove(room);
			nextRoom = allRooms.get(rand.nextInt(allRooms.size()));

			for (Card c : Card.getTypes(Type.WEAPON)) {
				Position weaponPosition = currentTokenPositions.get(c);
				if (roomTokenPositions.contains(weaponPosition)) {
					currentTokenPositions.put(c,
							this.getNextTokenPosition(nextRoom));
					nextTokenPosition = weaponPosition;
					break;
				}
			}
		}

		return nextTokenPosition;
	}

	public void storeNotebook(HashMap<Card, String> stateList, String text) {
		Notebook notes = data.getCurrentTurn().getPlayer().getNotebook();
		HashMap<Card, String> states = notes.getStateList();

		notes.setText(text);
		for (Map.Entry<Card, String> entry : stateList.entrySet()) {
			states.put(entry.getKey(), entry.getValue());
		}
	}

	public void setData(SessionData data) {
		this.data = data;
	}

	public SessionData getData() {
		return data;
	}

	public Gameboard getBoard() {
		return board;
	}

	public Card getDisprovingCard(ComputerPlayer disprovingPlayer) {
		Difficulty difficulty = disprovingPlayer.getDifficulty();
		if (difficulty == Difficulty.EASY) {
			return easyAI.getDisprovingCard();
		} else if (difficulty == Difficulty.MEDIUM) {
			return mediumAI.getDisprovingCard();
		} else {
			return hardAI.getDisprovingCard();
		}
	}

	public boolean canRetractMovement() {
		Turn currentTurn = data.getCurrentTurn();
		Player currentPlayer = currentTurn.getPlayer();

		// has previous paths
		if (!currentTurn.getPreviousPaths().isEmpty()) {
			// already made suggestion
			if (currentPlayer.getLocation().isRoom()
					&& !currentTurn.canMakeSuggestion()) {
				return false;
			} else {
				return true;
			}
		}
		// no previous paths
		else {
			return false;
		}
	}
}
