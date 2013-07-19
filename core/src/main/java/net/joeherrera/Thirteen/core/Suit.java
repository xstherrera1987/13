package net.joeherrera.Thirteen.core;

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
