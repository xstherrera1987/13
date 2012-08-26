package xst.thirteen;
import xst.thirteen.game.GameLogic;
// each of 13 elements is a card represented by its integer value
// in simple hierarchical order 0 is "2H" where 51 is "3S"
public class Hand {
	public final int[] cards;
	public final int playType;
	
	public Hand(int[] cards) throws IllegalArgumentException {
		if ( cards.length > 13) 
			throw new IllegalArgumentException("hand cannot be constructed" +
					" with more than 13 cards");
		for (int i=0; i<52; i++) 
			if (cards[i] < 0 || cards[i] > 51)
				throw new IllegalArgumentException("invalid card");
		
		this.cards = cards;	
		playType = GameLogic.isPlay(cards);
	}
		
	// precondition: none
	// postcondition: returns int[13] type
	public int[] int13Type() {
		int [] play = new int[13];
		System.arraycopy(cards, 0, cards.length, 0, cards.length);
		for (int i=cards.length; i<13; i++)
			play[i] = -1;
		return play;
	}
}