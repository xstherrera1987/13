package net.joeherrera.Thirteen.rminet;
import java.io.IOException;
import java.util.regex.Pattern;

import net.joeherrera.Thirteen.gameplay.*;


public class SinglePlayerClient implements Client {
	final SinglePlayerGame game;
	final Io io;
	Token token;
	public SinglePlayerClient(Io io) {
		this.io = io;
		this.game = new SinglePlayerGame(this);
	}
	

	@Override
	public void connect() {
		String name = null;
		try {
			name = this.io.promptForString("Enter your name: ", 
					Pattern.compile("\\S+"), 
					"Error: No whitespace allowed in name");
		} catch (IOException ignore) { }
		
		this.token = this.game.registerPlayer(name);
		this.game.start(this.token);
	}

	@Override
	public void initiateGame(PlayerGameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initiateRound(PlayerGameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRound(PlayerGameState state) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Print the state of the game on the console.
	 * @param state the state of the game
	 */
	public void showState(PlayerGameState state) {
		// TODO implement
	}
}
