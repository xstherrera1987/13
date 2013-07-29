package net.joeherrera.Thirteen.rminet;
import net.joeherrera.Thirteen.core.*;
import net.joeherrera.Thirteen.gameplay.*;

public class SinglePlayerGame implements Game {
	final SinglePlayerClient client;
	public SinglePlayerGame(SinglePlayerClient client) {
		this.client = client;
	}
	
	@Override
	public PlayerGameState start(final Token token) {
		// TODO Auto-generated method stub
		
		// TODO call or implement game loop here
		
		return null;
	}

	@Override
	public Token registerPlayer(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MakePlayResponse makePlay(final Card[] cards, final Token token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void quitGame(final Token token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerGameState getCurrentState(final Token token) {
		// TODO Auto-generated method stub
		return null;
	}
}
