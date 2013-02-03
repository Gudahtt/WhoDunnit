package ca.mun.whodunnit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ca.mun.whodunnit.api.model.Direction;
import ca.mun.whodunnit.api.model.PlayerType;
import ca.mun.whodunnit.api.model.SquareType;
import ca.mun.whodunnit.logic.ClueController;
import ca.mun.whodunnit.logic.GameController;
import ca.mun.whodunnit.logic.TurnController;
import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.ComputerPlayer;
import ca.mun.whodunnit.model.GNode;
import ca.mun.whodunnit.model.Gameboard;
import ca.mun.whodunnit.model.Player;
import ca.mun.whodunnit.model.Position;
import ca.mun.whodunnit.model.RoomNode;
import ca.mun.whodunnit.model.SessionData;
import ca.mun.whodunnit.model.Turn;

public class ClueMediator {
	private ClueController control;
	private GameController game;
	private TurnController turn;
	private SessionData data;

	public void setData(SessionData data) {
		this.data = data;
	}

	/**
	 * @param control
	 */
	public ClueMediator(ClueController control) {
		this.control = control;
		game = control.getGameController();
		turn = game.getTurnController();
		data = game.getData();
	}

	/**
	 * 
	 */
	public void endGame() {
		game.endGame();
	}
	
	public Card getWinner() {
		return game.getWinner();
	}

	public void endTurn() {
		turn.endTurn();
	}

	public Turn getCurrentTurn() {
		return game.getData().getCurrentTurn();
	}

	public String getEventLog() {
		return game.getData().getEventLog();
	}

	public ArrayList<Player> getPlayers() {
		return game.getData().getPlayers();
	}

	public void retractMovement() {
		turn.retractMovement();
	}

	public HashMap<Card, Position> getTokenPositions() {
		return game.getData().getTokenPositions();
	}

	public Position getTokenPosition(Card card) {
		return game.getTokenPosition(card);
	}

	public void makeSuggestion(Card suspect, Card weapon) {
		turn.makeSuggestion(suspect, weapon);
	}

	public void startGame(HashMap<Card, PlayerType> players) {
		game.startGame(players);
	}

	public void startTurn() {
		turn.startTurn();
	}

	public void saveGame(String saveFilePath) {
		try {
			control.saveGame(saveFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadGame(String loadFilePath) {
		try {
			control.loadGame(loadFilePath);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void endDisproveSuggestion() {
		turn.endDisproveSuggestion();
	}

	public void makeAccusation(Card room, Card suspect, Card weapon) {
		turn.makeAccusation(room, suspect, weapon);
	}

	public SquareType checkIsRoom(Position position) {
		Gameboard board = turn.getBoard();
		GNode node = board.getGNode(position);

		if (node == null)
			return SquareType.UNUSED;
		else if (node.isRoom())
			return SquareType.ROOM;
		else
			return SquareType.HALLWAY;
	}

	public HashMap<Position, Direction> getEntrances() {
		return turn.getBoard().getEntrancePositions();
	}

	public void registerSubscriber(String event, Subscriber sub) {
		control.getBroker().registerSubscriber(event, sub);

	}

	// stores current state of interface-notebook in the model-notebook
	public void storeNotebook(HashMap<Card, String> states, String text) {
		turn.storeNotebook(states, text);
	}

	public void setDisprovingCards(ArrayList<Card> c) {
		game.getCurrentTurn().setDisprovingCards(c);
	}

	public void rollDie() {
		turn.rollDie();
	}

	// returns list of valid positions to move to
	public ArrayList<Position> getPathPositions() {
		return turn.getSelectablePositions();
	}

	public void moveCurrentPlayer(Position movedPosition) // called from GUI to
	// inform model that
	// a player has
	// moved
	{
		turn.moveCurrentPlayer(movedPosition);

	}

	public Card getDisprovingCard(ComputerPlayer disprovingComputerPlayer) {
		return turn.getDisprovingCard(disprovingComputerPlayer);
	}

	public boolean canRollDie() {
		return data.getCurrentTurn().canRollDie();
	}

	public boolean canSuggest() {
		return data.getCurrentTurn().canMakeSuggestion();
	}

	public boolean canRetractMovement() {
		return turn.canRetractMovement();
	}

	public boolean checkIsHuman() {
		return data.getCurrentTurn().getPlayer().isHuman();
	}

	public Card getCurrentRoom() {
		GNode location = data.getCurrentTurn().getPlayer().getLocation();
		RoomNode room = null;
		if (!(location == null))
			room = (RoomNode) location;
		return turn.getBoard().getRoomCard(room);
	}
}
