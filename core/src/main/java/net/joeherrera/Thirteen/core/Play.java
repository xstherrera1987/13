package net.joeherrera.Thirteen.core;

/**
 * A Play refers to a pair of Rule and Card[] that together will "make a play"
 * on the board.  A Rule is the general type like Pair or Straight while a Play
 * is an instance of those like [2♥, 2♦]
 */
public class Play {
	public static final Play SKIP_TURN = new Play(null, null);
	
	/**
	 * the rule that this play can satisfy
	 */
	final Rule rule;
	
	/**
	 * the cards that comprise the play
	 */
	final Card[] cards;
	
	public Play(final Rule rule, final Card[] cards) {
		this.rule = rule;
		this.cards = cards;
	}
}
