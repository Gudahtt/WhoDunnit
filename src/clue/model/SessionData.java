package clue.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import clue.api.model.PlayerType;
import clue.model.Card.Type;

public class SessionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6232533420023316253L;
	private ArrayList<Player> players;
	private Turn currentTurn;
	private ArrayList<Card> caseFile;
	private String eventLog;
	private HashMap<Card, Position> tokenPositions;

	public SessionData() {
		currentTurn = new Turn();

		this.eventLog = "";
		this.tokenPositions = new HashMap<Card, Position>();
		this.caseFile = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
	}

	/**
	 * @param weaponPositions
	 *            the weaponPositions to set
	 */
	public void setTokenPositions(HashMap<Card, Position> tokenPositions) {
		this.tokenPositions = tokenPositions;
	}

	public void createPlayers(HashMap<Card, PlayerType> playerSettings) {
		if (!players.isEmpty()) {
			players.clear();
		}

		for (Card c : Card.getTypes(Type.SUSPECT)) {
			if (playerSettings.containsKey(c)) {
				switch (playerSettings.get(c)) {
				case HUMAN:
					players.add(new HumanPlayer(c, true));
					break;
				case EASY:
					players.add(new ComputerPlayer(c, Difficulty.EASY));
					break;
				case MEDIUM:
					players.add(new ComputerPlayer(c, Difficulty.MEDIUM));
					break;
				case HARD:
					players.add(new ComputerPlayer(c, Difficulty.HARD));
					break;
				}
			} else {
				players.add(new HumanPlayer(c, false));
			}
		}

	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players
	 *            the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * @return the currentTurn
	 */
	public Turn getCurrentTurn() {
		return currentTurn;
	}

	/**
	 * @return the caseFile
	 */
	public ArrayList<Card> getCaseFile() {
		return caseFile;
	}

	/**
	 * @param caseFile
	 *            the caseFile to set
	 */
	public void setCaseFile(ArrayList<Card> caseFile) {
		this.caseFile = caseFile;
	}

	/**
	 * @return the eventLog
	 */
	public String getEventLog() {
		return eventLog;
	}

	/**
	 * @param eventLog
	 *            the eventLog to set
	 */
	public void setEventLog(String eventLog) {
		this.eventLog = eventLog;

	}

	public HashMap<Card, Position> getTokenPositions() {
		return tokenPositions;
	}

	public void clear() {
		this.eventLog = "";
		this.tokenPositions = new HashMap<Card, Position>();
		this.currentTurn.clear();
		this.caseFile = new ArrayList<Card>();
		this.players = new ArrayList<Player>();
	}
}
