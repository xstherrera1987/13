package net.joeherrera.Thirteen.gameplay;

/**
 *  MakePlayResponse ddescribes the outcome of an attempt to make a play.
 */
public class MakePlayResponse {	
	/**
	 * status of play attempt. Will show succes if the play was successful, or
	 * describe the error if one occured.
	 */
	GameplayStatus gameplayStatus;
	
	/**
	 *  the current state of the game regardless of whether the play was made. 
	 */
	PlayerGameState state;
}
