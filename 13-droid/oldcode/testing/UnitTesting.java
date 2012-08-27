package xst.dev;
import static xst.dev.CLI_Interface.printHand;
import static xst.dev.CLI_Interface.printPlay;
import static xst.dev.CLI_Interface.printState;
import static xst.dev.PLAYS.NOPLAY;
import static xst.dev.STATE.fewPairsState;
import static xst.dev.STATE.noPairsState;
import static xst.dev.STATE.pairsState;
import static xst.dev.STATE.quadsState;
import static xst.dev.STATE.seqOfPairsState;
import static xst.dev.STATE.tripsState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class UnitTesting {
	// gameplay tests
	public static void fullRandomGameTest() {
		Game g = new Game();
		g.startGame(true);
	}
	
	public static void fullPredefinedGameTest(String filename) {
		Game g = STATE.readStateFromFile(filename);
		// this field is transient so must be reassigned upon deserialization
		g.cli.br = new BufferedReader(new InputStreamReader(System.in));
		g.startGame(false);
	}
	
	public static String predefineGameFile() {
		Game g = new Game();
		g.deal();
		String filename = STATE.dumpStateToFile(g);
		return filename;
	}
	
	// CLI - KB tests
	public static void TEST_promptMakePlay() {
		CLI_Interface cli = new CLI_Interface();
		cli.g = new Game();
		cli.g.deal();
		cli.g.initializeHands();
		
		cli.promptMakePlay();
	}
	
	// AI tests
	public static void TEST_findSeqPairs_Symmetric() {
		Game g = seqOfPairsState();
		
		// test modificiations
		g.state[16] = 3;
		g.state[17] = 3;
		g.state[30] = 0;
		g.state[31] = 0;
		
		System.out.println("INITIAL STATE");
		CLI_Interface.printState(g.state);
		System.out.println("\n");
		
		boolean found;
		for (int i=0; i<4; i++) {
			g.turn = i;
			// find sequences of 3 pairs (most common, still rare)
			found = g.findSeqPairs(g.playsFound[i], 3);
			System.out.println("player"+i+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("no seq of pairs found");
		}
	}
	public static void TEST_findSeqPairs_Random() {
		Game g = new Game();	
		g.deal();
		g.initializeHands();
		System.out.println("random deal");
		CLI_Interface.printState(g.state);
		System.out.println("\n\n");
		
		boolean found;
		for (int i=0; i<4; i++) {
			g.turn = i;
			// find sequences of 3 pairs (most common, still rare)
			found = g.findSeqPairs(g.playsFound[i], 3);
			System.out.println("player"+i+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("no seq of pairs found");
		}
	}
	public static void TEST_findLowStraight_Random() {
		Game g = new Game();	
		g.deal();
		g.initializeHands();
		System.out.println("Random Deal");
		CLI_Interface.printState(g.state);
		System.out.println("\n");
		
		boolean found;
		for (int i=0; i<4; i++) {
			g.turn = i;
			found = g.findLowStraight(g.playsFound[i], 3);
			System.out.println("player"+i+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none");
		}
	}
	public static void TEST_findLowStraight_Symmetric() {
		Game g = STATE.noPairsState();
		CLI_Interface.printState(g.state);
		System.out.println("\n");
		
		boolean found;
		for (int i=0; i<4; i++) {
			g.turn = i;
			found = g.findLowStraight(g.playsFound[i], 11);
			System.out.println("player"+i+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none");
		}
	}
	public static void TEST_findLowQuads_Random() {
		Game g = new Game();	
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		boolean found;
		
		long start,length;
		g.turn = 0;
		for (int i=0; i<4; i++, g.turn++) {
			start = System.nanoTime();
			found = g.findLowQuads(g.playsFound[i]);
			length = System.nanoTime() - start;
			System.out.println("\nplayer"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none found");
			System.out.println("found in "+length/1000.0+" ms");
		}
	}
	public static void TEST_findLowQuads_Symmetric() {
		Game g = quadsState();
		CLI_Interface.printState(g.state);
		boolean found;
		
		g.turn = 0;
		for (int i=0; i<4; i++, g.turn++) {
			found = g.findLowQuads(g.playsFound[i]);
			System.out.println("\nplayer"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none found");
		}
	}
	public static void TEST_findLowTrips_Random() {
		Game g = new Game();	
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		
		long start,length;
		boolean found;
		g.turn = 0;
		for (int i=0; i<4; i++, g.turn++) {
			start = System.nanoTime();
			found = g.findLowTrips(g.playsFound[i]);
			length = System.nanoTime() - start;
			System.out.println("\nplayer"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none found");
			System.out.println("found in "+length/1000.0+" ms");
		}
	}
	public static void TEST_findLowTrips_Symmetric() {
		Game g = tripsState();
		CLI_Interface.printState(g.state);
		boolean found;
		
		long start,length;
		g.turn = 0;
		for (int i=0; i<4; i++, g.turn++) {
			start = System.nanoTime();
			found = g.findLowTrips(g.playsFound[i]);
			length = System.nanoTime() - start;
			System.out.println("\nplayer"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none found");
			System.out.println("found in "+length/1000.0+" ms");
		}
	}
	public static void TEST_findLowPair_Symmetric() {
		Game g = pairsState();
		CLI_Interface.printState(g.state);
		boolean found;
		
		g.turn = 0;
		for (int i=0; i<4; i++, g.turn++) {
			found = g.findLowPair(g.playsFound[i]);
			System.out.println("\nplayer"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none found");
		}
	}
	public static void TEST_findLowPair_Random() {
		boolean found;
		System.out.println("\n\nrandom deal");
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		System.out.println("\n\n");
		long start,length;
		for (int i=0; i<4; i++) {
			start = System.nanoTime(); 
			found = g.findLowPair(g.playsFound[i]);
			length = System.nanoTime() - start;
			System.out.println("---------------------------------------");
			System.out.println("player"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none");
			System.out.println("found in "+length/1000.0+" ms");
			g.turn = (g.turn+1) % 4;
			System.out.println("---------------------------------------");
		}
	}
	public static void TEST_findLowPair_FewPairs() {
		boolean found;
		Game g = fewPairsState();
		CLI_Interface.printState(g.state);
		System.out.println("\n\n");
		long start,length;
		g.turn = 0;
		for (int i=0; i<4; i++) {
			start = System.nanoTime(); 
			found = g.findLowPair(g.playsFound[i]);
			length = System.nanoTime() - start;
			System.out.println("---------------------------------------");
			System.out.println("player"+g.turn+" ");
			if (found)
				CLI_Interface.printHand(g.playsFound[i]);
			else
				System.out.println("none");
			System.out.println("found in "+length/1000.0+" ms");
			g.turn = (g.turn+1) % 4;
			System.out.println("---------------------------------------");
		}
	}
	
	// AI larger than
	public static void TEST_findSingleLargerThan() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		CLI_Interface.printHand(g.hands[0]);
		int randCard = (int) ((Math.random() * 100) % 52);
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findSingleLargerThan(g.playsFound[0], randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}
	public static void TEST_findPairLargerThan() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		CLI_Interface.printHand(g.hands[0]);
		int randCard = (int) ((Math.random() * 100) % 52);
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findPairLargerThan(g.playsFound[0], randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}
	public static void TEST_findTripsLargerThan() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		CLI_Interface.printHand(g.hands[0]);
		int randCard = (int) ((Math.random() * 100) % 52);
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findTripsLargerThan(g.playsFound[0], randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}
	public static void TEST_findQuadsLargerThan() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		CLI_Interface.printHand(g.hands[0]);
		int randCard = (int) ((Math.random() * 100) % 52);
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findQuadsLargerThan(g.playsFound[0], randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}	
	public static void TEST_findStraightLargerThan() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		CLI_Interface.printHand(g.hands[0]);
		int randCard = (int) ((Math.random() * 100) % 52);
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findStraightLargerThan(g.playsFound[0], 3, randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}
	public static void TEST_findStraightLargerThan_ERROR_00() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		g.turn = 0;
		
		// recreating a specific error state
		g.hands[0][0] = 5; // AD
		g.hands[0][1] = 6; // AC
		g.hands[0][2] = 7; // AS
		g.hands[0][3] = 14; // QC
		g.hands[0][4] = 15; // QS
		g.hands[0][5] = 16; // JH
		g.hands[0][6] = 19; // JS
		g.hands[0][7] = 29; // 8D
		g.hands[0][8] = 30; // 8C
		g.hands[0][9] = 35; // 7S
		g.hands[0][10] = 37; // 6D
		g.hands[0][11] = 38; // 6C
		g.hands[0][12] = 41; // 5D
		
		
		CLI_Interface.printHand(g.hands[0]);
		Arrays.toString(g.hands[0]);
		int randCard = 41; // 5D
		System.out.print("RAND= ");
		System.out.println(CLI_Interface.getCard(randCard));
		boolean found = g.findStraightLargerThan(g.playsFound[0], 3, randCard);
		if (found) {
			System.out.print("FOUND= ");
			CLI_Interface.printHand(g.playsFound[0]);
		} else {
			System.out.println("None larger");
		}
	}
	
	// TODO: test and verify this, likely has problems
	public static void TEST_AI_initiateRound() {
		
	}
	
	// gameplay tests
	public static void TEST_deal() {
		Game g = new Game();
		g.deal();
		CLI_Interface.printState(g.state);
	}
	public static void TEST_init() {
		Game g = pairsState();
		CLI_Interface.printState(g.state);
		//g.init();
		CLI_Interface.printHand(g.current);
		for (int i=1; i<51; i++) {
			g.AI_round();
			CLI_Interface.printState(g.state);
			CLI_Interface.printHand(g.current);
		}		
	}
	public static void TEST_findLowSingle_Symmetric() {
		Game g = noPairsState();
		CLI_Interface.printState(g.state);
		boolean found = g.findLowSingle(g.playsFound[0]);
		System.out.println(found);
	}
	public static void TEST_findLowSingle_Random() {
		Game g = noPairsState();
		CLI_Interface.printState(g.state);
		boolean found;
		for (int i=0; i<4; i++) {
			found = g.findLowSingle(g.playsFound[0]);
			System.out.println("Player"+i+": "+found);
		}
	}
	public static void TEST_start() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		for (int i=0; i<4; i++) {
			CLI_Interface.printHand(g.hands[i]);
		}
	}

	// miscellaneous tests
	public static void TEST_calculateHand() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		for (int i=0; i<4; i++) {
			System.out.println("Player"+i);
			g.calculateHand(i);
			CLI_Interface.printHand(g.hands[i]);
		}
	}
	public static void TEST_isPair_Symmetric() {
		byte[] hand = new byte[12];
		for (int i=3; i<12; i++) {
			hand[i] = -1;
		}
		
		hand[0] = 8;
		hand[1] = 13;
		hand[2] = 17;
		hand[3] = 21;
		hand[4] = 26;
		
		CLI_Interface.printHand(hand);
		System.out.println( Game.isStraight(hand) );
	}
	public static void TEST_isPair_Random() {
		
	}
	public static void TEST_numPairs_Symmetric() {
		Game g = pairsState();
		CLI_Interface.printState(g.state);
		int num;
		for (int i=0; i<4; i++) {
			num = g.numPairs(i);
			System.out.println("Player"+i+" has "+num+" pairs");
		}
		
	}
	public static void TEST_numPairs_Random() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		int num;
		for (int i=0; i<4; i++) {
			num = g.numPairs(i);
			System.out.println("Player"+i+" has "+num+" pairs");
		}
		
	}
	public static void TEST_numTrips_Symmetric() {
		Game g = tripsState();
		CLI_Interface.printState(g.state);
		int num;
		for (int i=0; i<4; i++) {
			num = g.numTrips(i);
			System.out.println("Player"+i+" has "+num+" triples");
		}
		
	}
	public static void TEST_numTrips_Random() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		int num;
		for (int i=0; i<4; i++) {
			num = g.numTrips(i);
			System.out.println("Player"+i+" has "+num+" triples");
			System.out.println(g.instantWin(i) );
		}
		
	}
	
	// player tests
	public static void TEST_isSequenceOfPairs() {
		byte[] hand = new byte[12];
		for (int i=3; i<12; i++) {
			hand[i] = -1;
		}
		
		hand[0] = 8;
		hand[1] = 13;
		hand[2] = 17;
		hand[3] = 21;
		hand[4] = 26;
		
		CLI_Interface.printHand(hand);
		System.out.println( Game.isStraight(hand) );
	}
	public static void TEST_isStraight_Symmetric() {
		byte[] hand = new byte[12];
		for (int i=3; i<12; i++) {
			hand[i] = -1;
		}
		
		hand[0] = 8;
		hand[1] = 13;
		hand[2] = 17;
		hand[3] = 21;
		hand[4] = 26;
		
		CLI_Interface.printHand(hand);
		System.out.println( Game.isStraight(hand) );
	}
	public static void TEST_isStraight_Random() {
		Random r = new Random();
		byte[] cards = {-1,-1,-1,-1, -1,-1,-1,-1, -1,-1,-1,-1, -1};
		// 100 iterations because straights are fairly common
		int iters = 100;
		int result;
		System.out.println(iters+" iterations:");
		for (int i=0; i<iters; i++) {
			// clear cards
			for (int j=0; j<13; j++) cards[j] = -1;
			// random number, (3 through 7)
			// a straight has to be at least 3 cards, more than 7 is unlikely
			int numCards = 3 + (r.nextInt(4));
			// fill cards with "numCards" random numbers
			for (int j=0; j<numCards; j++) {
				cards[j] = (byte) r.nextInt(52);
			}
			siftAndSort(cards);
			result = Game.isStraight(cards);
			if (result != NOPLAY) {
				CLI_Interface.printHand(cards);
				CLI_Interface.printPlay(result);
			}
		}
	}
	public static void TEST_isSeqOfPairs_Symmetric() {
		byte[] cards = {-1,-1,-1,-1, -1,-1,-1,-1, -1,-1,-1,-1, -1};
		cards[0] = 4;
		cards[1] = 5;
		cards[2] = 8;
		cards[3] = 9;
		cards[4] = 12;
		cards[5] = 13;
		CLI_Interface.printHand(cards);
		System.out.println(Game.isSeqOfPairs(cards) );
	}
	public static void TEST_isSeqOfPairs_Random() {
		Random r = new Random();
		byte[] cards = {-1,-1,-1,-1, -1,-1,-1,-1, -1,-1,-1,-1, -1};
		// 10000 iterations because SeqOfPairs are so uncommon
		int iters = 10000;
		int result;
		System.out.println(iters+" iterations:");
		for (int i=0; i<iters; i++) {
			// clear cards
			for (int j=0; j<13; j++) cards[j] = -1;
			// random number, (6,7, or 8)
			// because sequence of pairs have to be 6 or 8 cards (10 is extremely unlikely)
			int numCards = 6 + (r.nextInt(3));
			// fill cards with "numCards" random numbers
			for (int j=0; j<numCards; j++) {
				cards[j] = (byte) r.nextInt(52);
			}
			siftAndSort(cards);
			result = Game.isSeqOfPairs(cards);
			if (result != NOPLAY) {
				CLI_Interface.printHand(cards);
				CLI_Interface.printPlay(result);
			}
		}
	}
	public static void TEST_isDragonHead_Symmetric() {
		Game g = noPairsState();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		for (int i=0; i<4; i++) {
			boolean dh = g.hasDragonHead(i);
			System.out.println("player"+i+" dragonhead="+dh);
		}
	}
	public static void TEST_isDragonHead_Random() {
		Game g = new Game();
		g.deal();
		g.initializeHands();
		CLI_Interface.printState(g.state);
		for (int i=0; i<4; i++) {
			boolean dh = g.hasDragonHead(i);
			System.out.println("player"+i+" dragonhead="+dh);
		}
	}
	public static void TEST_isPlay() {
		Random r = new Random();
		byte[] cards = {-1,-1,-1,-1, -1,-1,-1,-1, -1,-1,-1,-1, -1};
		int iters = 1000;
		System.out.println(iters+" iterations:");
		int playFound = NOPLAY;
		for (int i=0; i<iters; i++) {
			// clear cards
			for (int j=0; j<13; j++) cards[j] = -1;
			// random number between 2 and 6
			int numCards = 2 + (r.nextInt(5));
			// fill cards with "numCards" random numbers
			for (int j=0; j<numCards; j++) {
				cards[j] = (byte) r.nextInt(52);
			}
			siftAndSort(cards);
			playFound = Game.isPlay(cards);
			
			if (playFound != NOPLAY) {
				CLI_Interface.printHand(cards);
				System.out.print("i="+i+" ");
				CLI_Interface.printPlay(playFound);
				System.out.println("-----------------------------");
			}
		}
	}
	
	// CLI tests
	public static void TEST_getCardString() {
		
	}
	private static void TEST_printState() {
		byte[] test = new byte[52];
		for (int i=0; i<13; i++) 
			for(int j=0; j<4; j++) 
				test[(i*4)+j] = (byte) j;
		printState(test);
	}
	public static void TEST_siftNegatives() {
		CLI_Interface cli = new CLI_Interface();
		cli.cardsChosen[0] = 2;
		cli.cardsChosen[1] = -1;
		cli.cardsChosen[2] = 4;
		cli.cardsChosen[3] = 6;
		cli.cardsChosen[4] = -1;
		cli.cardsChosen[5] = 3;
		cli.cardsChosen[6] = 7;
		siftAndSort(cli.cardsChosen);
		for (int i=0; i<13; i++) {
			System.out.print(cli.cardsChosen[i]+" ");
		}
	}
	public static void TEST_chooseCard() {
		CLI_Interface cli = new CLI_Interface();
		cli.chooseCard(49);
		cli.chooseCard(33);
		cli.chooseCard(15);
		cli.chooseCard(4);
		cli.chooseCard(8);
		siftAndSort(cli.cardsChosen);
		printHand(cli.cardsChosen);
	}
	public static void TEST_unchooseCard() {
		CLI_Interface cli = new CLI_Interface();
		cli.chooseCard(49);
		cli.chooseCard(33);
		cli.chooseCard(15);
		cli.chooseCard(4);
		cli.chooseCard(8);
		siftAndSort(cli.cardsChosen);
		printHand(cli.cardsChosen);
		System.out.println("------unchoose-------");
		cli.unchooseCard(8);
		cli.unchooseCard(15);
		cli.unchooseCard(4);
		CLI_Interface.printHand(cli.cardsChosen);
	}

	/////////////////////////////////////////////////////////////////
	// UTIL METHODS
	/////////////////////////////////////////////////////////////////
	public static void siftAndSort(byte[] cards) {
		byte[] temp = new byte[13];
		// insertion sort is efficient for small data sets like this
		int i, j, newValue;
		for (i=0; i<13; i++) temp[i] = -1;
		// replace -1 with 127 so they go to end during sort
		for (i=0; i<13; i++) {
			if (cards[i] >= 0) 
				temp[i] = cards[i];
			else
				temp[i] = 127;
		}
		
		// (insertion) sort temp array
		for (i=1; i<13; i++) {
			newValue = temp[i];
			j = i;
	        while (j > 0 && temp[j - 1] > newValue) {
	        	temp[j] = temp[j - 1];
	            j--;
	        }
	        temp[j] = (byte) newValue;
		}
		
		// copy back to cards (replace 127's with -1)
		for (i=0; i<13; i++) cards[i] = -1;
		for (i=0; i<13; i++) {
			if (temp[i] != 127)
				cards[i] = temp[i];
			else
				cards[i] = -1;
		}
	}
}

