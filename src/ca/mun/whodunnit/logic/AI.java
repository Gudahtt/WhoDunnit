package ca.mun.whodunnit.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.ComputerPlayer;
import ca.mun.whodunnit.model.GNode;
import ca.mun.whodunnit.model.Gameboard;
import ca.mun.whodunnit.model.Notebook;
import ca.mun.whodunnit.model.Path;
import ca.mun.whodunnit.model.Position;
import ca.mun.whodunnit.model.RoomNode;
import ca.mun.whodunnit.model.Turn;
import ca.mun.whodunnit.model.Card.Type;

public abstract class AI {

	private TurnController turnController; // will be set by subclasses

	private static final int START_TURN_PAUSE = 1000;
	private static final int ROLL_DIE_PAUSE = 1000;
	private static final int MOVEMENT_PAUSE = 1000;

	public AI(TurnController turnController) {
		this.turnController = turnController;
	}

	abstract public void setGoalRoom();

	abstract public Card generateSuspect();

	abstract public Card generateWeapon();

	public void accusation() {
		// assumes that there is only one available card of each type
		Notebook notebook = turnController.getData().getCurrentTurn()
				.getPlayer().getNotebook();
		Card room = notebook.getViableCards(Card.Type.ROOM).get(0);
		Card suspect = notebook.getViableCards(Card.Type.SUSPECT).get(0);
		Card weapon = notebook.getViableCards(Card.Type.WEAPON).get(0);

		turnController.makeAccusation(room, suspect, weapon);
	}

	abstract public Card getDisprovingCard();

	public void suggestion() {
		Card suspect = generateSuspect();
		Card weapon = generateWeapon();
		boolean suggestionDisproved = turnController.makeSuggestion(suspect,
				weapon);

		if (!suggestionDisproved)
			turnController.endTurn();
	}

	public boolean shouldMakeAccusation() {
		Notebook playerNotebook = turnController.getData().getCurrentTurn()
				.getPlayer().getNotebook();
		ArrayList<Card> viableRooms = playerNotebook
				.getViableCards(Card.Type.ROOM);
		ArrayList<Card> viableSuspects = playerNotebook
				.getViableCards(Card.Type.SUSPECT);
		ArrayList<Card> viableWeapons = playerNotebook
				.getViableCards(Card.Type.WEAPON);

		if (viableRooms.size() == 1 && viableSuspects.size() == 1
				&& viableWeapons.size() == 1) {
			return true;
		} else
			return false;

	}

	public void takeTurn() {
		try {
			Thread.sleep(START_TURN_PAUSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Turn currentTurn = turnController.getData().getCurrentTurn();
		ComputerPlayer currentPlayer = (ComputerPlayer) currentTurn.getPlayer();
		Gameboard board = turnController.getBoard();
		GNode currentLocation = currentPlayer.getLocation();
		Card goalRoom = currentPlayer.getAIData().getGoalRoom();

		if (goalRoom == null || currentPlayer.wasMoved()) {
			this.setGoalRoom();
		} else if (currentLocation.isRoom()
				&& board.getRoomCard((RoomNode) currentLocation).equals(
						goalRoom)) {
			this.setGoalRoom();
		}

		if (this.shouldMakeAccusation()) {
			accusation();
			return;
		}

		if (currentTurn.canMakeSuggestion()) {
			this.suggestion();
			return;
		} else {
			Path targetPath = this.generateNextPath();

			GNode targetRoom = null;

			if (targetPath.size() == 1) {
				turnController.endTurn();
				return;
			} else {
				for (int i = 1; i < targetPath.size(); i++) {
					if (targetPath.getNode(i).isRoom()) {
						targetRoom = targetPath.getNode(i);
						break;
					}
				}
			}

			if (targetRoom == null) {
				// TODO: exception
				System.err.println("Cannot find room on target path");
				return;
			}

			GNode targetNode = null;
			if (currentPlayer.getLocation().isRoom()
					&& targetPath.indexOf(targetRoom) == 1) {
				targetNode = targetRoom;
			} else {
				turnController.rollDie();

				try {
					Thread.sleep(ROLL_DIE_PAUSE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int remainingSteps = currentTurn.getRemainingSteps();

				if (targetPath.indexOf(targetRoom) <= remainingSteps) {
					targetNode = targetRoom;
				} else {
					targetNode = targetPath.getNode(remainingSteps);
				}
			}

			Position targetPosition = turnController.getBoard()
					.getGNodePosition(targetNode);
			turnController.moveCurrentPlayer(targetPosition);

			try {
				Thread.yield();
				Thread.sleep(MOVEMENT_PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (currentPlayer.getLocation().isRoom()) {
			if (turnController.getBoard()
					.getRoomCard((RoomNode) currentPlayer.getLocation())
					.equals(goalRoom)) {
				// player has reached their goal
				this.setGoalRoom();
			}
		}

		if (currentTurn.canMakeSuggestion()) {
			this.suggestion();
			return;
		}

		// only occurs if AI wants to make an accusation after making a
		// suggestion
		if (shouldMakeAccusation()) {
			accusation();
			return;
		}

		turnController.endTurn();
	}

	public Path generateNextPath() {

		Gameboard board = turnController.getBoard();
		Turn currentTurn = turnController.getData().getCurrentTurn();
		ComputerPlayer player = (ComputerPlayer) currentTurn.getPlayer();

		Position targetRoomPosition = board.getRoomPosition(player.getAIData()
				.getGoalRoom());
		GNode targetNode = board.getGNode(targetRoomPosition);

		ArrayList<GNode> targetNodes = new ArrayList<GNode>();
		targetNodes.add(targetNode);
		return getShortestPath(targetNodes);
	}

	public Path getShortestPath(ArrayList<GNode> targetNodes) {

		final class WeightedGNode implements Comparable<WeightedGNode> {
			private GNode node;
			private int distance;
			private WeightedGNode previous;

			public WeightedGNode(GNode node, int distance) {
				this.node = node;
				this.distance = distance;
				this.setPrevious(null);
			}

			public GNode getNode() {
				return node;
			}

			public void setDistance(int distance) {
				this.distance = distance;
			}

			public int getDistance() {
				return distance;
			}

			@Override
			public int compareTo(WeightedGNode n) {
				if (n.getDistance() > this.getDistance())
					return -1;
				else if (n.getDistance() == this.getDistance())
					return 0;
				else
					return 1;
			}

			public void setPrevious(WeightedGNode previous) {
				this.previous = previous;
			}

			public WeightedGNode getPrevious() {
				return previous;
			}
		}

		Gameboard board = turnController.getBoard();
		Turn currentTurn = turnController.getData().getCurrentTurn();
		HashMap<Card, Position> tokenPositions = turnController.getData()
				.getTokenPositions();
		ComputerPlayer player = (ComputerPlayer) currentTurn.getPlayer();

		// create list of occupied tiles to omit
		ArrayList<GNode> occupiedGNodes = new ArrayList<GNode>();
		ArrayList<Card> otherPlayers = Card.getTypes(Type.SUSPECT);
		otherPlayers.remove(player.getCharacter());

		for (Card c : otherPlayers) {
			Position occupiedPositon = tokenPositions.get(c);
			GNode occupiedGNode = board.getGNode(occupiedPositon);
			if (!occupiedGNode.isRoom()) {
				occupiedGNodes.add(occupiedGNode);
			}
		}

		GNode currentNode = player.getLocation();
		PriorityQueue<WeightedGNode> nodeQueue = new PriorityQueue<WeightedGNode>();
		ArrayList<WeightedGNode> allNodes = new ArrayList<WeightedGNode>();

		for (GNode n : board.getTiles()) {
			if (!n.equals(currentNode) && !occupiedGNodes.contains(n)) {
				WeightedGNode node = new WeightedGNode(n, Integer.MAX_VALUE);
				allNodes.add(node);
				nodeQueue.add(node);
			}
		}
		for (GNode n : board.getRooms()) {
			if (!n.equals(currentNode)) {
				WeightedGNode node = new WeightedGNode(n, Integer.MAX_VALUE);
				allNodes.add(node);
				nodeQueue.add(node);
			}
		}

		nodeQueue.add(new WeightedGNode(currentNode, 0));

		while (!nodeQueue.isEmpty()) {
			WeightedGNode shortest = nodeQueue.remove();

			if (shortest.getDistance() == Integer.MAX_VALUE)
				break;

			// obtain WeightedGNode array of edges
			ArrayList<WeightedGNode> edges = new ArrayList<WeightedGNode>();
			for (GNode node : shortest.getNode().getEdges()) {
				if (!occupiedGNodes.contains(node)) {
					for (WeightedGNode weightedNode : allNodes) {
						if (weightedNode.getNode().equals(node)) {
							edges.add(weightedNode);
							break;
						}
					}
				}
			}

			for (WeightedGNode n : edges) {
				int alt = shortest.getDistance();
				// secret passage
				if (n.getNode().isRoom() && shortest.getNode().isRoom()) {
					// average roll is 3.5, plus between 0-5 steps wasted when
					// using
					// a secret passage as a shortcut. So, weight set to 5/2 +
					// 3.5
					alt += 6;
				} else
					alt += 1;

				if (alt < n.getDistance()) {
					n.setDistance(alt);
					n.setPrevious(shortest);
					if (nodeQueue.contains(n)) {
						nodeQueue.remove(n);
						nodeQueue.add(n);
					}

				}
			}
		}

		WeightedGNode closestTarget = new WeightedGNode(currentNode,
				Integer.MAX_VALUE);
		for (WeightedGNode n : allNodes) {
			if (targetNodes.contains(n.getNode())) {
				if (n.getDistance() < closestTarget.getDistance()) {
					closestTarget = n;
				}
			}
		}
		if (closestTarget.getNode().equals(currentNode)) {
			// blocked; cannot reach goal room(s)
			return new Path(currentNode);
		}

		Path shortestPath = new Path(currentNode);
		ArrayList<GNode> tempStack = new ArrayList<GNode>();
		while (closestTarget.getNode() != currentNode) {
			tempStack.add(closestTarget.getNode());
			closestTarget = closestTarget.getPrevious();
			if (closestTarget == null) {
				// TODO: exception
				System.err.println("Error: shortest path calculation failed");
				return null;
			}
		}

		for (int i = tempStack.size() - 1; i >= 0; i--) {
			shortestPath.add(tempStack.get(i));
		}

		return shortestPath;
	}
}
