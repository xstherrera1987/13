package net.joeherrera.Thirteen.core;

/**
 * A Play refers to a pair of Rule and Card[] that together will "make a play"
 * on the board.  A Rule is the general type like Pair or Straight while a Play
 * is an instance of those like [2♥, 2♦]
 */
public class Play {
	// the rule that this play can satisfy
	final Rule rule;
	// the cards that comprise the play
	final Card[] cards;
	
	public Play(Rule rule, Card[] cards) {
		this.rule = rule;
		this.cards = cards;
	}
}
