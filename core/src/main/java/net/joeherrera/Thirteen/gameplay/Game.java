package net.joeherrera.Thirteen.gameplay;

import net.joeherrera.Thirteen.core.*;

public interface Game {
	int registerPlayer(Player p); 
	PlayerGameState makePlay(Card[] cards, Token token);
}
