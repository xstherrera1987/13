package net.joeherrera.Thirteen.core;

import java.net.InetAddress;

public class Token {
	final String id;
	InetAddress address;
	
	public static final Token AI_TOKEN = new Token("AI", null );
	
	public Token(String id, InetAddress address) {
		super();
		this.id = id;
		this.address = address;
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
