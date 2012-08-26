package xst.thirteen.ai;
import xst.thirteen.Hand;
import xst.thirteen.game.Game;
public interface GameAI {
	// precondition: gameState is in initial state, player has the 3S
	// postcondition: returns the hand that player chooses to play
	public Hand initialPlay(Game g);
	
	// precondition: player won previous round
	// postcondition: returns the hand that player chooses to play
	public Hand initiateRound(Game g);

	// precondition: player's turn
	// postcondition: returns the hand that player chooses to play
	public Hand continueRound(Game g);
}