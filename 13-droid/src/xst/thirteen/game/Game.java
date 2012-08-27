package xst.thirteen.game;
import xst.thirteen.GameSystem;
import xst.thirteen.Hand;
import xst.thirteen.ai.GameAI;
/**
 * Game manages the flow of the game between players, and is the interface
 * which clients use to interact with the game
 */
public class Game {
	public int roundState;
	
	public final GameSystem sys;
	GameState gameState;
	GameLogic gameLogic;
	GameAI[] gameAI;
	
	// precondition: none
	// postcondition: creates game subsystem for new game
	public Game(GameSystem sys) {
		this.sys = sys;
		gameState = new GameState();
		gameLogic = new GameLogic(gameState);
	}
		
	// precondition: none
	// postcondition: makes the play and returns roundState
	// TODO use control codes instead of throwing exceptions
	public int makePlay(Hand hand, int playerNumber) throws Exception {
		if (playerNumber != gameLogic.turn)
			throw new Exception("not this player's turn");
		
		int[] cards = hand.int13Type();
		boolean valid = gameState.playMatchesHand(hand.int13Type(), 
				playerNumber);
		if (!valid) throw new Exception("play not in hand");
		
		return roundState = gameLogic.makePlay( cards , hand.cards.length, 
				hand.playType );
	}

	// TODO need a callback to the gamecontroller when it is this player's 
	// 	turn again
	
}