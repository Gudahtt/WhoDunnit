package clue.model;

import java.io.Serializable;
import java.util.ArrayList;

public enum Card implements Serializable {
	MISS_SCARLET(Type.SUSPECT, "Miss Scarlet"), COLONEL_MUSTARD(Type.SUSPECT,
			"Colonel Mustard"), MRS_WHITE(Type.SUSPECT, "Mrs. White"), MR_GREEN(
			Type.SUSPECT, "Mr. Green"), MRS_PEACOCK(Type.SUSPECT,
			"Mrs. Peacock"), PROFESSOR_PLUM(Type.SUSPECT, "Professor Plum"),

	KNIFE(Type.WEAPON, "Knife"), CANDLESTICK(Type.WEAPON, "Candlestick"), REVOLVER(
			Type.WEAPON, "Revolver"), ROPE(Type.WEAPON, "Rope"), LEAD_PIPE(
			Type.WEAPON, "Lead Pipe"), WRENCH(Type.WEAPON, "Wrench"),

	HALL(Type.ROOM, "Hall"), LOUNGE(Type.ROOM, "Lounge"), DINING_ROOM(
			Type.ROOM, "Dining Room"), KITCHEN(Type.ROOM, "Kitchen"), BALL_ROOM(
			Type.ROOM, "Ball Room"), CONSERVATORY(Type.ROOM, "Conservatory"), BILLIARD_ROOM(
			Type.ROOM, "Billiard Room"), LIBRARY(Type.ROOM, "Library"), STUDY(
			Type.ROOM, "Study");

	public static enum Type {
		ROOM, SUSPECT, WEAPON;
	}

	private String name;

	Card(Type type, String name) {
		this.name = name;
	}

	private static Card[] rooms = new Card[] { HALL, LOUNGE, DINING_ROOM,
			KITCHEN, BALL_ROOM, CONSERVATORY, BILLIARD_ROOM, LIBRARY, STUDY };
	private static Card[] weapons = new Card[] { KNIFE, CANDLESTICK, REVOLVER,
			ROPE, LEAD_PIPE, WRENCH };
	private static Card[] suspects = new Card[] { MISS_SCARLET,
			COLONEL_MUSTARD, MRS_WHITE, MR_GREEN, MRS_PEACOCK, PROFESSOR_PLUM };

	public static Card[] getCardArray(Type type) {
		switch (type) {
		case ROOM:
			return rooms;
		case SUSPECT:
			return suspects;
		case WEAPON:
			return weapons;
		default:
			return null;
		}
	}

	public static ArrayList<Card> getTypes(Type type) {
		if (type == Type.ROOM) {
			ArrayList<Card> list = new ArrayList<Card>();
			for (Card c : Card.rooms)
				list.add(c);
			return list;
		}
		if (type == Type.WEAPON) {
			ArrayList<Card> list = new ArrayList<Card>();
			for (Card c : Card.weapons)
				list.add(c);
			return list;
		} else {
			ArrayList<Card> list = new ArrayList<Card>();
			for (Card c : Card.suspects)
				list.add(c);
			return list;
		}
	}

	public static ArrayList<Card> getAllCards() {
		ArrayList<Card> allCards = new ArrayList<Card>();

		allCards.addAll(Card.getTypes(Type.SUSPECT));
		allCards.addAll(Card.getTypes(Type.WEAPON));
		allCards.addAll(Card.getTypes(Type.ROOM));

		return allCards;
	}

	public static Card getCard(String s) {
		for (Card c : getAllCards()) {
			if (c.toString().equals(s)) {
				return c;
			}
		}

		throw new IllegalArgumentException("String doesn't match any card");
	}

	@Override
	public String toString() {
		return name;
	}
}