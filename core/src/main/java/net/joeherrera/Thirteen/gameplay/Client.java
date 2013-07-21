package net.joeherrera.Thirteen.gameplay;

public interface Client {
	void initiateGame(PlayerGameState state);
	void initiateRound(PlayerGameState state);
	void playRound(PlayerGameState state);
}
