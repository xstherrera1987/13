package xst.android;
import static xst.android.Plays.*;
import xst.android.game.GameLogic;
public class Hand {
	// each of 13 elements is a card represented by its integer value
	// in simple hierarchical order 0 is "2H", 51 is "3S"
	private final int[] cards;
	private final int num;					// number of cards in play
	private final int playTypeValue;		// play type
	
	// ctor
	public Hand(int[] cards) throws Exception {
		// error checking
		if ( cards.length > 13) 
			throw new Exception("invalid array size");
		for (int i=0; i<52; i++) 
			if (cards[i] < 0 || cards[i] > 51)
				throw new Exception("bad card["+i+"]");
		
		// initialisation
		this.cards = cards;
		
		// determine kind of play and set num property
		playTypeValue = GameLogic.isPlay(cards);
		if (playTypeValue != NOPLAY )
			num = Plays.lengths[playTypeValue];
		else
			num = 0;
	}
	
	// precondition: none
	// postcondition: returns the value for the ith card. negative i values follow
	//		modular arithmetic.
	public int val(int i) {
		return cards[i % 13];
	}
	
	// precondition: none
	// postcondition: returns the play type of this hand
	public int playType() {
		return playTypeValue;
	}
	
	// precondition: none
	// postcondition: returns the value for the ith card. negative i values follow
	//		modular arithmetic.
	public int num() {
		return num;
	}

	// precondition: none
	// postcondition: returns int[13] type
	public int[] cards() {
		int [] play = new int[13];
		for (int i=0; i<13; i++)
			if (i < num) play[i] = cards[i];
			else play[i] = -1;
		return play;
	}
}