package ca.mun.whodunnit.model;

import java.io.Serializable;

public final class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606530683262469973L;
	private int x;
	private int y;

	public Position(int i, int j) {
		x = i;
		y = j;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object p) {
		if (p.getClass() != getClass())
			return false;

		Position pos = (Position) p;
		if (this.hashCode() == pos.hashCode())
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return x * 100 + y;
	}

	@Override
	public String toString() {
		return "Position: " + x + ", " + y;
	}

}
