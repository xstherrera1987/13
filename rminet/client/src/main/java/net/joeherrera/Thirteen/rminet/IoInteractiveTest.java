package net.joeherrera.Thirteen.rminet;

import java.io.IOException;
import java.util.regex.Pattern;

import net.joeherrera.Thirteen.core.Card;

public class IoInteractiveTest {
	static Io testIo = new Io();
	
	public static void getInteger() {
		int lower = -9;
		int upper = 5;
		
		int result = 0;
		try {
			result = testIo.getInteger("Enter Integer ("+lower+"<x<"+upper+"): ", 
					lower, upper);
		} catch(IOException e) { }
		
		System.out.println(result);
	}
	public static void getString() {
		Pattern pattern = Pattern.compile("[A-Z]");
		
		String result = null;
		try {
			result = testIo.promptForString("Enter a capital letter: ", pattern, "Not a capital letter");
		} catch(IOException e) { }
		
		System.out.println(result);
	}
	public static void getCard() {
		try {
			Card[] cards = testIo.promptForCards();
		} catch (IOException e) { }
	}
}
