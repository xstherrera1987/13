package xst.android.ai;
import xst.android.game.Game;
import xst.android.GameAI;
import xst.android.Hand;
import static xst.android.Plays.*;

// the default AI for 13 game

public class AI_00 implements GameAI {
	int[][] playsFound = new int[8][13];
	boolean[] validPlays = new boolean[8];

	@Override
	public Hand initialPlay(Game g) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hand initiateRound(Game g) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Hand continueRound(Game g) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// precondition: none
	// postcondition: returns simple ascending rank NOTE:single cards have no value
	public double playValue(int play) {
		return 0.0;
	}

	
	/*
	// precondition: none
	// postcondition: returns control code for this play
	public static int playCode(int play) {
		
		switch(play) {
		case 0: return PAIR;
		case 1: return STRAIGHT_3;
		case 2: return TRIPS;
		case 3: return STRAIGHT_4;
		case 4: return STRAIGHT_5;
		case 5: return SEQ_PAIRS_3;
		case 6: return QUADS;
		default: return -1;
		}
	}
	 */

	/**
	// returns the best play to initiate a round at this state
	public Hand initiateRound(int[] state, int[] hand, int playerNumber) {
		for (int i=0; i<7; i++) validPlays[i] = false;
		
		System.out.println("AI#"+turn+" initiates round");
		// check for good pair
		validPlays[0] = findLowPair( playsFound[0] );
	
		// check for good 3-card straight
		validPlays[1] = findLowStraight( playsFound[1], 3 );
		
		// check for good three-of-a-kind
		validPlays[2] = findLowTrips( playsFound[2] );
		
		// if found a good 3 card straight, maybe there is a 4-card straight?
		if (validPlays[1]) {
			validPlays[3] = findLowStraight( playsFound[3], 4 );
			
			// if found a good 4 card straight, maybe there is a 5-card straight?
			if (validPlays[3]) {
				validPlays[4] = findLowStraight( playsFound[4], 5 );
			}
			// don't bother checking for 6 or more card straight
		}
		
		// check for a sequence of 3 pairs (4 is rare, dont' bother)
		validPlays[5] = findSeqPairs(playsFound[5], 3);
		
		// check for 4-of-a-kind
		validPlays[6] = findLowQuads(playsFound[6]);
		

		
		// pick best play among those found
		int thePlay = 0;
		// check weakest then upwards, keep strongest
		for (int i=0; i <=6 ; i++)
			if (validPlays[i])
				thePlay = i;
		//
		int playType = playCode(thePlay);
		double playValue = playValue(thePlay);
		
		/** TODO: alloc Hand object, return it
		// if a good play was found, play it otherwise play a single
		//if (playValue > 1) {
		//	makePlay(playsFound[thePlay], playType);
		//} else {
		//	// otherwise choose lowest single card
		//	findLowSingle(playsFound[7]);
		//	makePlay(playsFound[7], SINGLE);
		//}
		
		Hand retValue = new Hand(Plays.lengths[playType] , playsFound[thePlay], playType);
		return retValue;
	}
*/

}
