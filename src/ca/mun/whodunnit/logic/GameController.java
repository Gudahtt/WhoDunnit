package ca.mun.whodunnit.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ca.mun.whodunnit.api.EventBroker;
import ca.mun.whodunnit.api.model.PlayerType;
import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.ComputerPlayer;
import ca.mun.whodunnit.model.GNode;
import ca.mun.whodunnit.model.Gameboard;
import ca.mun.whodunnit.model.Notebook;
import ca.mun.whodunnit.model.Player;
import ca.mun.whodunnit.model.Position;
import ca.mun.whodunnit.model.SessionData;
import ca.mun.whodunnit.model.Turn;
import ca.mun.whodunnit.model.Card.Type;


public class GameController {
	private Random rand;
	private SessionData data;
	private Gameboard board;
	private EventBroker broker;
	private TurnController turn;
	private Card winner;

	/*
	 * @param control
	 */
	public GameController(WhoDunnitController control) {
		this.broker = control.getBroker();
		this.rand = new Random();
		this.data = new SessionData();
		this.board = new Gameboard();
		this.winner = null;

		this.turn = new TurnController(data, broker, board);

		// Registering events with EventBroker
		broker.registerEvent("EventLog");
		broker.registerEvent("StartGame");
		broker.registerEvent("EndGame");
	}

	/* @param initialPlayers MUST contain all 6 characters */
	public void startGame(HashMap<Card, PlayerType> playerSettings) {
		// creating players
		data.createPlayers(playerSettings);

		HashMap<Card, Position> tokenPositions = randomizeWeaponPositions();

		ArrayList<Player> players = data.getPlayers();

		for (Player p : players) {
			// determine start Position
			Position tokenPosition;
			Card playerCharacter = p.getCharacter();

			switch (playerCharacter) {
			case MISS_SCARLET:
				tokenPosition = new Position(7, 24);
				break;
			case COLONEL_MUSTARD:
				tokenPosition = new Position(0, 17);
				break;
			case MRS_WHITE:
				tokenPosition = new Position(9, 0);
				break;
			case MR_GREEN:
				tokenPosition = new Position(14, 0);
				break;
			case MRS_PEACOCK:
				tokenPosition = new Position(23, 6);
				break;
			default:
				tokenPosition = new Position(23, 19);

			}

			tokenPositions.put(playerCharacter, tokenPosition);
			GNode startNode = board.getGNode(tokenPosition);
			p.setLocation(startNode);
		}

		data.setTokenPositions(tokenPositions);

		Player first = null;
		for (Player p : players) {
			if (p.getAlive()) {
				first = p;
				break;
			}
		}

		ArrayList<Card> caseFile = new ArrayList<Card>();

		dealCards(players, caseFile);

		for (Player p : players) {
			Notebook playerNotes = p.getNotebook();
			for (Card c : p.getHand()) {
				playerNotes.changeState(c, "No");
			}
		}

		data.setPlayers(players);
		data.getCurrentTurn().setPlayer(first);
		data.setCaseFile(caseFile);

		// Writing game start event to eventlog
		String events = new String("Game initializing...\nCurrent Players: "
				+ first.getCharacter());
		if (first.isHuman()) {
			events = events.concat(" (Human)");
		} else {
			ComputerPlayer firstComp = (ComputerPlayer) first;
			events = events.concat(" (Computer: "
					+ firstComp.getDifficulty().toString() + ")");
		}

		for (int i = players.indexOf(first) + 1; i < players.size(); i++) {
			if (players.get(i).getAlive()) {
				events = events.concat(", "
						+ players.get(i).getCharacter().toString());
				if (players.get(i).isHuman()) {
					events = events.concat(" (Human)");
				} else {
					events = events.concat(" (Computer: "
							+ ((ComputerPlayer) players.get(i)).getDifficulty()
									.toString() + ")");
				}
			}
		}

		events = events.concat("\n");
		data.setEventLog(data.getEventLog().concat(events));

		broker.notify("StartGame");
		broker.notify("EventLog");
		broker.notify("UpdateTokens");
		broker.notify("UpdateSaveButton");

		if (!data.getCurrentTurn().getPlayer().isHuman()) {
			turn.startTurn();
		}
	}

	private void dealCards(ArrayList<Player> players, ArrayList<Card> caseFile) {
		// creating sorted deck of cards
		ArrayList<Card> cards = Card.getAllCards();

		// add room to case file
		caseFile.add(cards.remove(12 + rand.nextInt(9)));

		// add weapon to case file
		caseFile.add(cards.remove(6 + rand.nextInt(6)));

		// add suspect to case file
		caseFile.add(cards.remove(rand.nextInt(6)));

		// determine number of active players
		int numPlayers = 0;
		for (Player p : players) {
			if (p.getAlive()) {
				++numPlayers;
			}
		}

		// create array for each hand
		ArrayList<ArrayList<Card>> deal = new ArrayList<ArrayList<Card>>();

		for (int i = 0; i < numPlayers; i++) {
			deal.add(new ArrayList<Card>());
		}

		// add cards to each hand
		int i = 0;
		while (deal.size() > 0 && !cards.isEmpty()) {
			Card c = cards.remove(rand.nextInt(cards.size()));
			deal.get(i).add(c);
			i++;
			i %= deal.size();
		}

		// assign dealt cards to players in random order
		for (Player p : players) {
			if (p.getAlive()) {
				p.setHand(deal.remove(rand.nextInt(deal.size())));
			}
		}
	}

	public HashMap<Card, Position> randomizeWeaponPositions() {
		ArrayList<Card> weapons = Card.getTypes(Type.WEAPON);
		ArrayList<Card> rooms = Card.getTypes(Type.ROOM);
		HashMap<Card, Position> weaponPositions = new HashMap<Card, Position>();

		for (Card c : weapons) {
			Card room = rooms.remove(rand.nextInt(rooms.size()));
			weaponPositions.put(c, board.getRoomTokenPositions(room).get(0));
		}
		return weaponPositions;
	}

	public void endGame() {
		if (data.getCurrentTurn().getPlayer().getAlive()) {
			winner = data.getCurrentTurn().getPlayer().getCharacter();
		}
		else {
			for (Player p : data.getPlayers()) {
				if (p.getAlive()) {
					winner = p.getCharacter();
				}
			}
		}
		
		data.clear();
	}

	public SessionData getData() {
		return data;
	}

	public void setData(SessionData data) {
		this.data = data;
		turn.setData(data);

	}

	/**
	 * @return the currentTurn
	 */
	public Turn getCurrentTurn() {
		return data.getCurrentTurn();
	}

	public boolean isGameStarted() {
		if (data == null)
			return false;
		else
			return true;
	}

	public TurnController getTurnController() {
		return turn;
	}

	public Position getTokenPosition(Card card) {
		return data.getTokenPositions().get(card);
	}

	public Card getWinner() {
		return winner;
	}
}
