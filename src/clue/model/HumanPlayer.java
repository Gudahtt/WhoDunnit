package clue.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HumanPlayer implements Player, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4763151982795823130L;
	private ArrayList<Card> hand;
	private Card character;
	private GNode location;
	private Notebook notes;
	private boolean alive, moved;

	@Override
	public boolean getAlive() {
		return alive;
	}

	public HumanPlayer(Card character, boolean alive) {

		this.character = character;
		notes = new Notebook();
		hand = new ArrayList<Card>();
		this.alive = alive;
		this.moved = false;

		location = null;
	}

	@Override
	public int compareTo(Player p) {
		return character.compareTo(p.getCharacter());
	}

	@Override
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * @return the cards
	 */
	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * @param cards
	 *            the cards to set
	 */
	@Override
	public void setHand(ArrayList<Card> cards) {
		this.hand = cards;
	}

	/**
	 * @return the notes
	 */
	@Override
	public Notebook getNotebook() {
		return notes;
	}

	/**
	 * @return the character
	 */
	@Override
	public Card getCharacter() {
		return character;
	}

	/**
	 * @return the position
	 */
	@Override
	public GNode getLocation() {
		return location;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	@Override
	public void setLocation(GNode location) {
		this.location = location;
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public String toString() {
		return "Hand: " + hand.toString() + ", Character: "
				+ character.toString() + ", Alive: " + alive + ", Notes: "
				+ notes.toString();
	}

	@Override
	public boolean wasMoved() {
		return moved;
	}

	@Override
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
