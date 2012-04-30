package xst.android.game;
import xst.android.GameController;
import xst.android.Hand;
import xst.android.GameAI;
import xst.android.ai.AI_00;				// default AI class

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
	public Game(GameController gameplay, GameState gameState, GameLogic gameLogic) {
		
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
		
		int[] cards = hand.cards();
		boolean valid = gameState.playMatchesHand(hand.cards(), playerNumber);
		if (!valid) throw new Exception("play not in hand");
		
		boolean stronger = gameLogic.defeatsCurrent( cards );
		if ( !stronger )
			throw new Exception("play not stronger");
		
		return roundState = gameLogic.makePlay( cards , hand.num(), hand.playType() );
	}
}