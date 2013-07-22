package net.joeherrera.Thirteen.gameplay;

/**
 * Client implementations interact with Game implementations to provide gameplay.
 * Clients respond to game events and communicated by Game implementations.  
 */
public interface Client {
	/**
	 * Indicates that this client should initiate the game now.
	 * @param state the state of the game
	 */
	void initiateGame(PlayerGameState state);
	
	/**
	 * Indicates that this client should initiate a new round now.
	 * @param state the state of the game
	 */
	void initiateRound(PlayerGameState state);
	
	/**
	 * Indicates that this client should make a play now.
	 * @param state the state of the game
	 */
	void playRound(PlayerGameState state);
}
