package net.joeherrera.Thirteen.core;

import static net.joeherrera.Thirteen.core.Card.NULL_CARD;

import java.util.List;

/**
 * A collection (container) of cards. Used to holds the cards that a player owns.
 * This class also holds other information related to the hand, such as the 
 * plays that subsets of cards from this hand can make.
 */
public class Hand {
	// a list of possible plays that this hand can make
	List<Play> plays;
	// the cards in this hand
	final Card[] hand;
	int length;

	public Hand(final Card[] hand, final int length) {
		this.hand = hand;
		this.length = length;
	}
	
	public Hand(Hand otherHand) {
		this.hand = new Card[ otherHand.length ];
		this.length = otherHand.length;		
		System.arraycopy(otherHand.hand, 0, this.hand, 0, otherHand.length);			
	}
	
	/**
	 * Two hands are equal if they contain the same cards in the same order, 
	 * regardless of the backing capacity of the Card[].
	 */
	@Override public boolean equals(Object other) {
		if ( !other.getClass().equals(Hand.class) ) 
			return false;
		
		Hand otherHand = (Hand) other;
		final int length = this.length;
		if (otherHand.length != length) 
			return false;
		
		final Card[] hand = this.hand;
		for (int i=0; i<length; i++) {
			if (otherHand.hand[i] != hand[i])
				return false;
		}
		
		return true;
	}

	/**
	 * remove every card whos index is in indices from this hand and then sort again.
	 * 
	 * @param indices of cards to be removed
	 */
	public void removeCards(final int[] indices) {
		for (final int i : indices)
			this.hand[i] = NULL_CARD;
		this.siftAndSort();
	}

	/**
	 * sorts cards in hand so stronger ones are in lower indices. in-place
	 * insertion sort.
	 */
	void siftAndSort() {
		int i, hole;
		Card insertCard;

		final int length = this.length;
		for (i = 1; i < length; i++) {
			insertCard = this.hand[i];
			
			hole = i;
			while (hole > 0 && this.hand[hole - 1].val > insertCard.val) {
				this.hand[hole] = this.hand[hole - 1];
				hole--;
			}
			
			this.hand[hole] = insertCard;
		}

		// find index of last valid card, new length is 1 more than that
		for (i = 0; i < length && NULL_CARD != this.hand[i]; i++);
		this.length = i;
	}

	@Override
	public String toString() {
		return Card.cardArrayToString(this.hand);
	}
}
