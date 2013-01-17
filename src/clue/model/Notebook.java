package clue.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Notebook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1645875343061677731L;
	HashMap<Card, String> stateList; // yes,no,maybe, or unchecked state for
	// each button
	String text;

	public Notebook() {
		text = "";
		stateList = new HashMap<Card, String>();

		for (Card c : Card.getAllCards()) {
			stateList.put(c, "Unchecked");
		}
	}

	public void changeState(Card card, String state) {
		stateList.put(card, state); // overwrite old state
	}

	public int size() {
		return stateList.size();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HashMap<Card, String> getStateList() {
		return stateList;
	}

	@Override
	public String toString() {
		String s = new String();
		for (Card c : Card.getAllCards()) {
			s = s.concat("Notebook - " + c.toString() + ": " + stateList.get(c)
					+ "\n");
		}
		return s;
	}

	public ArrayList<Card> getViableCards(Card.Type type) {
		ArrayList<Card> cardList = Card.getTypes(type);
		ArrayList<Card> stateIsNo = new ArrayList<Card>();

		for (Card c : cardList) {
			if (stateList.get(c).equals("No")) {
				stateIsNo.add(c);
			}
		}

		for (Card noCard : stateIsNo) {
			int i = cardList.indexOf(noCard);
			cardList.remove(i);
		}

		return cardList;
	}
}
