package net.joeherrera.Thirteen.rminet;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {		
	static Reader r = new InputStreamReader(System.in);
	static Scanner scn = new Scanner(r);
	static String input = "";
	static boolean quit = false;
	public static void main() {
		while (!quit) {
			out.println("13 Client");
			out.println("Enter 1 for single player game.");
			out.println("Enter M for multiplayer.");
			out.println("Enter Q to Quit.");
			out.print(">");
			
			input = scn.next();
			switch(input) {
			case "1":
				// TODO implement
				break;
			case "M":
				// TODO implement
				break;
			case "Q":
				quit = true;
				out.println("Quitting");
				break;
			default:
				out.println("Invalid Choice");
				break;
			}
		}
	}
}
