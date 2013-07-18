package net.joeherrera.Thirteen;

import net.joeherrera.Thirteen.game.Game;

/**
 * GameSystem manages the connection to a game, be it locally or remotely
 * this is the primary interface for the GameController
 */
public class GameSystem {
	public final GameController controller;
	public boolean local = true;
	
	Game g = null;
	long gamenum = -1;
	int playernum = -1;
	
	// TODO 13activity should construct GameSystem, but only keep reference 
	// to its GameController (safety & encapsulation)
	public GameSystem(boolean local) {
		this.local = local;
		controller = new GameController();
		if (local) {
			g = new Game(this);
		} else {
			// get gamenum, playernum from remote game
		}
	}
	
	public int makePlay(Hand h) throws Exception {
		return g.makePlay(controller.current, playernum);
	}
}
