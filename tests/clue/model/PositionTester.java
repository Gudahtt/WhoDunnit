package clue.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class PositionTester {
	Position p;
	Random rand;

	@Before
	public void setUp() {
		p = new Position(10, 20);
		rand = new Random();
	}

	@Test
	public void testPosition() {
		final int x = rand.nextInt();
		final int y = rand.nextInt();
		Position b = new Position(x, y);
		assertEquals("For x: " + x, x, b.getX(), 0);
		assertEquals("For y: " + y, y, b.getY(), 0);
	}

	@Test
	public void testHashCode() {
		assertEquals("For Hashcode (10, 20) ", 1020, p.hashCode(), 0);
	}

	@Test
	public void testGetX() {
		assertEquals("For getX: ", 10, p.getX(), 0);
	}

	@Test
	public void testGetY() {
		assertEquals("For getY: ", 20, p.getY(), 0);
	}

	@Test
	public void testEqualsObject() {
		Position p2 = new Position(10, 20);

		assertTrue(p.equals(p2));
	}

	@Test
	public void testToString() {
		assertTrue("Position: 10, 20".equals(p.toString()));
	}

}
