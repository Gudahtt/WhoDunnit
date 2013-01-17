package clue.model;

import java.util.ArrayList;

public interface GNode {

	public GNode getNorth();

	public GNode getEast();

	public GNode getSouth();

	public GNode getWest();

	public ArrayList<GNode> getEdges();

	public void setNorth(GNode g);

	public void setEast(GNode g);

	public void setSouth(GNode g);

	public void setWest(GNode g);

	public boolean isRoom();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

}
