package net.joeherrera.Thirteen.gameplay;

import java.net.InetAddress;

public class Token {
	final String id;
	InetAddress address;
	final int playerNumber;
	
	public static final Token AI_0 = new Token("AI0", null, 0);
	public static final Token AI_1 = new Token("AI1", null, 1);
	public static final Token AI_2 = new Token("AI2", null, 2);
	public static final Token AI_3 = new Token("AI3", null, 3);
	public static final Token[] AIs = { AI_0, AI_1, AI_2, AI_3 };
	
	public Token(final String id, final InetAddress address, final int playerNumber) {
		super();
		this.id = id;
		this.address = address;
		this.playerNumber = playerNumber;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public String getId() {
		return this.id;
	}
}
