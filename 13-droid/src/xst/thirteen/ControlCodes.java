package xst.thirteen;

public class ControlCodes {
	// STATE[ ] constants
	// used to delimit end of int[13] type
	public static final int NULLCARD = -1;
	// n = 0,1,2,3 card corresponds to player #n
	// card is current play
	public static final int CURRENT = 4;
	// card was already played
	public static final int OLD = 5;
	// used when searching for cards without constraints on its rank
	public static final int ANY_CARD = 52;
	
	// ROUNDSTATE constants
	// the game is ready to start, but has not yet started
	public static final int FIRST_PLAY = -1;
	// the round was won by this player
	public static final int ROUNDWON = -2;
	// the game was won by this player
	public static final int GAMEWON = -3;	
	// this player skipped this round
	public static final int SKIPPED = -4;	
	// the round must continue, this players turn
	public static final int CONTINUEROUND = -5;
	// the game is over, abort to main
	public static final int GAMEOVER = -6;
	public static final int ERROR = -7;
}