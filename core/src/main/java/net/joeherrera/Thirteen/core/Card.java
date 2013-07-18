package net.joeherrera.Thirteen.core;

/**
 * Card is identified by a Rank and Suit
 */
public class Card {

	/**
	 * Suit of the card. (weaker) ♠, ♣, ♦ ,♥ (stronger)
	 */
	public enum Suit {
		HEARTS('♥', 0), DIAMONDS('♦', 1), CLUBS('♣', 2), SPADES('♠', 3), 
			NULL_SUIT('�', -1);
		final char name;
		final int val;

		Suit(final char name, final int val) {
			this.name = name;
			this.val = val;
		}
	}

	/**
	 * Rank of the card. (weaker) 3,4,5, ..., K,A,2 (stronger)
	 */
	public enum Rank {
		THREE("3", 12), FOUR("4", 11), FIVE("5", 10), SIX("6", 9), SEVEN("7", 8), 
		EIGHT("8", 7), NINE("9", 6), TEN("10", 6), JACK("J", 4), QUEEN("Q", 3), 
		KING("K", 2), ACE("A", 1), TWO("2", 0), NULL_RANK("�", -1);

		final String name;
		final int val;

		Rank(final String name, final int val) {
			this.val = val;
			this.name = name;
		}
	}

	final Rank rank;
	final Suit suit;
	final int val;

	Card(final Rank r, final Suit s) {
		this.rank = r;
		this.suit = s;
		this.val = (s.val * 13) + r.val;
	}

	/**
	 * build and return a new deck
	 * 
	 * @return standard 52 card deck
	 */
	public static Card[] getDeck() {
		final Card[] deck = new Card[52];
		int val = 0;
		for (final Suit s : Suit.values())
			for (final Rank r : Rank.values()) {
				deck[val++] = new Card(r, s);
			}
		return deck;
	}

	public static final Card NULL_CARD = new Card(Rank.NULL_RANK, Suit.NULL_SUIT);
	
	@Override public String toString() {
		return this.rank.name + this.suit.name;
	}
}
