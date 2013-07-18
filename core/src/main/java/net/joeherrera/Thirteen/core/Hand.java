package net.joeherrera.Thirteen.core;

import static net.joeherrera.Thirteen.core.Card.NULL_CARD;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection (container) of cards. Used to holds the cards that a player owns.
 * This class also holds other information related to the hand, such as the 
 * plays that are possible to make.
 */
public class Hand {
	// a list of possible plays that this hand can make
	List<Play> plays;
	// the cards in this hand
	Card[] hand;
	int length;

	public Hand(final Card[] hand, final int length) {
		this.hand = hand;
		this.length = length;
		this.plays = new ArrayList<>();
	}

	/**
	 * remove every card whos index is in indices from this hand
	 * 
	 * @param indices
	 *            indices of cards to be removed
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
		int i, j;
		Card tempCard;

		for (i = 1; i < 13; i++) {
			tempCard = this.hand[i];
			j = i;
			while (j > 0 && this.hand[j - 1].val > tempCard.val) {
				this.hand[j] = this.hand[j - 1];
				j--;
			}
			this.hand[j] = tempCard;
		}

		// find index of last valid card, length is 1 more than that
		for (i = 0; NULL_CARD != this.hand[i]; i++) {
			// TODO implement
		}

		this.length = i + 1;
	}
}
