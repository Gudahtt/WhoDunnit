package ca.mun.whodunnit.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.mun.whodunnit.model.Card;
import ca.mun.whodunnit.model.GNode;
import ca.mun.whodunnit.model.RoomNode;

public class RoomNodeTester {

	private RoomNode testNode;
	private RoomNode eastNode;
	private RoomNode westNode;
	private RoomNode southNode;
	private RoomNode northNode;
	
	@Before
	public void setUp(){
		testNode = new RoomNode(Card.BALL_ROOM);
		eastNode = new RoomNode(Card.BALL_ROOM);
		westNode = new RoomNode(Card.BALL_ROOM);
		southNode = new RoomNode(Card.BALL_ROOM);
		northNode = new RoomNode(Card.BALL_ROOM);
		testNode.setEast(eastNode);
		testNode.setWest(westNode);
		testNode.setSouth(southNode);
		testNode.setNorth(northNode);
	}
	
	@Test
	public void testRoomNode() {
		new RoomNode(Card.BALL_ROOM);
	}

	@Test
	public void testGetEast() {
		assertTrue(testNode.getEast().equals(eastNode));
	}

	@Test
	public void testSetEast() {
		testNode.setEast(westNode);
		assertTrue(testNode.getEast().equals(westNode));
	}

	@Test
	public void testGetWest() {
		assertTrue(testNode.getWest().equals(westNode)); // TODO
	}

	@Test
	public void testSetWest() {
		testNode.setWest(westNode);
		assertTrue(testNode.getWest().equals(eastNode));
	}

	@Test
	public void testGetNorth() {
		assertTrue(testNode.getNorth().equals(northNode));
	}

	@Test
	public void testSetNorth() {
		testNode.setNorth(westNode);
		assertTrue(testNode.getNorth().equals(westNode));
	}

	@Test
	public void testGetSouth() {
		assertTrue(testNode.getSouth().equals(southNode));
	}

	@Test
	public void testSetSouth() {
		testNode.setSouth(westNode);
		assertTrue(testNode.getSouth().equals(westNode));
	}

	@Test
	public void testIsRoom() {
		assertTrue(testNode.isRoom());
	}

	@Test
	public void testGetRoom() {
		assertTrue(testNode.getRoom().equals(Card.BALL_ROOM));
	}

	@Test
	public void testGetEdges() {
		ArrayList<GNode> edges = testNode.getEdges();
		assertTrue(edges.get(0).equals(northNode));
		assertTrue(edges.get(1).equals(eastNode));
		assertTrue(edges.get(2).equals(southNode));
		assertTrue(edges.get(3).equals(westNode));
	}

	@Test
	public void testEqualsObject() {
		assertTrue(testNode.equals(testNode));
	}


}
