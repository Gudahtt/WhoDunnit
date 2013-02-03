package ca.mun.whodunnit.model;

import java.util.ArrayList;

public interface Player extends Comparable<Player> {
	public ArrayList<Card> getHand();

	public void setHand(ArrayList<Card> cards);

	@Override
	public int compareTo(Player p);

	public boolean getAlive();

	public void setAlive(boolean alive);

	public Card getCharacter();

	public GNode getLocation();

	public void setLocation(GNode p);

	public Notebook getNotebook();

	public boolean isHuman();

	public boolean wasMoved();

	public void setMoved(boolean moved);

}
