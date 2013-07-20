package net.joeherrera.Thirteen.rminet;

import java.io.IOException;

public class Main {		
	static Client client;
	public static void main() {
		Main.client = new Client();
		try {
			Main.client.introduction();
			Main.client.mainMenu();
		} catch(IOException e) { }
	}
}
