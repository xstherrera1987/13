package net.joeherrera.Thirteen.gameplay;

import java.net.InetAddress;

import net.joeherrera.Thirteen.core.Ai;
import net.joeherrera.Thirteen.core.Hand;

public class Player {
	final Token token;
	final InetAddress address;
	final String name;
	final Ai ai;
	
	Hand hand;
	boolean isAi;

	public Player(final String name, final Ai ai, final Token token, final InetAddress address) {
		this.ai = ai;
		this.name = name;
		this.token = token;
		this.address = address;
	}
}
