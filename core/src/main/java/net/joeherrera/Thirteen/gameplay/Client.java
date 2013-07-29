package net.joeherrera.Thirteen.gameplay;

/**
 * Client implementations interact with Game implementations to provide gameplay.
 * Clients respond to game events and communicated by Game implementations.  
 */
public interface Client {
	// TODO redesign this method for multiple threads (game vs client)
	/**
	 * Connect to a Game and configure gameplay.  This starts the game.
	 */
	void connect();
	
	/**
	 * Games will instruct clients when they should initiate the game.  This
	 * indicates that this client can start now.
	 * @param state the state of the game
	 */
	void initiateGame(PlayerGameState state);
	
	/**
	 * Games will instruct clients when they can initiate a round. This 
	 * indicates that this client can initiate a new round now.
	 * @param state the state of the game
	 */
	void initiateRound(PlayerGameState state);
	
	/**
	 * Games will instruct clients when it is their turn. This 
	 * indicates that this client can make a play now.
	 * @param state the state of the game
	 */
	void playRound(PlayerGameState state);
}
