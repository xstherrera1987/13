package xst.android;
import xst.android.game.Game;
public interface GameAI {
	// precondition: gameState is in initial state, player has the 3S
	// postcondition: returns the hand that player chooses to play
	public Hand initialPlay(Game g);
	
	// precondition: gameState is in initiate round state, player has the 3S
	// postcondition: returns the hand that player chooses to play
	public Hand initiateRound(Game g);

	// precondition: gameState is in continue round state, player has the 3S
	// postcondition: returns the hand that player chooses to play
	public Hand continueRound(Game g);
}