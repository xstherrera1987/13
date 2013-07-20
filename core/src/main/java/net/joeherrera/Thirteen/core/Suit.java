package net.joeherrera.Thirteen.core;

/**
 * Suit of the card. (weaker) ♠, ♣, ♦ ,♥ (stronger)
 */
public enum Suit {
	HEARTS('♥', 0), DIAMONDS('♦', 1), CLUBS('♣', 2), SPADES('♠', 3),
		// high value so null cards bubble towards the end during sorting
		NULL_SUIT('�', 10);
	final char name;
	final int val;

	Suit(final char name, final int val) {
		this.name = name;
		this.val = val;
	}
}
