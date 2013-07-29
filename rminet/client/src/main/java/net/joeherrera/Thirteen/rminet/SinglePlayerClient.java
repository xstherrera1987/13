package net.joeherrera.Thirteen.rminet;
import net.joeherrera.Thirteen.gameplay.*;


public class SinglePlayerClient implements Client {
	SinglePlayerGame game;
	public SinglePlayerClient() {
		this.game = new SinglePlayerGame(this);
	}
	

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
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
}
