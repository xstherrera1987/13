package xst.android.game;
import static xst.android.ControlCodes.*;
import java.util.Random;

public class GameState {
	// game state and hands data
	final byte[] state;					// the state of each of the 52 cards
	final byte[][] hands;				// this data structure should be synchronized with state[]
	final byte[] lastCard;		 		// index of last card in each player's hand
	Random r;
	
	public GameState() {
		state = new byte[52];
		hands = new byte[4][13];
		lastCard = new byte[4];
		r = new Random();
	}
	
	// precondition: none
	// postcondition: reads state[] and fills hands[], updates lastCard[]
	public void calculateHand(int player) {
		// update hands[] from state[]
		byte count=0;
		for (byte i=0; i<52; i++)
			if (state[i] == player)
				hands[player][count++] = i;
		
		// pad the rest of hands[] with NULLCARD
		lastCard[player] = count;
		for (int i=count; i<13; i++) {
			hands[player][count++] = NULLCARD;
		}
		// update lastCard[]
		lastCard[player] = (byte) (count-1);
	}
	
	// precondition: none
	// postcondition: returns true if all cards have been shed by player
	public boolean checkWin(int player) {
		boolean win = true;
		for (int i=0; i<52 && win; i++) 
			if (state[i] == player) win = false;
		return win;
	}
	
	// precondition: the play has been verified as defeating the current play
	// postcondition: updates state[], hands[] for making this play
	public void makePlay(int[] cards, int playerNumber) {
		// all marked as current are now marked as old
		for (int i=0; i<52; i++) {
			if (state[i] == CURRENT)
				state[i] = OLD;
		}
		
		// each card copied to hands[][] (including NULLCARD), valid cards update state[]
		byte card;
		for (int i=0; i < 13; i++) {
			card = (byte) cards[i];
			hands[playerNumber][i] = card;
			if ( card > NULLCARD )
				state[card] = CURRENT;
		}
	}
	
	// precondition: none
	// postcondition: checks all cards in hand for ownership by player
	public boolean playMatchesHand(int[] cards, int player) {
		int card;
		boolean valid = true;
		for (int i=0; i<13 && valid; i++) {
			card = cards[i];
			if (state [ card ] != player) valid = false;
		}
		return valid;
	}

	// precondition: none
	// postcondition: fills state[] with values (eg. deals cards randomly)
	//		NOTE: Fisher–Yates shuffle, inside-out version
	public void deal() {
		int j;
		state[0] = 0;
		for (int i=1; i<52; i++) {
			// need random value between 0 and i, inclusive
			// nextInt returns values between 0 and i-1, inclusive
			j = r.nextInt(i+1);
			state[i] = state[j];
			state[j] = (byte) i;
		}
	}
}