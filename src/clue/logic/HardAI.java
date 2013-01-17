package clue.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import clue.model.Card;
import clue.model.ComputerPlayer;
import clue.model.GNode;
import clue.model.Gameboard;
import clue.model.Path;
import clue.model.RoomNode;

public class HardAI extends AI {

	private TurnController turnController;
	private Random rand;

	public HardAI(TurnController turn) {
		super(turn);
		this.turnController = turn;
		this.rand = new Random();
	}

	@Override
	public void setGoalRoom() {
		ArrayList<Card> viableRooms = turnController.getData().getCurrentTurn()
				.getPlayer().getNotebook().getViableCards(Card.Type.ROOM);
		Gameboard board = turnController.getBoard();
		ComputerPlayer player = (ComputerPlayer) turnController.getData()
				.getCurrentTurn().getPlayer();

		ArrayList<GNode> targetRooms = new ArrayList<GNode>();

		GNode currentLocation = player.getLocation();
		if (currentLocation.isRoom()) {
			viableRooms.remove(board.getRoomCard((RoomNode) currentLocation));
		}

		for (Card c : viableRooms) {
			targetRooms.add(board.getRoomNode(c));
		}

		Path goalPath = this.getShortestPath(targetRooms);

		RoomNode destination = (RoomNode) goalPath.getDestination();
		player.getAIData().setGoalRoom(board.getRoomCard(destination));
	}

	@Override
	public Card generateSuspect() {
		ArrayList<Card> viableRooms = turnController.getData().getCurrentTurn()
				.getPlayer().getNotebook().getViableCards(Card.Type.SUSPECT);
		return viableRooms.get(rand.nextInt(viableRooms.size()));
	}

	@Override
	public Card generateWeapon() {
		ArrayList<Card> viableRooms = turnController.getData().getCurrentTurn()
				.getPlayer().getNotebook().getViableCards(Card.Type.WEAPON);
		return viableRooms.get(rand.nextInt(viableRooms.size()));
	}

	@Override
	public Card getDisprovingCard() {
		ArrayList<Card> disprovingCards = turnController.getData()
				.getCurrentTurn().getDisprovingCards();
		HashMap<Card, ArrayList<Card>> shown = ((ComputerPlayer) turnController
				.getData().getCurrentTurn().getDisprovingPlayer()).getAIData()
				.getShown();
		Card currentPlayerCard = turnController.getData().getCurrentTurn()
				.getPlayer().getCharacter();
		ArrayList<Card> shownList = shown.get(currentPlayerCard);

		for (Card c : disprovingCards) {
			if (shownList.contains(c)) { // show the current player the same
											// card again
				return c;
			}
		}

		// have not shown this player any of these cards before
		return disprovingCards.get(0);
	}
}
