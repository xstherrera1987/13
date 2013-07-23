package net.joeherrera.Thirteen.core;
import static net.joeherrera.Thirteen.core.Rank.*;
import static net.joeherrera.Thirteen.core.Suit.*;

/**
 * Card is identified by a Rank and Suit. This implementation does not support
 * multiple decks.
 */
public enum Card {	
	// null card is used as placeholder
	NULL_CARD(Rank.NULL_RANK, Suit.NULL_SUIT),
	// hearts
	_2H(TWO ,HEARTS), _AH(ACE , HEARTS), _KH(KING , HEARTS), _QH(QUEEN , HEARTS), 
	_JH(JACK , HEARTS), _10H(TEN , HEARTS), _9H(NINE , HEARTS), _8H(EIGHT , HEARTS), 
	_7H(SEVEN , HEARTS), _6H(SIX , HEARTS), _5H(FIVE , HEARTS), _4H(FOUR , HEARTS),
	_3H(THREE , HEARTS),
	// diamonds
	_2D(TWO ,DIAMONDS), _AD(ACE , DIAMONDS), _KD(KING , DIAMONDS), _QD(QUEEN , DIAMONDS), 
	_JD(JACK , DIAMONDS), _10D(TEN , DIAMONDS), _9D(NINE , DIAMONDS), _8D(EIGHT , DIAMONDS), 
	_7D(SEVEN , DIAMONDS), _6D(SIX , DIAMONDS), _5D(FIVE , DIAMONDS), _4D(FOUR , DIAMONDS),
	_3D(THREE , DIAMONDS),
	// clubs
	_2C(TWO ,CLUBS), _AC(ACE , CLUBS), _KC(KING , CLUBS), _QC(QUEEN , CLUBS), 
	_JC(JACK , CLUBS), _10C(TEN , CLUBS), _9C(NINE , CLUBS), _8C(EIGHT , CLUBS), 
	_7C(SEVEN , CLUBS), _6C(SIX , CLUBS), _5C(FIVE , CLUBS), _4C(FOUR , CLUBS),
	_3C(THREE , CLUBS),
	// spades
	_2S(TWO ,SPADES), _AS(ACE , SPADES), _KS(KING , SPADES), _QS(QUEEN , SPADES), 
	_JS(JACK , SPADES), _10S(TEN , SPADES), _9S(NINE , SPADES), _8S(EIGHT , SPADES), 
	_7S(SEVEN , SPADES), _6S(SIX , SPADES), _5S(FIVE , SPADES), _4S(FOUR , SPADES),
	_3S(THREE , SPADES);
	
	/**
	 * The full deck of cards ordered by strength with lower indices stronger.
	 */
	public static final Card[] DECK = getDeck();
	
	final Rank rank;
	final Suit suit;
	final int val;
	Card(final Rank r, final Suit s) {
		this.rank = r;
		this.suit = s;
		this.val = cardVal(r, s);
	}

	/**
	 * build and return a new deck
	 * 
	 * @return standard 52 card deck
	 */
	public static Card[] getDeck() {
		final Card[] cards = Card.values();
		
		final Card[] deck = new Card[52];
		for (Card c : cards) {
			if (NULL_CARD != c) {
				int index = c.val;
				deck[index] = c;
			}
		}
		return deck;
	}
	
	/**
	 * The value of a card is used to order cards by relative individual strength.
	 * @param r the rank of the card
	 * @param s the suit of the card
	 * @return the integer value of this card
	 */
	public static int cardVal(final Rank r, final Suit s) {
		return (r.val * 4) + s.val;
	}
	
	/**
	 * Retrieve a card from a Rank and Suit
	 * @param rank
	 * @param suit
	 * @return the card
	 */
	public static Card getCard(final Rank rank, final Suit suit) {
		return DECK[ cardVal(rank,suit) ];
	}

	@Override 
	public String toString() {
		return this.rank.name + this.suit.name;
	}
	
	/**
	 * Stringify a Card[] for printing
	 * @param cards the cards to print
	 * @return a string representing the cards
	 */
	public static String cardArrayToString(final Card[] cards) {
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		
		Card c;
		boolean lastIteration = false;
		for (int i=0; !lastIteration; i++) {
			lastIteration = (i == cards.length - 1);
			c = cards[i];
			sb.append(c.toString());
			if ( !lastIteration )
				sb.append(", ");
		}
		sb.append(']');
		
		return sb.toString();
	}
}
