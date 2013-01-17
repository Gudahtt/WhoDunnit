package clue.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Path extends ArrayList<GNode> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4784108012717894566L;

	public Path(GNode origin) {
		this.add(origin);
	}

	@Override
	public Path clone() {
		Path clone = new Path(this.get(0));

		for (int i = 1; i < this.size(); i++) {
			clone.add(this.get(i));
		}

		return clone;
	}

	public GNode getOrigin() {
		return this.get(0);
	}

	public GNode getDestination() {
		return this.get(this.size() - 1);
	}

	public GNode getNode(int i) {
		return this.get(i);
	}

	public void trimPath(GNode n) {
		int trimLength = this.indexOf(n);

		for (int i = 0; i < trimLength; i++) {
			this.remove(0);
		}
	}
}
