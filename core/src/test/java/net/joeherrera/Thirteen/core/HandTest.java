package net.joeherrera.Thirteen.core;

import static org.junit.Assert.*;

import org.junit.Test;

import static net.joeherrera.Thirteen.core.Card.*;

@SuppressWarnings("static-method")
public class HandTest {
	
	@Test
	public void testEquals() {
		Hand h = new Hand(new Card[]{
			_2D, _AS, _9S, _8D, _7S, _6H, _3H
		}, 7);
		assertEquals(HAND_SIFTED_AND_SORTED, h);
	}
	
	@Test
	public void testSiftAndSort_sortOnly() {
		Hand h = new Hand(HAND_UNSORTED);
		h.siftAndSort();
		
		System.out.println("expected: " + HAND_SIFTED_AND_SORTED);
		System.out.println("h: " + h);
		assertEquals(HAND_SIFTED_AND_SORTED, h);
	}
	
	@Test
	public void testSiftAndSort_siftOnly() {
		Hand h = new Hand(HAND_UNSIFTED);
		h.siftAndSort();
		
		assertEquals(HAND_SIFTED_AND_SORTED, h);
	}
	
	@Test
	public void testSiftAndSort() {
		Hand h = new Hand(HAND_UNSIFTED_AND_UNSORTED);
		h.siftAndSort();
		
		assertEquals(HAND_SIFTED_AND_SORTED, h);
	}
	
	@Test
	public void testRemoveCards() {
		Hand h = new Hand(FULL_HAND);
		h.removeCards(REMOVE_INDICES);
		assertEquals(h, REMOVED_HAND);
	}
	
	/** Test Data for Hand **/
	// sifting and sorting
	public static final Hand HAND_UNSORTED = new Hand(new Card[] {
		_6H, _8D, _AS, _2D, _3H, _7S, _9S
	}, 7);
	public static final Hand HAND_UNSIFTED = new Hand(new Card[] {
		_2D, _AS, NULL_CARD, _9S, _8D, NULL_CARD, _7S, _6H, _3H, NULL_CARD
	}, 10);
	public static final Hand HAND_UNSIFTED_AND_UNSORTED = new Hand(new Card[] {
		NULL_CARD, _6H, _8D, _AS, NULL_CARD, _2D, _3H, _7S, _9S, NULL_CARD
	}, 10);
	public static final Hand HAND_SIFTED_AND_SORTED = new Hand(new Card[] {
		_2D, _AS, _9S, _8D, _7S, _6H, _3H
	}, 7);

	// remove cards
	public static final Hand FULL_HAND = new Hand(new Card[] {
		_2D, _AS, _KH, _KD, _KS, _9S, _8H, _8D, _7S, _6H, _4D, _3H, _3C
	}, 13);
	public static final int[] REMOVE_INDICES = { 0, 12, 5, 3, 8 };
	public static final Hand REMOVED_HAND = new Hand(new Card[] {
		_AS, _KH, _KS, _8H, _8D, _6H, _4D, _3H
	}, 8);
}
