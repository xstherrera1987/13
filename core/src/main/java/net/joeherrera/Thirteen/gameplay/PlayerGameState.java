package net.joeherrera.Thirteen.gameplay;
import net.joeherrera.Thirteen.core.*;

/**
 * The state of the game as visibile to a single player. 
 */
public class PlayerGameState {
	boolean successfulPlayMade;
	int[] cardCounts;
	// TODO consider sending back Card[] and letting the client calculate the Hand
	Hand h;
}
