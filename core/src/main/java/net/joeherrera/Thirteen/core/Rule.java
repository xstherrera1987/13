package net.joeherrera.Thirteen.core;

/**
 * A Rule represents one of the possible play types in the game.  This class
 * enumerates the rules and provides static methods for determining if a Card[]
 * matches a particular Rule.
 */
public enum Rule {
	/** miscellaneous rules **/
	FIRST_PLAY("First Play"), NULL_RULE("NULL RULE"),
	
	/** multiple cards of equal rank rules **/
	PAIR("Pair"), TRIPS("Three of a Kind"),
	// four-of-a-kind defeats a deuce
	QUADS("Four of a Kind"),
	
	/** sequential series rules **/
	// highest possible ending card in a straight is the A (no 2's in a straigt)
	STRAIGHT_3("3-Card Straight"), STRAIGHT_4("4-Card Straight"),
	STRAIGHT_5("3-Card Straight"), STRAIGHT_6("4-Card Straight"),
	STRAIGHT_7("3-Card Straight"), STRAIGHT_8("4-Card Straight"),
	STRAIGHT_9("3-Card Straight"), STRAIGHT_10("4-Card Straight"),
	STRAIGHT_11("3-Card Straight"), STRAIGHT_12("4-Card Straight"),
	// 13-card-straight is unbeatable except by another of higher suit
	STRAIGHT_13("13-Card Straight"),
	
	/** several pairs in an unbroken sequence **/
	// 3-pair sequence defeats a deuce 
	SEQ_PAIRS_3("Sequence of 3 Pairs"),
	// 4-pair sequence defeats 2 deuces
	SEQ_PAIRS_4("Sequence of 4 Pairs");
	
	Rule(String name) {
		this.name = name;
	}
	
	// the name of the rule used to display it
	final String name;
	
	/**
	 *  returns true if cards contains the 3♠
	 * @param cards
	 * @return true if cards contains the 3♠
	 */
	public static boolean containsFirstCard(final Card[] cards) {
		// TODO implement
		
		return false;
	}
}
