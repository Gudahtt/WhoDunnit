package clue.logic;

import java.util.ArrayList;
import java.util.Random;

import clue.model.Card;
import clue.model.ComputerPlayer;
import clue.model.GNode;
import clue.model.Gameboard;
import clue.model.RoomNode;

public class MediumAI extends AI {
	private Random rand;
	TurnController turnController;

	public MediumAI(TurnController turn) {
		super(turn);
		turnController = turn;
		rand = new Random();
	}

	@Override
	public void setGoalRoom() {
		ComputerPlayer player = (ComputerPlayer) turnController.getData()
				.getCurrentTurn().getPlayer();
		ArrayList<Card> viableRooms = player.getNotebook().getViableCards(
				Card.Type.ROOM);

		GNode currentLocation = player.getLocation();
		if (currentLocation.isRoom()) {
			Gameboard board = turnController.getBoard();
			viableRooms.remove(board.getRoomCard((RoomNode) currentLocation));
		}

		Card room = viableRooms.get(rand.nextInt(viableRooms.size()));

		player.getAIData().setGoalRoom(room);
	}

	@Override
	public Card generateSuspect() {
		ArrayList<Card> selectableCards = turnController.getData()
				.getCurrentTurn().getPlayer().getNotebook()
				.getViableCards(Card.Type.SUSPECT);

		Card suspect = selectableCards
				.get(rand.nextInt(selectableCards.size()));
		return suspect;
	}

	@Override
	public Card generateWeapon() {
		ArrayList<Card> selectableCards = turnController.getData()
				.getCurrentTurn().getPlayer().getNotebook()
				.getViableCards(Card.Type.WEAPON);

		Card weapon = selectableCards.get(rand.nextInt(selectableCards.size()));
		return weapon;
	}

	@Override
	public Card getDisprovingCard() {
		return turnController.getData().getCurrentTurn().getDisprovingCards()
				.get(0);
	}
}
