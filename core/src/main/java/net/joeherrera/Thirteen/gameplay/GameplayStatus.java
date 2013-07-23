package net.joeherrera.Thirteen.gameplay;

/**
 *  GameplayStatus describes state of the game after a client attempts to issue
 *  commands to configure the game or during gameplay.
 */
public enum GameplayStatus {
	SUCCESS, 
	// gameplay errors
	NOT_TURN, PLAY_TOO_WEAK, PLAY_RULE_MISMATCH,
	// other
	BAD_AUTHENTICATION_TOKEN, NAME_IN_USE
}
