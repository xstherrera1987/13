package xst.dev;

// currently Game.java serializes to ~300 bytes

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;

public class STATE {
	// artificial states used for testing
	public static Game quadsState() {
		Game g = new Game();
		int player = 0;
		for (int i=0; i<44; i+=2) {
			g.state[i] = (byte) player;
			g.state[i+1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=51; i>=48; i-=2) {
			g.state[i] = (byte) player;
			g.state[i-1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=44; i<48; i++) {
			g.state[i] = (byte) player;
			player = (player + 1) % 4;
		}
		g.state[51] = 3; 	// 3spades
		g.state[48] = 2;
		g.state[50] = 3;
		g.state[49] = 3;
		g.state[39] = 2;	// 6spades
		g.state[44] = 1;	// 4 hearts
		g.state[43] = 0;	// 5 spades
		
		g.state[33] = 1;	// 7diamonds
		g.state[42] = 0;	// 5clubs
		
		g.state[47] = 2;
		g.state[48] = 3;
		
		g.initializeHands(); 
		return g;
	}
	public static Game tripsState() {
		Game g = new Game();
		int player = 0;
		for (int i=0; i<44; i+=2) {
			g.state[i] = (byte) player;
			g.state[i+1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=51; i>=48; i-=2) {
			g.state[i] = (byte) player;
			g.state[i-1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=44; i<48; i++) {
			g.state[i] = (byte) player;
			player = (player + 1) % 4;
		}
		g.state[51] = 3; 	//3spades
		g.state[48] = 2;
		g.state[50] = 3;
		g.state[49] = 3;
		g.state[39] = 2;	// 6spades
		g.state[44] = 1;	// 4 hearts
		g.state[43] = 0;	// 5 spades
		
		g.initializeHands(); 
		return g;
	}
	public static Game pairsState() {
		Game g = new Game();
		int player = 0;
		for (int i=0; i<44; i+=2) {
			g.state[i] = (byte) player;
			g.state[i+1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=51; i>=48; i-=2) {
			g.state[i] = (byte) player;
			g.state[i-1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=44; i<48; i++) {
			g.state[i] = (byte) player;
			player = (player + 1) % 4;
		}
		g.state[51] = 2;
		g.state[48] = 2;
		g.state[50] = 3;
		g.state[49] = 3;
		g.initializeHands();
		return g;
	}
	public static Game fewPairsState() {
		Game g = new Game();
		int player = 0;
		for (int i=0; i<24; i+=2) {
			g.state[i] = (byte) player;
			g.state[i+1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=24; i<52; i++) { // all other cards are garbage
			g.state[i] = 5;
		}
		g.initializeHands();
		for (int i=0; i<4; i++) { // everyone has 6 cards
			g.lastCard[i] = 5;
		}
		return g;
	}
	public static Game seqOfPairsState() {
		Game g = new Game();
		int player = 0;
		for (int i=0; i<44; i+=2) {
			g.state[i] = (byte) player;
			g.state[i+1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=51; i>=48; i-=2) {
			g.state[i] = (byte) player;
			g.state[i-1] = (byte) player;
			player = (player + 1) % 4;
		}
		for (int i=44; i<48; i++) {
			g.state[i] = (byte) player;
			player = (player + 1) % 4;
		}
		g.state[51] = 2;
		g.state[48] = 2;
		g.state[50] = 3;
		g.state[49] = 3;
		
		g.state[24] = 2;
		g.state[25] = 2;
		g.state[36] = 0;
		g.state[37] = 0;
		
		g.initializeHands();
		return g;
	}
	public static Game noPairsState() {
		// 13 card straights all around
		Game g = new Game();
		int player = 0;
		for (int i=0; i<52; i++) {
			g.state[i] = (byte) player;
			player = (player+1) % 4;
		}
		g.initializeHands();
		return g;
	}
	public static Game randomGame() {
		int humanPlayer = 0;
		Game game = new Game();
		
		for (int i=0; i<4; i++) {
			if (i == humanPlayer)
				game.humans[i] = true;
			else 
				game.humans[i] = false;
		}
		return game;
	}
	
	// example usage of serialization mechanism
	public static void serializationExample() throws Exception {
		// current time in milliseconds from epoch will be filename
		long currentTime =  System.currentTimeMillis();
		String filename = currentTime+".dat";
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename) );
		
		// make a game object and deal the cards
		Game g = new Game();
		g.deal();
		
		// write to file using default serialization mechanism
		out.writeObject(g);
		out.close();
		
		// read this object back in
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename) );
		Game h = (Game) in.readObject();
		in.close();
		
		System.out.println(g.equals(h));
		CLI_Interface.printState(h.state);
	}
	
	// precondition: filename points to a valid file, which previously serialized a Game object
	// postcondition: returns a Game object previously serialized to disk
	public static Game readStateFromFile(String filename) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename) );
			Game g = (Game) in.readObject();
			in.close();
			return g;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	// precondition: none
	// postcondition: writes a file named currentTime.dat where "currentTime" is the integer milliseconds
	//		which have passed since the epoch (Jan 1,1970)
	public static String dumpStateToFile(Game g) {
		try {
			String filename = System.currentTimeMillis() + ".13game";
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(filename) );
			out.writeObject(g);
			out.close();
			return filename;
		} catch(Exception e) {return null;}
	}
}

