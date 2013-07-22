package net.joeherrera.Thirteen.gameplay;

import net.joeherrera.Thirteen.core.*;

/**
 * Game implementations provide a variety of game types (maybe with different rules)
 * over a variety of networks (LAN, internet, same machine). Game implementations
 * interact with Client implementations to provide gameplay.
 */
public interface Game {
	/**
	 * Entry point into the rest of the Game.
	 */
	void start();
	
	/**
	 * Register this player for a game. The Token returned is subsequently used
	 * to authenticate the player.
	 * @param name the player's name
	 * @return the unique identifier for the player
	 */
	Token registerPlayer(String name);
	
	/**
	 * Attempt to make a play with the current cards.
	 * @param cards the cards comprising the play to be made.
	 * @param token the unique identifier for the player
	 * @return the state of the game after the play is made. 
	 */
	PlayerGameState makePlay(Card[] cards, Token token);
	
	/**
	 * Quit the game. A player will automatically be removed from a game
	 * after an unspecified period of inactivity.
	 */
	void quitGame();
}
