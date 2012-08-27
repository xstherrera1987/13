package xst.thirteen.game;
import xst.thirteen.GameController;
import xst.thirteen.Hand;
import xst.thirteen.ai.AI_00;
import xst.thirteen.ai.GameAI;
/**
 * Game manages the flow of the game between players, and is the interface
 * which clients use to interact with the game
 */
public class Game {
	int roundState;
	
	GameController gameplay;
	GameAI gameAI;
	GameState gameState;
	GameLogic gameLogic;
	
	// precondition: none
	// postcondition: creates game subsystem for new game
	public Game(GameController gameplay) {
		this.gameplay = gameplay;
		gameState = new GameState();
		gameLogic = new GameLogic(gameState);
	}
	
	// precondition: none
	// postcondition: creates game subsystem from previously started game
	public Game(GameController gameplay, GameState gameState, 
			GameLogic gameLogic) {
		
	}
	
	// precondition: none
	// postcondition: use ai for this play
	public void setAI(GameAI ai) {
		if (ai != null)
			this.gameAI = ai;
		else
			ai = new AI_00();
	}
	
	// precondition: none
	// postcondition: makes the play and returns roundState
	public int makePlay(Hand hand, int playerNumber) throws Exception {
		if (playerNumber != gameLogic.turn)
			throw new Exception("not this player's turn");
		
		int[] cards = hand.int13Type();
		boolean valid = gameState.playMatchesHand(hand.int13Type(), 
				playerNumber);
		if (!valid) throw new Exception("play not in hand");
		
		boolean stronger = gameLogic.defeatsCurrent( cards );
		if ( !stronger )
			throw new Exception("play not stronger");
		
		return roundState = gameLogic.makePlay( cards , hand.cards.length, 
				hand.playType );
	}
}