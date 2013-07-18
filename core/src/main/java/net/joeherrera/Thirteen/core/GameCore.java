package net.joeherrera.Thirteen.core;

public class GameCore {
	// the full deck of cards
	Card[] deck;
	// reference to the current play
	Card[] handOnTable;

	// human and AI players in order of turns
	final Player[] players = new Player[4];
	// maintains who has skipped their turn
	final boolean[] skippedTurn = new boolean[4];
	// the index of player whose turn it is
	int turn;

	Rule roundType;
	// marks if game is over
	boolean gameWon;
	// allows the game to terminate
	boolean quitGame = false;

	public GameCore() {
		this.deck = Card.getDeck();
		this.handOnTable = new Card[] {};
		
		this.roundType = Rule.FIRST_PLAY;
		this.gameWon = false;
	}
	
	/**
	 * 
	 */
	public void gameLoop() {
		// TODO implement
	}
}