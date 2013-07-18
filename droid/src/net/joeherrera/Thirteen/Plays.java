package net.joeherrera.Thirteen;
import java.util.TreeMap;
public class Plays {
	// SIMPLE HIERARCHY_00
	public static final int NOPLAY = 0;
	public static final int SINGLE  = 1;

	public static final int STRAIGHT_3  = 2;
	public static final int STRAIGHT_4  = 3;
	public static final int PAIR  = 4;
	public static final int STRAIGHT_5  = 5;
	
	public static final int STRAIGHT_6  = 6;
	public static final int STRAIGHT_7  = 7;
	public static final int TRIPS  = 8;
	
	public static final int TWO  = 9;
	public static final int SEQ_PAIRS_3  = 10;
	public static final int QUADS  = 11;
	
	public static final int STRAIGHT_8  = 12;
	public static final int STRAIGHT_9  = 13;
	
	public static final int TWO_2  = 14;
	public static final int SEQ_PAIRS_4  = 15;
	public static final int QUADS_2  = 16;
	
	public static final int STRAIGHT_10  = 17;
	public static final int STRAIGHT_11  = 18;
	
	public static final int TWO_3  = 19;
	public static final int SEQ_PAIRS_5  = 20;
	public static final int QUADS_3  = 21;
	
	public static final int TWO_4  = 22;
	public static final int DRAGON  = 23;
	
	public static final TreeMap<Integer, String> playNames = new TreeMap<Integer, String>();
	public static final int[] lengths = new int[24];
	// initialize playNames dictionary (lookup table)
	static {
		playNames.put(NOPLAY, "No Play");
		playNames.put(SINGLE, "Single");
		playNames.put(PAIR , "Pair");
		playNames.put(TRIPS, "Triples");
		playNames.put(TWO, "Two");
		playNames.put(TWO_2, "Two x2");
		playNames.put(TWO_3, "Two x3");
		playNames.put(SEQ_PAIRS_3, "3-PairSequence");
		playNames.put(SEQ_PAIRS_4, "4-PairSequence");
		playNames.put(SEQ_PAIRS_5, "5-PairSequence");
		playNames.put(QUADS, "Bomb");
		playNames.put(QUADS_2, "Bomb x2");
		playNames.put(QUADS_3, "Bomb x3");
		playNames.put(STRAIGHT_3, "3-Straight");
		playNames.put(STRAIGHT_4, "4-Straight");
		playNames.put(STRAIGHT_5,  "5-Straight");
		playNames.put(STRAIGHT_6, "6-Straight");
		playNames.put(STRAIGHT_7, "7-Straight");
		playNames.put(STRAIGHT_8, "8-Straight");
		playNames.put(STRAIGHT_9, "9-Straight");
		playNames.put(STRAIGHT_10, "10-Straight");
		playNames.put(STRAIGHT_11, "11-Straight");
		playNames.put(DRAGON, "Dragon");
		
		lengths[ NOPLAY] = 0;
		lengths[ SINGLE ] = 1;
		lengths[ STRAIGHT_3 ] = 3;
		lengths[ STRAIGHT_4 ] = 4;
		lengths[ PAIR ] = 2;
		lengths[ STRAIGHT_5 ] = 5;
		lengths[ STRAIGHT_6 ] = 6;
		lengths[ STRAIGHT_7 ] = 7;
		lengths[ TRIPS ] = 3;
		lengths[ TWO ] = 1;
		lengths[ SEQ_PAIRS_3 ] = 6;
		lengths[ QUADS ] = 4;
		lengths[ STRAIGHT_8 ] = 8;
		lengths[ STRAIGHT_9 ] = 9;
		lengths[ TWO_2 ] = 2;
		lengths[ SEQ_PAIRS_4 ] = 8;
		lengths[ QUADS_2 ] = 8;
		lengths[ STRAIGHT_10 ] = 10;
		lengths[ STRAIGHT_11 ] = 11;
		lengths[ TWO_3 ] = 3;
		lengths[ SEQ_PAIRS_5 ] = 10;
		lengths[ QUADS_3 ] = 12;
		lengths[ TWO_4 ] = 4;
		lengths[ DRAGON ] = 12;
	}
}