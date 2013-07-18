package net.joeherrera.Thirteen.core;

public class Player {
	boolean isAi;

	final String name;
	Hand hand;

	// play making decisions for ai players
	// play suggestions for human players
	final Ai ai;

	// authentication token.
	// TODO send this to client during initialization to uniquely identify them
	// for session (web = use cookie, android = send by JSON)
	final Token token;

	public Player(final String name, final Hand hand, final Ai ai, 
			final Token token) {
		this.hand = hand;
		this.ai = ai;
		this.name = name;
		this.token = token;
	}
}
