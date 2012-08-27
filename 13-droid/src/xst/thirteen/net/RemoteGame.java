package xst.thirteen.net;

import xst.thirteen.GameSystem;
import xst.thirteen.Hand;
import xst.thirteen.game.Game;

public class RemoteGame extends Game {

	public RemoteGame(GameSystem sys) {
		super(sys);
	}
	
	@Override
	public int makePlay(Hand hand, int playernum) {
		/*
		return roundState = gameLogic.makePlay( cards , hand.cards.length, 
				hand.playType );
		 */
		
		return 0;
	}
}
