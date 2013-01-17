package clue.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardTester {

	@Test
	public void testGetCardArray() {
		assertEquals("For suspect: ", Card.MISS_SCARLET.hashCode(),
				Card.getCardArray(Card.Type.SUSPECT)[0].hashCode(), 0);
		assertEquals("For weapon: ", Card.KNIFE.hashCode(),
				Card.getCardArray(Card.Type.WEAPON)[0].hashCode(), 0);
		assertEquals("For room: ", Card.HALL.hashCode(),
				Card.getCardArray(Card.Type.ROOM)[0].hashCode(), 0);
	}

	@Test
	public void testGetTypes() {
		assertEquals("For suspect: ", Card.MISS_SCARLET.hashCode(), Card
				.getTypes(Card.Type.SUSPECT).get(0).hashCode(), 0);
		assertEquals("For weapon: ", Card.KNIFE.hashCode(),
				Card.getTypes(Card.Type.WEAPON).get(0).hashCode(), 0);
		assertEquals("For room: ", Card.HALL.hashCode(),
				Card.getTypes(Card.Type.ROOM).get(0).hashCode(), 0);
	}

	@Test
	public void testGetAllCards() {
		assertEquals("For MISS_SCARLET: ", Card.MISS_SCARLET.hashCode(), Card
				.getAllCards().get(0).hashCode(), 0);
		assertEquals("For KNIFE: ", Card.KNIFE.hashCode(), Card.getAllCards()
				.get(6).hashCode(), 0);
		assertEquals("For HALL: ", Card.HALL.hashCode(), Card.getAllCards()
				.get(12).hashCode(), 0);
	}

	@Test
	public void testGetCard() {
		assertEquals("For MISS_SCARLET: ", Card.MISS_SCARLET.hashCode(), Card
				.getCard("Miss Scarlet").hashCode(), 0);
		assertEquals("For KNIFE: ", Card.KNIFE.hashCode(), Card
				.getCard("Knife").hashCode(), 0);
		assertEquals("For HALL: ", Card.HALL.hashCode(), Card.getCard("Hall")
				.hashCode(), 0);
	}

	@Test
	public void testToString() {
		assertEquals("For MISS_SCARLET: ", "Miss Scarlet".hashCode(),
				Card.MISS_SCARLET.toString().hashCode(), 0);
		assertEquals("For KNIFE: ", "Knife".hashCode(), Card.KNIFE.toString()
				.hashCode(), 0);
		assertEquals("For HALL: ", "Hall".hashCode(), Card.HALL.toString()
				.hashCode(), 0);
	}
}
