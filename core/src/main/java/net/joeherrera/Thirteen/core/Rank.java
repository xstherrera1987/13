package net.joeherrera.Thirteen.core;

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
