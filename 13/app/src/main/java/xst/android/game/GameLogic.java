package xst.android.game;
import static xst.android.Plays.*;
import static xst.android.ControlCodes.*;

// game rules and gameplay management

public class GameLogic {
	private GameState gameState;						// the associated GameState
	public int[] current = new int[13];					// stores current hand played; for printing to the table, etc...
	int playType;										// current play type for this round (singles,doubles,trips, etc..)
	int turn;											// player number of whose turn it is
	int lastPlay;										// player who last played a hand (didn't skip)
	boolean[] skipTurn = new boolean[4];				// keeps track of who has skipped this round
	boolean[] humans = new boolean[4];					// marks human vs AI players
	boolean gameWon;									// marks if game is over
	boolean quitGame;									// marks if user wants to quit the game
	int gameWinner;										// marks the winner of the game
	
	// precondition: gameState is initialized
	// postcondition: initializes logic for single player games
	public GameLogic(GameState gameState) {
		this.gameState = gameState;
		turn = gameState.state[51];
		playType = FIRST_PLAY;
		lastPlay = -1;
		for (int i=0; i<4; i++) skipTurn[i] = false;
		for (int i=0; i<4; i++) humans[i] = i == 0 ? true : false;
		gameWon = false;
		quitGame = false;
		gameWinner = -1;
	}
	
	// precondition: gameState is initialized
	// postcondition: initializes logic for multiplayer games
	public GameLogic(GameState gameState, boolean[] players) {
		super();
		humans = players;
	}
	
	// precondition: none
	// postcondition: reads state and returns roundState for player whose turn it is
	public int determineRoundState() {
		if (playType == FIRST_PLAY) return FIRST_PLAY;
		if (!gameWon) {
			int numSkipped = 0;
			// calculate how many players skipped
			for (int j=0; j<4; j++) if (skipTurn[j]) numSkipped++;
			
			// if 3 players skipped, then the round was won
			if (numSkipped == 3 && !skipTurn[turn] ) return ROUNDWON;
			
			// if this player hasn't skipped, can continue this round
			if (!skipTurn[turn] ) return CONTINUEROUND;
			else return SKIPPED;
		} else {
			return GAMEOVER;
		}
	}
	
	// precondition: cards is valid and stronger play
	// postcondition: 
	public int makePlay(int[] cards, int num, int playType) {
		// update game state
		gameState.makePlay(cards, turn);
		int roundState = determineRoundState();
		
		switch(roundState) {
		case FIRST_PLAY:
			break;
		case ROUNDWON:
			break;
		case GAMEWON:			
			break;
		case SKIPPED:			
			break;
		case CONTINUEROUND:
			break;
		case GAMEOVER:
			break;
		}
		
		if ( gameState.checkWin(turn) ) {
			gameWon = true;
			gameWinner = turn;
			

		} else {
			// update logic fields
			lastPlay = turn;
			skipTurn[turn] = false;
			playType = playType;
			// wtf ? System.arraycopy(play, 0, current, 0, 13);
			//gameState.calculateHand(turn);
			
			// increment turn
			turn = ( turn + 1 ) % 4;
		}
		
		
		return roundState;
	}
	
	public boolean defeatsCurrent(int[] cards) {
		int roundState = determineRoundState();
		switch(roundState) {
		case FIRST_PLAY: return true;
		case ROUNDWON:
			break;
		case GAMEWON:			
			break;
		case SKIPPED:			
			break;
		case CONTINUEROUND:
			break;
		case GAMEOVER:
			break;
		}
		return false;
		//if ()
			//throw new Exception("incompatible play types");
	}
	
	/*
	// precondition: cards is sifted and sorted (powerful cards at front NULLCARD at end)
	// postcondition: returns true if "cards" is a valid play of type "play"
	public static boolean isCorrectPlay(int[] cards, int play) {
		switch (play) {
		case NOPLAY: return false;
		case PAIR: return isPair(cards);
		case TRIPS: return isTriple(cards);
		case QUADS: return isQuad(cards);
		case STRAIGHT_3: return (isStraight(cards) == STRAIGHT_3);
		case STRAIGHT_4: return (isStraight(cards) == STRAIGHT_4);
		case STRAIGHT_5: return (isStraight(cards) == STRAIGHT_5);
		case STRAIGHT_6: return (isStraight(cards) == STRAIGHT_6);
		case STRAIGHT_7: return (isStraight(cards) == STRAIGHT_7);
		case STRAIGHT_8: return (isStraight(cards) == STRAIGHT_8);
		case STRAIGHT_9: return (isStraight(cards) == STRAIGHT_9);
		case STRAIGHT_10: return (isStraight(cards) == STRAIGHT_10);
		case SEQPAIRS_3: return (isSeqOfPairs(cards) == SEQPAIRS_3);
		case SEQPAIRS_4: return (isSeqOfPairs(cards) == SEQPAIRS_4);
		case SEQPAIRS_5: return (isSeqOfPairs(cards) == SEQPAIRS_5);
		case SINGLE: return (isSingle(cards));
		default: return false;
		}
	}
	*/
	
	// precondition: cards is sifted and sorted (powerful cards at front, NULLCARD's at end)
	// postcondition: returns the play found (or NOPLAY if these cards can't be played)
	public static int isPlay(int[] cards) {
		// possibly reorder or parallelize the tests for efficiency
		int check2 = NOPLAY;
		if (twoCheck(cards) != NOPLAY)
			return check2;
		
		if (isSingle(cards) )
			return SINGLE;
		if (isPair(cards) )
			return PAIR;
		
		int play = NOPLAY;
		if ( ( play = isStraight(cards) ) != NOPLAY)
			return play;
		
		if (isTriple(cards) )
			return TRIPS;
		
		if ( ( play = isSeqOfPairs(cards) ) != NOPLAY)
			return play;
		
		int checkQuad = NOPLAY;
		if (quadCheck(cards) != NOPLAY)
			return checkQuad;
		
		return NOPLAY;
	}
	
	// precondition: cards is sifted and sorted (powerful cards at front, NULLCARD's at end)
	// postcondition: checks for TWO, TWO_2, TWO_3, TWO_4. returns NOPLAY if it is none of these
	public static int twoCheck(int[] cards) {
		int numTwos = 0;
		for (int i=0; cards[i] < 4 && cards[i] >= 0; i++) {
			if (cards[i] > 4 || cards[i] < 0)
				return NOPLAY;
			else ++numTwos;
		}
		switch(numTwos) {
		case 1: return TWO;
		case 2: return TWO_2;
		case 3: return TWO_3;
		case 4: return TWO_4;
		default: return NOPLAY;
		}
	}

	// precondition: cards is sifted and sorted (powerful cards at front, NULLCARD's at end)
	// postcondition: checks for QUAD, QUAD_2, QUAD_3. returns NOPLAY if it is none of these
	public static int quadCheck(int[] cards) {
		// keep track of rank of each quad (aka bomb)
		int[] ranks = new int[3];
		int numQuads = 0;
		boolean fourth = false;
		for (int i=0; i<13 && cards[i] != NULLCARD; i++) {
			fourth = i % 4 == 0;
			if (fourth && i / 4 > 0)
				++numQuads;
			if (fourth)
				ranks[i%4] = cards[i] % 4;
			if (ranks[i%4] != cards[i] % 4)
				return NOPLAY;
		}
		
		switch(numQuads) {
		default: return NOPLAY;
		case 1: return QUADS;
		case 2: return QUADS_2;
		case 3: return QUADS_3;
		}
	}
	
	// precondition: cards is sifted and sorted
	// postcondition: returns true if "cards" is a valid play and it includes the lowest card
	public static boolean isInitialPlay(int[] cards) {		
		boolean contains51 = false;
		for (int i=0; i<13; i++) {
			if (cards[i] == 51)
				contains51 = true;
		}
		
		int thePlay = NOPLAY;
		if (contains51)
			thePlay = isPlay(cards);
		
		if (contains51 && thePlay != NOPLAY)
			return true;
		else
			return false;
	}
	
	// precondition: none
	// postcondition: returns true if cards[] contains only a single card
	public static boolean isSingle(int[] cards) {
		int lastCard = NULLCARD;
		for (int i=0; i<13 && cards[i] != NULLCARD; i++)
			lastCard = i;
		
		if (lastCard == 0)					// needs to be exactly one card
			return true;
		else
			return false;
	}
	
	public static boolean isPair(int[] cards) {
		int lastCard = NULLCARD;
		for (int i=0; i<13 && cards[i] != NULLCARD; i++)
			lastCard = i;
		if (lastCard != 1)					// needs to be exactly two cards
			return false;
		
		
		if (cards[0]/4 == cards[1]/4)		// same rank
			return true;
		else
			return false;
	}

	public static boolean isTriple(int[] cards) {
		// find number of cards passed as argument
		int count = 0;
		boolean done = false;
		for (int i=0; i<13 && !done; i++) {
			if (cards[i] != NULLCARD)
				count += 1;
			else
				done = true;
		}
		
		// must be 3 cards
		if (count != 3) return false;
		
		int rank=cards[0]/4;
		if(rank == cards[1]/4 && rank == cards[2]/4)
			return true;
		else
			return false;
	}
	
	public static boolean isQuad(int[] cards) {
		int rank = cards[0]/4;
		if(rank == cards[1]/4 && rank == cards[2]/4 && rank == cards[3]/4)
			return true;
		else
			return false;
	}
	
	// precondition: cards[] is sifted and sorted
	// postcondition: returns NOPLAY if cards is not sequence of pairs
	//		otherwise returns SEQPAIRS_3, SEQPAIRS_4, SEQPAIRS_5
	//		depending on the number of pairs in the sequence
	public static int isSeqOfPairs(int[] cards) {
		int[] temp = new int[12];
		// find number of cards passed as argument
		int count = 0;
		boolean done = false;
		for (int i=0; i<13 && !done; i++) {
			if (cards[i] != NULLCARD)
				count += 1;
			else
				done = true;
		}
		
		// must be even amount of cards, and at least 6 cards for a sequence of pairs
		if (count % 2 != 0 || count < 6) return NOPLAY;
		
		// clear temp buffer for following calculations
		for (int i=0; i<count; i++) temp[i] = NULLCARD;
		// temp will hold the ranks of the cards
		for (int i=0; i<count; i++) temp[i] = cards[i]/4;
		// pairs must be same rank, and consecutive pairs must be of rank +1 of the last
		if (count == 6) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] )
				return SEQ_PAIRS_3;
		}
		else if (count == 8) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] && temp[6]==(temp[4] +1) &&
					temp[6]==temp[7] )
				return SEQ_PAIRS_4;
		}
		else if (count == 10) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] && temp[6]==(temp[4] +1) &&
					temp[6]==temp[7] && temp[8]==(temp[6] +1) &&
					temp[8]==temp[9] )
				return SEQ_PAIRS_5;
		}	
		return NOPLAY;
	}
	
	// precondition: cards[] is sifted and sorted
	// postcondition: returns NOPLAY if it's not a straight
	// 		other wise returns the appropriate one from STRAIGHT_3,
	//		STRAIGHT_4, STRAIGHT_5, etc.. depending on number of cards
	public static int isStraight(int[] cards) {
		// NOTE: 2's cannot be part of a straight
		
		// find last card in cards[]
		int i=0, count = 0;
		boolean done = false;
		for (i=0; i<13 && !done; i++) {
			if (cards[i] != NULLCARD)
				count += 1;
			else
				done = true;
		}
		
		if (count > 2) {	// straights must be at least 3 cards
			int rank, nextRank;
			// rank must equal nextRank for every consecutive pair of cards
			for (i=0; i<count-2; i++) {
				rank = cards[i]/4;
				nextRank = cards[i+1]/4;
				// rank, nextRank == 0 means it was a 2
				// 2's cannot be part of a straight
				if (nextRank != rank + 1 || rank == 0 || nextRank == 0)
					return NOPLAY;
			}
			switch (count) {
			case 3: return STRAIGHT_3;
			case 4: return STRAIGHT_4;
			case 5:	return STRAIGHT_5;
			case 6:	return STRAIGHT_6;
			case 7:	return STRAIGHT_7;
			case 8:	return STRAIGHT_8;
			case 9:	return STRAIGHT_9;
			case 10: return STRAIGHT_10;
			default: return NOPLAY;
			}
		} else {
			return NOPLAY;
		}
	}
}	