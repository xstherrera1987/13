package net.joeherrera.Thirteen.core;

import java.util.ArrayList;
import java.util.List;

/**
 * matches Card[] to a Play if possible or else NO_PLAY
 */
public class PlayMatcher {
	/**
	 * determine which play (if any) this hand or any subset of cards 
	 * of this hand can make.
	 * 
	 * @param cards the hand to test
	 * @return the plays that are possible or empty list if none
	 */
	public static List<Play> determinePlays(final Card[] cards) {
		final List<Play> retVal = new ArrayList<>();
		// TODO implement
		
		return retVal;
	}
}
