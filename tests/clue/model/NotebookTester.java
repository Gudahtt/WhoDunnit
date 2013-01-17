package clue.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class NotebookTester {
	private Notebook notebook;
	private ArrayList<Card> cardList; // list of all cards
	private HashMap<Card, String> testStateList; // default statelist that
													// should be made by
													// notebook

	@Before
	public void setUp() {
		notebook = new Notebook();
		notebook.setText("Testing");
		cardList = new ArrayList<Card>();
		cardList.add(Card.BALL_ROOM);
		cardList.add(Card.BILLIARD_ROOM);
		cardList.add(Card.CANDLESTICK);
		cardList.add(Card.COLONEL_MUSTARD);
		cardList.add(Card.CONSERVATORY);
		cardList.add(Card.DINING_ROOM);
		cardList.add(Card.HALL);
		cardList.add(Card.KITCHEN);
		cardList.add(Card.KNIFE);
		cardList.add(Card.LEAD_PIPE);
		cardList.add(Card.LIBRARY);
		cardList.add(Card.LOUNGE);
		cardList.add(Card.MISS_SCARLET);
		cardList.add(Card.MR_GREEN);
		cardList.add(Card.MRS_PEACOCK);
		cardList.add(Card.MRS_WHITE);
		cardList.add(Card.PROFESSOR_PLUM);
		cardList.add(Card.REVOLVER);
		cardList.add(Card.ROPE);
		cardList.add(Card.STUDY);
		cardList.add(Card.WRENCH);

		testStateList = new HashMap<Card, String>();
		for (Card c : cardList) {
			testStateList.put(c, "Unchecked");
		}
	}

	public void testNotebook() {
		new Notebook();
		fail("Constructing failed");
	}

	@Test
	public void testChangeState() {
		Card c = Card.BALL_ROOM;
		String s = "No";
		notebook.changeState(c, s);
		assertTrue(notebook.getStateList().get(c).equals("No"));
	}

	@Test
	public void testSize() {
		assertEquals("stateList size: ", 21, notebook.size());

	}

	@Test
	public void testGetText() {
		assertTrue(notebook.getText().equals("Testing"));

	}

	@Test
	public void testSetText() {
		notebook.setText("test");
		assertTrue(notebook.getText().equals("test"));

	}

	@Test
	public void testGetStateList() {
		HashMap<Card, String> stateList = notebook.getStateList();
		for (Card c : cardList) {
			assertTrue(stateList.get(c).equals("Unchecked")); // check to see if
																// the default
																// statelist is
																// returned
		}

	}

	@Test
	public void testGetViableCards() {
		notebook.changeState(Card.BALL_ROOM, "No");
		ArrayList<Card> viableCards = notebook.getViableCards(Card.Type.ROOM);
		assertTrue(viableCards.size() == 8);
		assertTrue(!viableCards.contains(Card.BALL_ROOM));
	}

}
