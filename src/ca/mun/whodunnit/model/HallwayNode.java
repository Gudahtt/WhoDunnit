package ca.mun.whodunnit.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HallwayNode implements GNode, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8302543857081052025L;

	GNode north;

	GNode south;

	GNode east;

	GNode west;

	Position location;

	public HallwayNode(Position location) {
		this.location = location;

		north = null;
		east = null;
		south = null;
		west = null;
	}

	@Override
	public GNode getEast() {
		return east;
	}

	@Override
	public GNode getNorth() {
		return north;
	}

	/**
	 * @return the location
	 */
	public Position getLocation() {
		return location;
	}

	@Override
	public GNode getSouth() {
		return south;
	}

	@Override
	public GNode getWest() {
		return west;
	}

	@Override
	public boolean isRoom() {
		return false;
	}

	@Override
	public void setEast(GNode g) {
		east = g;
	}

	@Override
	public void setNorth(GNode g) {
		north = g;
	}

	@Override
	public void setSouth(GNode g) {
		south = g;
	}

	@Override
	public void setWest(GNode g) {
		west = g;
	}

	@Override
	public ArrayList<GNode> getEdges() {
		ArrayList<GNode> edges = new ArrayList<GNode>();

		if (north != null)
			edges.add(north);
		if (east != null)
			edges.add(east);
		if (south != null)
			edges.add(south);
		if (west != null)
			edges.add(west);

		return edges;
	}

	@Override
	public boolean equals(Object node) {
		if (!(node instanceof HallwayNode)) {
			return false;
		}

		HallwayNode hnode = (HallwayNode) node;

		return hnode.getLocation().equals(this.getLocation());
	}

	@Override
	public String toString() {
		return "Location: " + this.getLocation().getX() + ", "
				+ this.getLocation().getY();
	}

	@Override
	public int hashCode() {
		return this.getLocation().hashCode();
	}
}
