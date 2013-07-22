package net.joeherrera.Thirteen.gameplay;

import java.net.InetAddress;


/**
 * Authentication token used to uniquely identify players
 */
public class Token {
	public final int token;
	
	public static final Token AI_0 = new Token("Ai0", null);
	public static final Token AI_1 = new Token("Ai1", null);
	public static final Token AI_2 = new Token("Ai2", null);
	public static final Token AI_3 = new Token("Ai3", null);
	public static final Token[] AIs = { AI_0, AI_1, AI_2, AI_3 };
	
	public Token(final String name, final InetAddress address) {
		String both = name + (null != address ? address.toString() : "");
		this.token = both.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if ( !other.getClass().equals(Token.class) )
			return false;
		
		Token otherToken = (Token) other;
		return otherToken.token == this.token;
	}
	@Override public int hashCode() { return this.token; }
}
