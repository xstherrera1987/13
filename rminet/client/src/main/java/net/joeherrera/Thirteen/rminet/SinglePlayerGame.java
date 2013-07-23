package net.joeherrera.Thirteen.rminet;
import net.joeherrera.Thirteen.core.*;
import net.joeherrera.Thirteen.gameplay.*;

public class SinglePlayerGame implements Game {
	@Override
	public PlayerGameState start(final Token token) {
		// TODO Auto-generated method stub
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

	final SinglePlayerClient client;
	public SinglePlayerGame(SinglePlayerClient client) {
		this.client = client;
	}
}
