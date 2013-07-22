package net.joeherrera.Thirteen.gameplay;
import net.joeherrera.Thirteen.core.*;

public class RoundState {
	public enum RoundType {
		FIRST_PLAY, START_ROUND, NORMAL;
	}
	
	final Rule rule;
	final RoundType roundType;
	
	public RoundState(RoundType roundType, Rule rule) {
		this.roundType = roundType;
		this.rule = rule;
	}
}
