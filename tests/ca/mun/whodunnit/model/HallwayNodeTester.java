package ca.mun.whodunnit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.mun.whodunnit.model.GNode;
import ca.mun.whodunnit.model.HallwayNode;
import ca.mun.whodunnit.model.Position;

public class HallwayNodeTester {
	Position p;
	HallwayNode h;
	HallwayNode n;
	HallwayNode e;
	HallwayNode s;
	HallwayNode w;

	@Before
	public void setUp() {
		p = new Position(8, 8);
		h = new HallwayNode(p);
		n = new HallwayNode(new Position(7, 8));
		e = new HallwayNode(new Position(8, 9));
		s = new HallwayNode(new Position(9, 8));
		w = new HallwayNode(new Position(8, 7));
		h.setNorth(n);
		h.setEast(e);
		h.setSouth(s);
		h.setWest(w);
	}

	@Test
	public void testHashCode() {
		assertEquals("For Hashcode for Position (8, 8) ", 808, h.hashCode(), 0);
	}

	@Test
	public void testHallwayNode() {
		assertTrue(h.getLocation().equals(p));
	}

	@Test
	public void testGetEast() {
		assertTrue(e.equals(h.getEast()));
	}

	@Test
	public void testGetNorth() {
		assertTrue(n.equals(h.getNorth()));
	}

	@Test
	public void testGetLocation() {
		assertTrue(h.getLocation().equals(p));
	}

	@Test
	public void testGetSouth() {
		assertTrue(s.equals(h.getSouth()));
	}

	@Test
	public void testGetWest() {
		assertTrue(w.equals(h.getWest()));
	}

	@Test
	public void testIsRoom() {
		assertFalse(h.isRoom());
	}

	@Test
	public void testSetEast() {
		h.setEast(w);
		assertTrue(w.equals(h.getEast()));
	}

	@Test
	public void testSetNorth() {
		h.setNorth(w);
		assertTrue(w.equals(h.getNorth()));
	}

	@Test
	public void testSetSouth() {
		h.setSouth(w);
		assertTrue(w.equals(h.getSouth()));
	}

	@Test
	public void testSetWest() {
		h.setWest(e);
		assertTrue(e.equals(h.getWest()));
	}

	@Test
	public void testGetEdges() {
		ArrayList<GNode> edges = new ArrayList<GNode>();
		edges.add(n);
		edges.add(e);
		edges.add(s);
		edges.add(w);
		assertTrue(edges.equals(h.getEdges()));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(h.equals(h));
	}

	@Test
	public void testToString() {
		assertTrue("Location: 8, 8".equals(h.toString()));
	}

}
