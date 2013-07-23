package net.joeherrera.Thirteen.gameplay;
import net.joeherrera.Thirteen.core.*;

/**
 * RoundState encapsulates a Rule and RoundType to specify the state of the 
 * current round.  
 */
public class RoundState {
	/**
	 * RoundType specifies a few special cases of round type. The first play of
	 * the game, starting a round, or normal (continuing a round). 
	 */
	public enum RoundType {
		FIRST_PLAY, START_ROUND, NORMAL;
	}

	/**
	 * The current round type.
	 */
	final RoundType roundType;
	
	/**
	 * The current rule that must be matched to make a play in this round.
	 */
	final Rule rule;
	
	public RoundState(final RoundType roundType, final Rule rule) {
		this.roundType = roundType;
		this.rule = rule;
	}
}
