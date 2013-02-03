package ca.mun.whodunnit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Turn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2442779604729232505L;
	private Player player;
	private boolean canMakeSuggestion, canRollDie;
	private int remainingSteps;

	private Player disprovingPlayer;
	// cards disproving player can use to disprove suggestion
	private ArrayList<Card> disprovingCards;

	private ArrayList<Path> selectablePaths;
	private ArrayList<Path> previousPaths;

	public Turn() {
		clear();
	}

	public void clear() {
		this.player = null;
		canMakeSuggestion = false;
		canRollDie = false;
		remainingSteps = 0;

		selectablePaths = new ArrayList<Path>();
		previousPaths = new ArrayList<Path>();
		disprovingPlayer = null;
		disprovingCards = new ArrayList<Card>();
	}

	public boolean canRollDie() {
		return canRollDie;
	}

	public ArrayList<Path> getPreviousPaths() {
		return previousPaths;
	}

	public boolean hasPreviousPath() {
		return previousPaths.size() > 0;
	}

	public Path getPreviousPath() {
		if (previousPaths.isEmpty()) {
			return null;
			// TODO: exception
		} else {
			return previousPaths.get(previousPaths.size() - 1);
		}
	}

	public Path removePreviousPath() {
		if (previousPaths.isEmpty()) {
			return null;
			// TODO: exception
		} else {
			return previousPaths.remove(previousPaths.size() - 1);
		}
	}

	public ArrayList<GNode> getPreviousLocations() {
		ArrayList<GNode> previousLocations = new ArrayList<GNode>();

		for (Path path : previousPaths) {
			for (int i = 0; i < path.size(); i++) {
				GNode n = path.getNode(i);
				if (!previousLocations.contains(n))
					previousLocations.add(n);
			}
		}

		return previousLocations;
	}

	public ArrayList<GNode> getSelectableNodes() {
		ArrayList<GNode> selectableNodes = new ArrayList<GNode>();

		for (Path p : selectablePaths) {
			for (int i = 1; i < p.size(); i++) {
				GNode n = p.getNode(i);
				if (!selectableNodes.contains(n)) {
					selectableNodes.add(n);
				}
			}
		}

		return selectableNodes;
	}

	public void addPreviousPath(Path p) {
		previousPaths.add(p);
	}

	public void setCanRollDie(boolean canRollDie) {
		this.canRollDie = canRollDie;
	}

	public boolean canMakeSuggestion() {
		return canMakeSuggestion;
	}

	public void setCanMakeSuggestion(boolean canMakeSuggestion) {
		this.canMakeSuggestion = canMakeSuggestion;
	}

	public ArrayList<Path> getSelectablePaths() {
		return selectablePaths;
	}

	public ArrayList<Card> getDisprovingCards() {
		return disprovingCards;
	}

	public void setDisprovingCards(ArrayList<Card> disprovingCards) {
		this.disprovingCards = disprovingCards;
	}

	public Player getDisprovingPlayer() {
		return disprovingPlayer;
	}

	public void setDisprovingPlayer(Player disprovingPlayer) {
		this.disprovingPlayer = disprovingPlayer;
	}

	/**
	 * @return the remainingSteps
	 */
	public int getRemainingSteps() {
		return remainingSteps;
	}

	/**
	 * @param remainingSteps
	 *            the remainingSteps to set
	 */
	public void setRemainingSteps(int remainingSteps) {
		this.remainingSteps = remainingSteps;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param p
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean canMove() {
		if (player.getLocation().isRoom() && (previousPaths.size() >= 1))
			return false;
		if (this.remainingSteps <= 0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Current player: " + this.player.getCharacter()
				+ "\nCanMakeSuggestion: " + this.canMakeSuggestion
				+ "\nCanRollDie: " + this.canRollDie + "\nRemainingSteps: "
				+ this.remainingSteps + "\nSelectablePaths: "
				+ this.selectablePaths + "\nPreviousPaths: "
				+ this.previousPaths + "\nDisprovingPlayer: "
				+ (this.disprovingPlayer != null);
	}
}
