package net.joeherrera.Thirteen.gameplay;
import net.joeherrera.Thirteen.core.*;

/**
 * The state of the game as visibile to a single player. 
 */
public class PlayerGameState {
	/**
	 * the player number for the player whose turn it is
	 */
	int playerTurn;
	
	/**
	 * the number of cards that each player has left 
	 */
	int[] cardCounts;
	
	/**
	 * the cards left in this player's hand
	 */
	Card[] cards;
	
	/**
	 * the current state of the round
	 */
	RoundState roundState;
}
