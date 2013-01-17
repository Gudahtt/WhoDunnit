package clue.logic;

import java.util.ArrayList;
import java.util.Random;

import clue.model.Card;
import clue.model.ComputerPlayer;
import clue.model.GNode;
import clue.model.Gameboard;
import clue.model.RoomNode;

public class EasyAI extends AI {
	private Random rand = new Random();
	private TurnController turnController;

	public EasyAI(TurnController turn) {
		super(turn);
		this.turnController = turn;
		rand = new Random();
	}

	@Override
	public void setGoalRoom() {
		ComputerPlayer player = (ComputerPlayer) turnController.getData()
				.getCurrentTurn().getPlayer();
		ArrayList<Card> roomList = Card.getTypes(Card.Type.ROOM);

		GNode currentLocation = player.getLocation();
		if (currentLocation.isRoom()) {
			Gameboard board = turnController.getBoard();
			roomList.remove(board.getRoomCard((RoomNode) currentLocation));
		}

		Card room = roomList.get(rand.nextInt(roomList.size()));

		player.getAIData().setGoalRoom(room);
	}

	@Override
	public Card generateSuspect() {
		Card[] suspectList = Card.getCardArray(Card.Type.SUSPECT);
		Card suspect = suspectList[rand.nextInt(6)];
		return suspect;
	}

	@Override
	public Card generateWeapon() {
		Card[] weaponList = Card.getCardArray(Card.Type.WEAPON);
		Card weapon = weaponList[rand.nextInt(6)];
		return weapon;
	}

	@Override
	public Card getDisprovingCard() {
		return turnController.getData().getCurrentTurn().getDisprovingCards()
				.get(0);
	}
}
