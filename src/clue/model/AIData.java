package clue.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AIData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4335785116124470586L;
	private Card goalRoom; // room computer player is trying to reach
	private HashMap<Card, ArrayList<Card>> shown; // keeps track of which cards
													// have been shown to which
													// players

	public AIData() {
		goalRoom = null;
		shown = new HashMap<Card, ArrayList<Card>>();
		shown.put(Card.COLONEL_MUSTARD, new ArrayList<Card>());
		shown.put(Card.MISS_SCARLET, new ArrayList<Card>());
		shown.put(Card.MR_GREEN, new ArrayList<Card>());
		shown.put(Card.MRS_PEACOCK, new ArrayList<Card>());
		shown.put(Card.MRS_WHITE, new ArrayList<Card>());
		shown.put(Card.PROFESSOR_PLUM, new ArrayList<Card>());
	}

	public Card getGoalRoom() {
		return goalRoom;
	}

	public void setGoalRoom(Card room) {
		this.goalRoom = room;
	}

	public HashMap<Card, ArrayList<Card>> getShown() {
		return shown;
	}

	public void setShown(HashMap<Card, ArrayList<Card>> shown) {
		this.shown = shown;
	}
}
