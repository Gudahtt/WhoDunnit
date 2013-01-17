/**
 * 
 */
package clue.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mjs221
 * 
 */

public class ComputerPlayer implements Player, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3613609589686552706L;
	private Card character;
	private boolean alive, moved;
	private Difficulty difficulty;
	private GNode location;
	private ArrayList<Card> hand;
	private Notebook notes;

	// AI data
	private AIData aIData;

	/**
	 * @param character
	 * @param d
	 */
	public ComputerPlayer(Card character, Difficulty difficulty) {
		this.character = character;
		this.alive = true;
		this.moved = false;
		this.difficulty = difficulty;

		this.hand = new ArrayList<Card>();
		this.notes = new Notebook();

		this.location = null;

		this.aIData = new AIData();
	}

	@Override
	public int compareTo(Player p) {
		return this.character.compareTo(p.getCharacter());
	}

	@Override
	public boolean getAlive() {
		return this.alive;
	}

	@Override
	public Card getCharacter() {
		return character;
	}

	@Override
	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public GNode getLocation() {
		return location;
	}

	@Override
	public Notebook getNotebook() {
		return notes;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
	public void setAlive(boolean alive) {
		this.alive = alive;

	}

	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	@Override
	public void setHand(ArrayList<Card> cards) {
		hand = cards;
	}

	@Override
	public void setLocation(GNode p) {
		location = p;
	}

	@Override
	public boolean wasMoved() {
		return moved;
	}

	@Override
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public AIData getAIData() {
		return aIData;
	}

}
