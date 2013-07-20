package net.joeherrera.Thirteen.rminet;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import java.util.regex.Pattern;

import net.joeherrera.Thirteen.gameplay.Game;

public class Client {
	final Io io;
	Game game;
	
	public enum Action {
		MAKE_PLAY, QUIT_GAME, SKIP_TURN, MAIN_MENU
	}
	public enum GameType {
		SINGLE_PLAYER, MULTIPLAYER, ERROR, QUITGAME
	}
	
	public Client() {
		this.io = new Io(System.in, System.out);
	}
	
	public void introduction() {
		this.io.println("Welcome to 13 Card Game.");
		this.io.println("************************");
	}
	public void mainMenu() throws IOException {
		while (true) {
			GameType gameType = chooseGameType();
			switch(gameType) {
			case SINGLE_PLAYER:
				startSinglePlayerGame();
				break;
			case MULTIPLAYER:
				return;
			case QUITGAME:
				return;
			}
		}
	}
	public GameType chooseGameType() throws IOException {
		this.io.println("Choose a Game Type.");
		final String prompt = "1: SinglePlayer\nM: Multiplayer\nQ: Quit";
		final String gameType = this.io.getString(prompt, 
				Pattern.compile("(1|M|Q)"), "Error: " + prompt);
		switch(gameType) {
		case "1":
			return GameType.SINGLE_PLAYER;
		case "M":
			return GameType.MULTIPLAYER;
		case "Q":
			return GameType.QUITGAME;
		default:
			return GameType.ERROR;
		}
	}
	public void startSinglePlayerGame() {
		this.game = new SinglePlayerGame();
		
	}
}
