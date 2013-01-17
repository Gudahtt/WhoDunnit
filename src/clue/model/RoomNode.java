package clue.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RoomNode implements GNode, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8661794714355771765L;

	GNode east;

	GNode west;

	GNode north;

	GNode south;

	Card room;

	public RoomNode(Card room) {
		east = null;
		west = null;
		north = null;
		south = null;
		this.room = room;
	}

	/**
	 * @return the east
	 */
	@Override
	public GNode getEast() {
		return east;
	}

	/**
	 * @param east
	 *            the east to set
	 */
	@Override
	public void setEast(GNode east) {
		this.east = east;
	}

	/**
	 * @return the west
	 */
	@Override
	public GNode getWest() {
		return west;
	}

	/**
	 * @param west
	 *            the west to set
	 */
	@Override
	public void setWest(GNode west) {
		this.west = west;
	}

	/**
	 * @return the north
	 */
	@Override
	public GNode getNorth() {
		return north;
	}

	/**
	 * @param north
	 *            the north to set
	 */
	@Override
	public void setNorth(GNode north) {
		this.north = north;
	}

	/**
	 * @return the south
	 */
	@Override
	public GNode getSouth() {
		return south;
	}

	/**
	 * @param south
	 *            the south to set
	 */
	@Override
	public void setSouth(GNode south) {
		this.south = south;
	}

	/**
	 * @return the room
	 */
	@Override
	public boolean isRoom() {
		return true;
	}

	public Card getRoom() {
		return this.room;
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
		if (!(node instanceof RoomNode)) {
			return false;
		}

		RoomNode hnode = (RoomNode) node;

		return hnode.getRoom() == this.room;
	}

	@Override
	public int hashCode() {
		return room.hashCode();
	}

	@Override
	public String toString() {
		return "Room: " + room.toString();
	}

}
