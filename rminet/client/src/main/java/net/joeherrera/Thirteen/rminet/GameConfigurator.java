package net.joeherrera.Thirteen.rminet;

import java.io.*;
import java.util.regex.*;
import net.joeherrera.Thirteen.gameplay.*;

public class GameConfigurator {
	static final Io io = new Io(System.in, System.out);
	static Client client;
	
	public enum Action {
		MAKE_PLAY, QUIT_GAME, SKIP_TURN, MAIN_MENU
	}
	public enum GameType {
		SINGLE_PLAYER, MULTIPLAYER, ERROR, QUITGAME
	}
	
	public static void printIntroduction() {
		GameConfigurator.io.println("Welcome to 13 Card Game.");
		GameConfigurator.io.println("************************");
	}
	public static void mainMenuLoop() throws IOException {
		while (true) {
			GameType gameType = promptForGameType();
			switch(gameType) {
			case SINGLE_PLAYER:
				GameConfigurator.client = new SinglePlayerClient();
				break;
			case MULTIPLAYER:
				return;
			case QUITGAME:
				return;
			case ERROR:
				GameConfigurator.io.println("Input Error.");
				break;
			default:
				break;
			}
		}
	}
	// TODO test interactively
	public static GameType promptForGameType() throws IOException {
		GameConfigurator.io.println("Choose a Game Type.");
		final String prompt = "1: SinglePlayer\nM: Multiplayer\nQ: Quit";
		final String gameType = GameConfigurator.io.promptForString(prompt, 
				Pattern.compile("1|M|m|Q|q"), "Error: " + prompt);
		switch(gameType) {
		case "1":
			return GameType.SINGLE_PLAYER;
		case "M": case "m":
			return GameType.MULTIPLAYER;
		case "Q": case "q":
			return GameType.QUITGAME;
		default:
			return GameType.ERROR;
		}
	}
}
