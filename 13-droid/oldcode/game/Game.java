// - States -
// 0 = in player 0's hand
// 1 = in player 1's hand
// 2 = in player 2's hand
// 3 = in player 3's hand
// 4 = current hand played (eg. what's visible on the table)
// 5 = already played, not current (eg. cards under current)
package team.alpha;

import static team.alpha.PLAYS.*;
import java.io.*;

public class Game implements Serializable {
	public static final int HUMAN = 0;
	public GameInterface gInterf;
	
	// game state and hands data
	public final int[] state = new int[52];				// the state of each of the 52 cards
	public final int[][] hands = new int[4][13];		// quick reference to each player's hand
	public final int[] lastCard = new int[4];			// index of last card in each player's hand
	
	// gameplay structures
	public final int[] current = new int[13];			// stores current hand played; for printing to the table, etc...
	public final int FIRST_PLAY = -2;
	
	public int roundType = FIRST_PLAY;							// current round type (singles,doubles,trips,straight, etc..)
	public int turn = -1;										// whose turn it is
	public int lastPlay = -1;									// player who last played a hand (didn't skip)
	public final boolean[] skipTurn = new boolean[4];			// keeps track of who has skipped
	public final boolean[] humans = new boolean[4];				// marks human vs AI players
	boolean gameWon = false;									// marks if game is over
	boolean quitGame = false;									// marks if user wants to quit the game
	int gameWinner = -1;										// marks the game winner
	
	// AI structures
	final int[][] playsFound = new int[8][13];	// stores the hands the AI can play (8 plays, 12 cards per hand)
	final boolean[] validPlays = new boolean[8];	// tracks which play-queries were successful

	public Game(GameInterface gi) {
		// initialize current[], skipping[], playsFound[], humans[]
		for(int i=0; i<13; i++) current[i] = -1;
		for(int i=0; i<4; i++) skipTurn[i] = false;
		for(int i=0; i<4; i++) for (int j=0; j<13; j++) playsFound[i][j] = -1;
		for(int i=0; i<4; i++) humans[i] = i==0 ? true: false;
		this.gInterf = gi;
	}
	
	// precondition: cards have been dealt
	// postcondition: initialize hands[], roundType, determines first player
	public void init() {
		// fill hands[], lastCard[]
		initializeHands();
		
		// player w lowest card starts
		turn = state[51];
		
		roundType = FIRST_PLAY;
	}

	// precondition: none
	// postcondition: executes AI code until it is the player's turn
	public void runAIRounds() {
		int numSkipped;
		boolean roundWon;
		
		boolean done = false;
		while (!done  && !gameWon) {
			if (turn != HUMAN) {
				if ( !skipTurn[turn] ) {
					numSkipped = 0;
					roundWon = false;
					// calculate how many players skipped
					for (int j=0; j<4; j++) if (skipTurn[j]) numSkipped++;
					// if 3 players skipped, then the round was won
					if (numSkipped == 3) roundWon = true;
					// if AI won that round, they set the new round type
					if (roundWon) {
						// reset skipTurn[]
						for (int j=0; j<4; j++) skipTurn[j] = false;
						// reset skip buttons
						gInterf.gAct.clearSkipButtons();
						gInterf.gAct.clearCurrentPlayed();
						// run AI code
						AI_initiateRound();
					} else {
						if (roundType == FIRST_PLAY) {
							AI_initiateGame();
						} else {
							AI_round();
						}
					}
				} else {
					// AI already skipped this round
					turn = (turn + 1) % 4;
				}
				// turn is updated when makePlay() is called
			} else {
				// if humans turn, and she didn't skip, return to the calling GUI code
				if (!skipTurn[HUMAN] ) {
					done = true;
					
					// calculate some stuff ??
				} else {
					//  human did skip otherwise keep running AI rounds
					turn = (turn + 1) % 4;
				}
			}
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// AI GAME METHODS
	/////////////////////////////////////////////////////////////////
		
	// precondition: none
	// postcondition: AI initiates a round
	public void AI_initiateRound() {
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
		int thePlay = -1;
		// check weakest then upwards, stick with strongest
		for (int i=0; i <=6 ; i++) {
			if (validPlays[i])
				thePlay = i;
		}
		
		int playType = -1;
		switch(thePlay) {
		case 0: 
			playType = PAIR;
			break;
		case 1: 
			playType = STRAIGHT_3;
			break;
		case 2: 
			playType = TRIPS;
			break;
		case 3: 
			playType = STRAIGHT_4;
			break;
		case 4: 
			playType = STRAIGHT_5;
			break;
		case 5: 
			playType = SEQPAIRS_3;
			break;
		case 6: 
			playType = QUADS;
			break;
		default: break;
		}
		
		// if a good play was found, play it otherwise play a single
		if (thePlay != -1) {
			makePlay(playsFound[thePlay], playType);
		} else {
			// otherwise choose lowest single card
			findLowSingle(playsFound[7]);
			makePlay(playsFound[7], SINGLE);
		}
	}
	
	// precondition: none
	// postcondition: AI starts the game by playing a hand involving the 3-clubs
	public void AI_initiateGame() {
		// check what plays can be made with the 3-clubs = #51
		
		// check for valid pair
		validPlays[0] = findLowPair( playsFound[0] );
		if (validPlays[0]) {
			// #51 would be 2nd in the pair
			if (playsFound[0][1] == 51)
				validPlays[0] = true;
			else
				validPlays[0] = false;
		}
		
		// check for valid 3-card straight
		validPlays[1] = findLowStraight( playsFound[1], 3 );
		if (validPlays[1]) {
			// #51 would be 3rd in 3-card straight
			if (playsFound[1][2] == 51)
				validPlays[1] = true;
			else
				validPlays[1] = false;
		}
		
		// check for valid three-of-a-kind
		validPlays[2] = findLowTrips( playsFound[2] );
		if (validPlays[2]) {
			// #51 would be 3rd in 3-of-a-kind
			if (playsFound[2][2] == 51)
				validPlays[2] = true;
			else
				validPlays[2] = false;
		}
		
		// if found a valid 3 card straight, maybe there is a 4-card straight?
		if (validPlays[1]) {
			validPlays[3] = findLowStraight( playsFound[3], 4 );
			// #51 would be 4th in 4-card straight
			if (playsFound[3][3] == 51)
				validPlays[3] = true;
			else
				validPlays[3] = false;
		}
		
		// pick best play among those found
		int thePlay = -1;
		// check weakest then upwards, stick with strongest
		for (int i=0; i<4; i++) {
			if (validPlays[i])
				thePlay = i;
		}
		
		int playType = -1;
		switch(thePlay) {
		case 0: 
			playType = PAIR;
			break;
		case 1: 
			playType = STRAIGHT_3;
			break;
		case 2: 
			playType = TRIPS;
			break;
		case 3: 
			playType = STRAIGHT_4;
			break;
		default: break;
		}
		
		// if a valid play was found, play it otherwise play a single
		if (thePlay != -1) {
			makePlay(playsFound[thePlay], playType);
		} else { // no choice, play single card
			// clear playsFound[]
			for (int i=1; i<13; i++) playsFound[0][i] = -1;
			playsFound[0][0] = 51;
			makePlay(playsFound[4], SINGLE);
		}
	}
	
	// precondition: none
	// postcondition: AI looks for a play according to game type, makes that play
	//		or skips turn if none is found
	public void AI_round() {
		// TODO: seq of 3 pairs beats a 2, seq of 4 pairs beats pair of 2s
		// TODO: larger than comparisons are not corrrect
		
		// determines if a play was found
		boolean found = false;
		switch(roundType) {
		case SINGLE:
			found = findSingleLargerThan(playsFound[0], current[0]);
			if (found) makePlay(playsFound[0], SINGLE);
			break;
		case PAIR:
			found = findPairLargerThan(playsFound[0], current[0]);
			if (found) makePlay(playsFound[0], PAIR);
			break;
		case TRIPS:
			found = findTripsLargerThan(playsFound[0], current[0]);
			if (found) makePlay(playsFound[0], TRIPS);
			break;
		case QUADS:
			found = findQuadsLargerThan(playsFound[0], current[0]);
			if (found) makePlay(playsFound[0], QUADS);
			break;
		case STRAIGHT_3:
			found = findStraightLargerThan(playsFound[0], 3, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_3);
			break;
		case STRAIGHT_4:
			found = findStraightLargerThan(playsFound[0], 4, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_4);
			break;
		case STRAIGHT_5:
			found = findStraightLargerThan(playsFound[0], 5, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_5);
			break;
		case STRAIGHT_6:
			found = findStraightLargerThan(playsFound[0], 6, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_6);
			break;
		case STRAIGHT_7:
			found = findStraightLargerThan(playsFound[0], 7, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_7);
			break;
		case STRAIGHT_8:
			found = findStraightLargerThan(playsFound[0], 8, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_8);
			break;
		case STRAIGHT_9:
			found = findStraightLargerThan(playsFound[0], 9, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_9);
			break;
		case STRAIGHT_10:
			found = findStraightLargerThan(playsFound[0], 10, current[0]);
			if (found) makePlay(playsFound[0], STRAIGHT_10);
			break;
		case SEQPAIRS_3:
			found = findSeqPairsLargerThan(playsFound[0], 3, current[0]);
			if (found) makePlay(playsFound[0], SEQPAIRS_3);
			break;
		case SEQPAIRS_4:
			found = findSeqPairsLargerThan(playsFound[0], 4, current[0]);
			if (found) makePlay(playsFound[0], SEQPAIRS_4);
			break;
		case SEQPAIRS_5:
			found = findSeqPairsLargerThan(playsFound[0], 5, current[0]);
			if (found) makePlay(playsFound[0], SEQPAIRS_5);
			break;
		}
		
		// if not found, AI skips turn
		if (!found) {
			System.out.println("AI#"+turn+" skips turn");
			skipTurn[turn] = true;
			gInterf.gAct.skipTurn(turn);
			
			// increment turn
			turn = (turn + 1) % 4;
		}
	}
	
	/////////////////////////////////////////////////////////////////
	// GAME METHODS
	/////////////////////////////////////////////////////////////////
	
	// precondition: none
	// postcondition: deals hands repeatedly until there is no autowin
	public void dealFairly() {
		boolean fairDeal = false;
		while (!fairDeal) {
			deal();
			initializeHands();
			int autoWins = 0;
			for(int i=0; i<4; i++)
				if (instantWin(i) ) autoWins++; 
			if (autoWins > 0) fairDeal = false;
			else fairDeal = true;
		}
	}
	
	// precondition: play[] and type are valid (no error checking)
	// postcondition: updates state[], current[], lastPlay, skipping[], roundType, gameWon
	public void makePlay(int[] play, int type) {
		// make previous "current" as old; state=5
		for (int i=0; i<52; i++) {
			if (state[i] == 4)
				state[i] = 5;
		}
		
		int card;
		for (int i=0; i<13 && play[i] != -1; i++) {
			// for each card
			card = play[i];
			// make this card current (out of hand)
			state[card] = 4;
		}
		
		// DEBUG -----------
		if (humans[turn]) {
			System.out.print("Human#"+turn+" plays:");
		} else {
			System.out.print("AI#"+turn+" plays:");
		}
		CLI_Interface.printHand(play);
		// -----------------
		
		// run animations
		switch (turn) {
		case HUMAN:
			gInterf.gAct.animateHumanPlay();
			break;
		case 1:
			gInterf.gAct.animateAIPlay(play, 1);
			break;
		case 2:
			gInterf.gAct.animateAIPlay(play, 2);
			break;
		case 3:
			gInterf.gAct.animateAIPlay(play, 3);
			break;
		}
		
		// if game not over, next player's turn
		if (checkWin(turn) ) {
			gameWon = true;
			gameWinner = turn;
			
			gInterf.gAct.gameWon(turn);
		} else {
			// update relevant fields
			lastPlay = turn;
			skipTurn[turn] = false;
			roundType = type;
			System.arraycopy(play, 0, current, 0, 13);
			calculateHand(turn);
			
			// increment turn
			turn = ( turn + 1 ) % 4;
		}
	}
	
	// precondition: none
	// postcondition: initializes hands[], lascCard[]
	public void initializeHands() {
		// populate hands[] arrays
		int a,b,c,d;
		a=b=c=d=0;
		for (int i=0; i<52; i++) {
			switch(state[i]) {
			case 0:
				hands[0][a++] = i;
				break;
			case 1:
				hands[1][b++] = i;
				break;
			case 2:
				hands[2][c++] = i;
				break;
			case 3:
				hands[3][d++] = i;
				break;
			default:
				break;
			}
		}
		lastCard[0] = lastCard[1] = lastCard[2] = lastCard[3] = 12;
	}
	
	// precondition: none
	// postcondition: reads state[] and fills hands[], updates lastCard[]
	public void calculateHand(int player) {
		// fill hands[]
		int count=0;
		for (int i=0; i<52; i++) {
			if (state[i] == player) {
				hands[player][count++] = i;
			}
		}
		
		// pad the rest of hands[] with -1
		lastCard[player] = count;
		for (int i=count; i<13; i++) {
			hands[player][count++] = -1;
		}
		// update lastCard[]
		lastCard[player] = (count-1);
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
					state[i] = random;
					total[random]++;
				} else {
					// if that player already had 4 cards, chose diff player
					strongRerandomize = true;
					while (total[random] >= 3 && strongRerandomize) {
						random = alt ? ((random+1) %4) : ((random+3) %4);
						if (total[random] < 3) {
							state[i] = random;
							total[random]++;
							strongRerandomize = false;
						}
					}
				}
			} else {
				// assign this card to player, if she has less than 13 cards
				if (total[random] < 13) {
					state[i] = random;
					total[random]++;
				} else {
					// if that player already had 4 cards, chose diff player
					otherRerandomize = true;
					while (total[random] >= 13 && otherRerandomize) {
						random = alt ? ((random+1) %4) : ((random+3) %4);
						if (total[random] < 13) {
							state[i] = random;
							total[random]++;
							otherRerandomize = false;
						}
					}
				}
			}
		}
	}
		
	// precondition: none
	// postcondition: checks state[], if player has no cards in hand, she has won
	public boolean checkWin(int player) {
		boolean win = true;
		for (int i=0; i<52 && win; i++) 
			if (state[i] == player) win = false;
		return win;
	}
	
	// precondition: none
	// postcondition: player gets instant win if she has four 2's, or
	//		six pairs, or 3 triples, or the dragon's head (12-card straight)
	public boolean instantWin(int player) {
		// 4 twos
		if (state[0]==player && state[1]==player
				&& state[2]==player && state[3]==player)
			return true;
		// six pairs
		if (numPairs(player) == 6)
			return true;
		// 3 triples
		if (numTrips(player) >= 3)
			return true;
		
		// dragon's head (3,4,...,A)
		if (hasDragonHead(player) )
			return true;
		
		return false;
	}
	
	// precondition: none
	// postcondition: returns true if this player has a 12-card straight
	public boolean hasDragonHead(int player) {
		// use AI array for calculations
		int[] temp = playsFound[player];
		// amt of cards in current found sequence
		int count = 0;			
		for (int i=12; i>=10; i--) {
			for (int k=0; k<12 && temp[k] != -1; k++) temp[k] = -1;
			temp[0] = hands[player][i];
			count = 1;
			for (int j=i-1; j>=0; j--) {
				if ( (temp[count-1]/4) == ( (hands[player][j]/4)+1) ) {
					temp[count] = hands[player][j];
					count++;
					if (count >= 12)  return true;
				}
			}
		}
		return false;
	}
	
	public int numPairs(int player) {
		int i,j;
		boolean done=false;
		boolean foundPair=false;
		int rank= -1, count=0;
		for (i=51; i>=0 && !done; i--) {
			if (state[i] == player) {
				rank = i/4;
				foundPair = false;
				for (j=i-1; j>=0 && (j/4)==rank && !foundPair; j--) {
					if (state[j]==player) {
						count++;
						if (j-1 >=0) {
							i = j;		// want j-1 , but outer for loop decrements at end
							foundPair = true;
						}
						else 
							done = true;
					}
				}
			}
		}
		return count;
	}
	
	public int numTrips(int player) {
		int i,j,k;
		boolean done=false;
		boolean foundTrip=false;
		int rank= -1, count=0;
		for (i=51; i>=0 && !done; i--) {
			if (state[i] == player) {
				rank = i/4;
				foundTrip = false;
				for (j=i-1; j>=0 && (j/4)==rank && !foundTrip; j--) {
					if (state[j]==player) {
						for (k=j-1; k>=0 && (k/4)==rank && !foundTrip; k--) {
							if (state[k]==player) {
								count++;
								if (j-1 >=0) {
									i = k;		// want k-1 , but outer for loop decrements at end
									foundTrip = true;
								}
								else 
									done = true;
							}
						}
					}
				}
			}
		}
		return count;
	}

	/////////////////////////////////////////////////////////////////
	// PLAYER METHODS (for human player)
	/////////////////////////////////////////////////////////////////
	
	// precondition: cards is sifted and sorted (powerful cards at front, -1's at end)
	// postcondition: returns the play found (or NOPLAY if these cards can't be played)
	public static int isPlay(int[] cards) {
		// may need to inline this (and possibly reorder the checks) for efficiency
		if (isSingle(cards) )
			return SINGLE;
		
		if (isPair(cards) )
			return PAIR;
		if (isTriple(cards) )
			return TRIPS;
		
		int play = NOPLAY;
		if (( play = isStraight(cards)) != NOPLAY)
			return play;
		
		play = NOPLAY;
		if (( play = isSeqOfPairs(cards)) != NOPLAY)
			return play;
		
		if(isQuad(cards) )
			return QUADS;
		
		return NOPLAY;
	}
	
	// precondition: cards is sifted and sorted (powerful cards at front, -1's at end)
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
		int lastCard = -1;
		for (int i=0; i<13 && cards[i] != -1; i++)
			lastCard = i;
		
		if (lastCard == 0)					// needs to be exactly one card
			return true;
		else
			return false;
	}
	
	public static boolean isPair(int[] cards) {
		int lastCard = -1;
		for (int i=0; i<13 && cards[i] != -1; i++)
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
			if (cards[i] != -1)
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
			if (cards[i] != -1)
				count += 1;
			else
				done = true;
		}
		
		// must be even amount of cards, and at least 6 cards for a sequence of pairs
		if (count % 2 != 0 || count < 6) return NOPLAY;
		
		// clear temp buffer for following calculations
		for (int i=0; i<count; i++) temp[i] = -1;
		// temp will hold the ranks of the cards
		for (int i=0; i<count; i++) temp[i] = cards[i]/4;
		// pairs must be same rank, and consecutive pairs must be of rank +1 of the last
		if (count == 6) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] )
				return SEQPAIRS_3;
		}
		else if (count == 8) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] && temp[6]==(temp[4] +1) &&
					temp[6]==temp[7] )
				return SEQPAIRS_4;
		}
		else if (count == 10) {
			if (	temp[0]==temp[1] && temp[2]==(temp[0] +1) &&
					temp[2]==temp[3] && temp[4]==(temp[2] +1) &&
					temp[4]==temp[5] && temp[6]==(temp[4] +1) &&
					temp[6]==temp[7] && temp[8]==(temp[6] +1) &&
					temp[8]==temp[9] )
				return SEQPAIRS_5;
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
			if (cards[i] != -1)
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
	
	/////////////////////////////////////////////////////////////////
	// AI METHODS
	/////////////////////////////////////////////////////////////////
	
	// precondition: none
	// postcondition: finds the single lowest card of this player
	public boolean findLowSingle(int[] playFound) {
		boolean done = false;
		for (int i=51; i>=0 && !done; i--) {
			if (state[i]==turn) {
				// clear playFound[]
				for (int j=0; j<13; j++) playFound[j] = -1;
				playFound[0] = i;
				return true;
			}
		}
		return false;	// found no single
	}
	// precondition: none
	// postcondition: finds the single lowest card , larger than "card"
	public boolean findSingleLargerThan(int[] playFound, int card) {
		boolean done = false;
		for (int i=card-1; i>=0 && !done; i--) {
			if (state[i]==turn) {
				// clear playFound[]
				for (int j=0; j<13; j++) playFound[j] = -1;
				playFound[0] = i;
				return true;
			}
		}
		return false;	// found none larger than "card"
	}
	// precondition: none
	// postcondition: finds the lowest pair for this player
	public boolean findLowPair(int[] playFound) {
		int i,j;
		int rank, searchLim;
		for (i=51; i>=1; i--) {
			if (state[i]==turn) {
				rank = i/4;
				searchLim = i-4;
				for (j=i-1; j>=0 && j>searchLim; j--) {
					if (state[j]==turn && (j/4)==rank) {
						// clear playFound[]
						for (int k=0; k<13; k++) playFound[k] = -1;
						
						playFound[0] = j;		// stronger card
						playFound[1] = i;
						return true;
					}
				}
			}
		}
		return false;	// found no pairs
	}
	// precondition: none
	// postcondition: finds a pair larger than "card"
	public boolean findPairLargerThan(int[] playFound, int card) {
		int i,j;
		int rank, searchLim;
		for (i=card-1; i>=1; i--) {
			if (state[i]==turn) {
				rank = i/4;
				searchLim = i-4;
				for (j=i-1; j>=0 && j>searchLim; j--) {
					if (state[j]==turn && (j/4)==rank) {
						// clear playFound[]
						for (int k=0; k<13; k++) playFound[k] = -1;
						
						playFound[0] = j;		// stronger card
						playFound[1] = i;
						return true;
					}
				}
			}
		}
		return false;	// found no pairs
	}
	// precondition: none
	// postcondition: finds the lowest pair for this player
	public boolean findLowTrips(int[] playFound) {
		int i,j,k;
		for (i=51; i>=2; i--) {
			if (state[i]==turn) {
				for (j=i-1; j>=1 && j>=i-4; j--) {
					if (state[j]==turn && (j/4)==(i/4)) {
						for (k=j-1; k>=0 && k>=i-3; k--) {
							if (state[k]==turn && (k/4)==(j/4)) {
								// clear playFound[]
								for (int m=0; m<13; m++) playFound[m] = -1;
								
								playFound[0] = k;		// stronger card
								playFound[1] = j;
								playFound[2] = i;
								return true;
							}
						}
					}
				}
			}
		}
		return false;	// found no triples
	}
	// precondition: none
	// postcondition: finds a pair larger than "card"
	public boolean findTripsLargerThan(int[] playFound, int card) {
		int i,j,k;
		for (i=card-1; i>=2; i--) {
			if (state[i]==turn) {
				for (j=i-1; j>=1 && j>=i-4; j--) {
					if (state[j]==turn && (j/4)==(i/4)) {
						for (k=j-1; k>=0 && k>=i-3; k--) {
							if (state[k]==turn && (k/4)==(j/4)) {
								// clear playFound[]
								for (int m=0; m<13; m++) playFound[m] = -1;
								
								playFound[0] = k;		// stronger card
								playFound[1] = j;
								playFound[2] = i;
								return true;
							}
						}
					}
				}
			}
		}
		return false;	// found no triples
	}
	// precondition: none
	// postcondition: finds the lowest quad for this player
	public boolean findLowQuads(int[] playFound) {
		int i,j,k,n;
		int rank;
		for (i=51; i>=3; i--) {
			if (state[i]==turn) {
				rank = i / 4;
				j = i - 1;
				k = i - 2;
				n = i - 3;
				if (turn == state[j] && turn == state[k] && turn == state[n] &&
					rank == (j/4) && rank == (k/4) && rank == (n/4)	) {
					// clear playFound[]
					for (int m=0; m<13; m++) playFound[m] = -1;
				
					playFound[0] = n;		// stronger card
					playFound[1] = k;
					playFound[2] = j;
					playFound[3] = i;
					return true;
				}
			}
		}
		return false;	// found no quads
	}
	// precondition: none
	// postcondition: finds the lowest quad for this player
	public boolean findQuadsLargerThan(int[] playFound, int card) {
		int i,j,k,n;
		int rank;
		for (i=card-1; i>=3; i--) {
			if (state[i]==turn) {
				rank = i / 4;
				j = i - 1;
				k = i - 2;
				n = i - 3;
				if (turn == state[j] && turn == state[k] && turn == state[n] &&
					rank == (j/4) && rank == (k/4) && rank == (n/4)	) {
					// clear playFound[]
					for (int m=0; m<13; m++) playFound[m] = -1;
				
					playFound[0] = n;		// stronger card
					playFound[1] = k;
					playFound[2] = j;
					playFound[3] = i;
					return true;
				}
			}
		}
		return false;	// found no quads
	}	
	// precondition: amount is the number of cards in straight
	// postcondition: returns true straight is found and result is in playFound[]
	public boolean findLowStraight(int[] playFound, int amount) {
		// dont' look for straights larger than 10
		if (amount > 10) return false;
		
		int count = 0;					// amt of cards in current found sequence
		boolean found = false;			// found a good sequence ?
		int previousCard, nextCard;		// used to track cards
		
		// start at last card (to find lowest possible straight), end at amount
		// since can't make straights if there is less than "amount" cards to check 
		for (int i=lastCard[turn]; i - (amount-1) >= 0 && !found; i--) {
			// clear playFound[]
			for (int k=0; k<13; k++) playFound[k] = -1;
			// insert the hand[i] to playFound temp array
			playFound[0] = hands[turn][i];
			// so far, 1 card in sequence to check
			count = 1;
			
			// look for a straight that starts with hand[i]
			for (int j=i-1; j >= 0 && !found; j--) {
				// the card most recently inserted into playsFound
				previousCard = playFound[count-1];
				// the card currently being considered
				nextCard = hands[turn][j];
				// if previousCard's rank is one more than nextCard and it's not a 2
				if ( previousCard/4 == (nextCard/4) + 1 && nextCard/4 != 0) {
					playFound[count] = hands[turn][j];
					count++;
				}
				// the number of cards in straight matches required amount
				if (count == amount) found = true;
			}
		}
		
		if (found) {
			// reverse the straight, so strongest at index=0
			int temp;
			for (int i=0; i<count/2; i++) {
				temp = playFound[i];
				playFound[i] = playFound[count-i-1];
				playFound[count-i-1] = temp;
			}
			return true;
		} else 
			return false;
	}
	
	// precondition: amount is the number of cards in straight
	// postcondition: returns true straight is found and result is in playFound[]
	public boolean findStraightLargerThan(int[] playFound, int amount, int card) {
		// dont' look for straights larger than 10
		if (amount > 10) return false;
		
		int count = 0;					// amt of cards in current found sequence
		boolean found = false;			// found a good sequence ?
		int previousCard, nextCard;		// used to track cards
		
		int startIndex = -1;			// index of strongest non-2 card
		boolean done = false;			// terminating condition
		// find index of strongest card that is not a 2
		for (int i=0; i<13 && !done; i++) {
			if (hands[turn][i]/4 != 0) {
				done = true;
				startIndex = i;
			}
		}
		
		if (startIndex != -1)  {	// found a card that is not a 2
			// index of last possible index, can't make straights if less than "amount" cards to check
			int lastIndex = lastCard[turn] - amount;
			// start with strongest cards
			for (int i=startIndex; i <= lastIndex + 1 && !found && hands[turn][i] < card; i++) {
				// clear playFound[]
				for (int k=0; k<13; k++) playFound[k] = -1;
				// insert the hand[i] to playFound[]
				playFound[0] = hands[turn][i];
				// so far, 1 card in sequence to check
				count = 1;
				
				// look for a straight that starts with hand[i]
				for (int j=i+1; j <= lastCard[turn] && !found; j++) {
					// the card most recently inserted into playsFound
					previousCard = playFound[count-1];
					// the card currently being considered
					nextCard = hands[turn][j];
					// if previousCard's rank is one less than nextCard and it's not a 2
					if ( previousCard/4 == (nextCard/4) - 1 && nextCard/4 != 0) {
						playFound[count] = hands[turn][j];
						count++;
					}
					// the number of cards in straight matches required amount
					if (count == amount) found = true;
				}
			}
			
			if (found)
				return true;
			else 
				return false;
		} else {
			// no cards were found that were not two's
			return false;
		}
	}
	// precondition: hand[] should be sifted and sorted. amount is the number of cards in straight
	// postcondition: returns true if a straight is found (of the correct amount)
	public boolean findSeqPairs(int[] playFound, int amount) {
		// dont' look for sequence of 6 or more pairs
		if (amount > 5) return false;
		
		boolean fail=false, 	
		pairFound = false;		// loop invariant for 5 inner loops
		int count=0;			// how many pairs in the sequence
		int g,h;				// loop counters
		int rank;				// rank being searched for
		int curr= -1;			// rank being considered
		int nextSearch = 0;		// where to begin the search in the following loop
		
		// start searching upwards from next lowest card
		for (int i=lastCard[turn]; i>=0 && count != amount; i--) {
			fail = false;
			count = 0;
			pairFound = false;
			
			// find matching card (for 1st pair)
			rank = hands[turn][i]/4;
			// note the assignment to "curr" in the loop condition
			for (h=i-1; !pairFound && h>=0 && (curr=hands[turn][h]/4) >= rank; h--) {
				if (curr == rank) {
					pairFound = true;
					
					// clear playFound[]
					for (int m=0; m<13; m++) playFound[m] = -1;
					
					playFound[0] = hands[turn][i];
					playFound[1] = hands[turn][h];
					count = 1;
					nextSearch = h;			// start next search here
				}
			}
			// if no pair, try again with next card
			if (!pairFound) fail = true;
			
			if (!fail) { //find 2nd pair, count = 2
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				// note the assignment to "curr" in the loop condition
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 2nd pair)
						// note the assignment to "curr" in the loop condition
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[2] = hands[turn][h];
								playFound[3] = hands[turn][g];
								count = 2;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail) { // find 3rd pair
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				// note the assignment to "curr" in the loop condition
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 3r pair)
						// note the assignment to "curr" in the loop condition
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[4] = hands[turn][h];
								playFound[5] = hands[turn][g];
								count = 3;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail && amount >= 4) { // find 4th pair, etc...
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 4th pair)
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[6] = hands[turn][h];
								playFound[7] = hands[turn][g];
								count = 4;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail && amount == 5) { // find 5th pair, etc...
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 5th pair)
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[8] = hands[turn][h];
								playFound[9] = hands[turn][g];
								count = 5;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
		}
		
		if (count == amount) 
			return true;
		else
			return false;
	}
	
	// TODO: this method is not correct \/
	// this searches up from weak, need to search down from strong
	
	// precondition: hand[] should be sifted and sorted. amount is the number of cards in straight
	// postcondition: returns true if a straight is found (of the correct amount)
	public boolean findSeqPairsLargerThan(int[] playFound, int amount, int card) {
		boolean fail=false, 	
		pairFound = false;		// loop invariant for 5 inner loops
		int count=0;			// how many pairs in the sequence
		int g,h;				// loop counters
		int rank;				// rank being searched for
		int curr= -1;			// rank being considered
		int nextSearch = 0;		// where to begin the search in the following loop
		
		// start searching upwards from next lowest card
		for (int i=lastCard[turn]; i>=0 && count != amount; i--) {
			fail = false;
			count = 0;
			pairFound = false;
			
			// find matching card (for 1st pair)
			rank = hands[turn][i]/4;
			// note the assignment to "curr" in the loop condition
			for (h=i-1; !pairFound && h>=0 && (curr=hands[turn][h]/4) >= rank; h--) {
				if (curr == rank) {
					pairFound = true;
					
					// clear old plays
					for (int m=0; m<12 && playFound[m] != -1; m++) playFound[m] = -1;
					
					playFound[0] = hands[turn][i];
					playFound[1] = hands[turn][h];
					count = 1;
					nextSearch = h;			// start next search here
				}
			}
			// if no pair, try again with next card
			if (!pairFound) fail = true;
			
			if (!fail) { //find 2nd pair, count = 2
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				// note the assignment to "curr" in the loop condition
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 2nd pair)
						// note the assignment to "curr" in the loop condition
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[2] = hands[turn][h];
								playFound[3] = hands[turn][g];
								count = 2;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail) { // find 3rd pair
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				// note the assignment to "curr" in the loop condition
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 3r pair)
						// note the assignment to "curr" in the loop condition
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[4] = hands[turn][h];
								playFound[5] = hands[turn][g];
								count = 3;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail && amount >= 4) { // find 4th pair, etc...
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 4th pair)
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[6] = hands[turn][h];
								playFound[7] = hands[turn][g];
								count = 4;
								nextSearch = h;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
			
			if (!fail && amount == 5) { // find 5th pair, etc...
				pairFound = false;
				
				// find a card of next rank
				rank = rank-1;
				for (h=nextSearch-1; !pairFound && h>=0 && ( curr = hands[turn][h] / 4) >= rank; h--) {
					if (curr == rank) {
						// find matching card (for 5th pair)
						for (g=h-1; !pairFound && g>=0 && ( curr = hands[turn][g] / 4) >= rank; g--) {
							if (curr == rank) {
								pairFound = true;
								playFound[8] = hands[turn][h];
								playFound[9] = hands[turn][g];
								count = 5;
							}
						}
					}
				}
				if (!pairFound) fail = true;
			}
		}
		
		if (count == amount) 
			return true;
		else
			return false;
	}
	
	/////////////////////////////////////////////////////////////////
	// SERIALIZATION
	/////////////////////////////////////////////////////////////////
	// precondition: none
	// postcondition: reads state[] and fills transient fields: hands[][], current[], lastCard[]
	// useful when reconstituting a serialized Game object
	public void repopulateGameplayArrays() {
		// holds the amount of cards each player has
		// indicates the index where next card is added to array
		// index 0-3 for each player, and the last one is for the current[]
		int[] amounts = {0,0,0,0, 0};
		
		// index to insert at
		int currAmt;
		for (int i=0; i<52; i++) {
			// depending on the state of each card, fills in appropriate array
			switch(state[i]) {
			case 0:
				currAmt = amounts[0];
				hands[0][currAmt] = i;
				amounts[0]++;
				break;
			case 1:
				currAmt = amounts[1];
				hands[1][currAmt] = i;
				amounts[1]++;
				break;
			case 2:
				currAmt = amounts[2];
				hands[2][currAmt] = i;
				amounts[2]++;
				break;
			case 3:
				currAmt = amounts[3];
				hands[3][currAmt] = i;
				amounts[3]++;
				break;
			case 4:
				currAmt = amounts[4];
				current[currAmt] = i;
				amounts[4]++;
				break;
			}
		}
		
		// repopulate the index of the last card of each player's hand
		for (int i=0; i<4; i++) 
			lastCard[i] = amounts[i];
	}
	
	static final long serialVersionUID = 123456789L;
	@Override
	// precondition: none
	// postcondition: checks the state[], roundType, skipping[], turn, lastPlay variables for equality
	public boolean equals(Object o) {
		Game h = (Game) o;
		
		for (int i=0; i<52; i++)
			if (state[i] != h.state[i])
				return false;
		for (int i=0; i<4; i++)
			if (skipTurn[i] != h.skipTurn[i] )
				return false;
		if (turn != h.turn)
			return false;
		if (lastPlay != h.lastPlay)
			return false;
		
		// all checks pass
		return true;
	}
}