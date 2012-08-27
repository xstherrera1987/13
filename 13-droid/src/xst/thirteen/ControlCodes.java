package xst.thirteen;

public class ControlCodes {
	// CARD codes
	// each card is represented in simple hierarchical order by an integer
	// 0 is "2H" and strongest, 51 is "3S" and weakest
	//
	// used to delimit end of int[13] type
	public static final int NULLCARD = -1;
	// used when searching for cards without constraints on its rank
	public static final int ANY_CARD = 52;
	
	// CARD STATE codes
	// state of n = 0,1,2,3 corresponds to player #n owning that card
	//
	// card is current play, the one to beat
	public static final int CURRENT = 4;
	// card was already played and already beaten
	public static final int OLD = 5;

	// TODO revise roundstate and error codes for consistency and efficacy
	
	// ROUNDSTATE codes
	public static final int NOTTURN = -1;
	// the game is ready to start, but has not yet started
	public static final int FIRST_PLAY = -2;
	// the round was won by this player
	public static final int ROUNDWON = -3;
	// the game was won by this player
	public static final int GAMEWON = -4;	
	// this player skipped this round
	public static final int SKIPPED = -5;	
	// the round must continue, this players turn
	public static final int CONTINUEROUND = -6;
	// the game is over
	public static final int GAMEOVER = -7;
	
	// ERROR codes
	public static final int ERROR = -8;
	public static final int ERROR_NOTSTRONGER = -9;
}