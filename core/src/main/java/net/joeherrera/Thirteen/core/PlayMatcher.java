package net.joeherrera.Thirteen.core;

import static net.joeherrera.Thirteen.core.Play.NO_PLAYS;

import java.util.ArrayList;
import java.util.List;

/**
 * matches Card[] to a Play if possible or else NO_PLAY
 * 
 */
public class PlayMatcher {
	/**
	 * determine which play (if any) this hand makes
	 * 
	 * @param hand the hand to test
	 * @return the plays that are possible
	 */
	public static List<Play> determinePlays(final Hand hand) {
		final List<Play> retVal = new ArrayList<>();

		// TODO implement
		
		// if plays can be made then return list with one value: NO_PLAY
		retVal.add(NO_PLAYS);
		return retVal;
	}
}
