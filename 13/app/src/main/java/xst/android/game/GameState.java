package xst.android.game;
import xst.android.Hand;
import static xst.android.ControlCodes.*;
import static xst.android.Plays.*;

public class GameState {
	// game state and hands data
	final byte[] state = new byte[52];				// the state of each of the 52 cards
	final byte[][] hands = new byte[4][13];			// this data structure should be synchronized with state[]
	final byte[] lastCard = new byte[4];			// index of last card in each player's hand
	
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
	public void deal() {
		int[] total = new int[4];							// how many cards each player has
		boolean alt = false;								// this variable alternates on every iteration
		boolean strongRerandomize, otherRerandomize;		// used when regenerating random number
		
		int random=0;										// the player who is assigned this card
		final int PRIME = 13;								// a prime number for calculations
		final int EVEN = 60;								// an even number for calculations
		final int MIN_EXEC = 111;							// minimum iterations of loops
		long a=0,b=0;
		int i=0, j=0, k=0, limOut, limIn;
		for (i=0;i<52; i++) {
			// a will be the execution time of some arithmetic calculations
			a = System.nanoTime();							// start time
			// the division yields an irrational number, so this pattern is slightly random
			limOut = ((i * PRIME) % EVEN) +MIN_EXEC;	// amount of iterations of outer loop (even number)
			limIn = (int) (a % (limOut + PRIME)) +MIN_EXEC;	// amount of iterations of inner loop (odd number)
			b = 0;											// this holds result of calculations
			for (j=0; j<limOut; j++)
				for (k=0; k<limIn; k++)
					b += PRIME;
			a = System.nanoTime() - a;						// difference in clock readings
			// one final calculation for good measure
			b = alt ? b +(limOut-limIn) : b +(limIn-limOut);
			
			// random = ExTime + ResultOfCalculation
			random = (int) (a + b) % 4;
			
			// first 12 cards (strongest) are dealt more fairly
			if (i<12) {
				// assign this card to player, if she has less than 4 cards
				if (total[random] < 3) {
					state[i] = (byte) random;
					total[random]++;
				} else {
					// if that player already had 4 cards, chose diff player
					strongRerandomize = true;
					while (total[random] >= 3 && strongRerandomize) {
						random = alt ? ((random+1) %4) : ((random+3) %4);
						if (total[random] < 3) {
							state[i] = (byte) random;
							total[random]++;
							strongRerandomize = false;
						}
					}
				}
			} else {
				// assign this card to player, if she has less than 13 cards
				if (total[random] < 13) {
					state[i] = (byte) random;
					total[random]++;
				} else {
					// if that player already had 4 cards, chose diff player
					otherRerandomize = true;
					while (total[random] >= 13 && otherRerandomize) {
						random = alt ? ((random+1) %4) : ((random+3) %4);
						if (total[random] < 13) {
							state[i] = (byte) random;
							total[random]++;
							otherRerandomize = false;
						}
					}
				}
			}
		}
	}
}