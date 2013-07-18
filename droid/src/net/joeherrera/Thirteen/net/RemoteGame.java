package net.joeherrera.Thirteen.net;

import net.joeherrera.Thirteen.GameSystem;
import net.joeherrera.Thirteen.Hand;
import net.joeherrera.Thirteen.game.Game;

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
