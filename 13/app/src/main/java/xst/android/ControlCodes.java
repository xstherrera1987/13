package xst.android;

public class ControlCodes {
	// STATE[ ] constants
	public static final int NULLCARD = -1;							// to delimit end of int[]
	// n = 0,1,2,3 card corresponds to player #n
	public static final int CURRENT = 4;							// card is current play
	public static final int OLD = 5;								// card was already played
	public static final int ANY_CARD = 52;							// used when searching for cards without constraints on its rank
	
	// ROUNDSTATE constants
	public static final int FIRST_PLAY = -1;						// the game is ready to start, but has not yet started
	public static final int ROUNDWON = -2;							// the round was won by this player
	public static final int GAMEWON = -3;							// the game was won by this player
	public static final int SKIPPED = -4;							// this player skipped this round
	public static final int CONTINUEROUND = -5;						// the round must continue, this players turn
	public static final int GAMEOVER = -6;							// the game is over, abort to main
	public static final int ERROR = -7;
}