package net.joeherrera.Thirteen.rminet;

import java.io.*;
import java.util.regex.*;

import net.joeherrera.Thirteen.core.*;
import static net.joeherrera.Thirteen.core.Rank.*;
import static net.joeherrera.Thirteen.core.Suit.*;

/**
 * Io abstracts interacting with the user's input and output devices. This class
 * is used to read from the keyboard and write back to the terminal. 
 */
public class Io {
	final BufferedReader rd;
	final BufferedWriter wr;

	public Io() {
		this.rd = new BufferedReader(new InputStreamReader(System.in));
		this.wr = new BufferedWriter(new PrintWriter(System.out));
	}
	public Io(InputStream is, OutputStream os) {
		this.rd = new BufferedReader(new InputStreamReader(is));
		this.wr = new BufferedWriter(new OutputStreamWriter(os));
	}
	public Io(BufferedReader rd, BufferedWriter wr) {
		this.rd = rd;
		this.wr = wr;
	}
	
	public void newLine() throws IOException {
		this.wr.write("\n");
		this.wr.flush();
	}
	public void print(String str) {
		try {
			this.wr.write(str);
			this.wr.flush();
		} catch (IOException e) { }
	}
	public void println(String str) {
		try {
			this.wr.write(str);
			this.wr.write("\n");
			this.wr.flush();
		} catch (IOException e) { }
	}
	
	/**
	 * Prompt the user for an integer.
	 * @param prompt the message displayed to describe the input that is needed
	 * @param lowerBound lowest acceptable integer value
	 * @param upperBound highest acceptable integer value
	 * @return the integer
	 * @throws IOException
	 */
	public int getInteger(String prompt, int lowerBound, int upperBound) 
			throws IOException {
		
		String input = null;
		int retVal = 0;
		while (true) {
			this.print(prompt);
			input = this.rd.readLine();
			
			try {
				retVal = Integer.parseInt(input);
				if (lowerBound <= retVal && retVal <= upperBound)
					return retVal;
				
				this.println("Out of range: " + lowerBound + " <= x <= " + upperBound);
			} catch (NumberFormatException e) {
				this.println("Error: input is not a number."); 
			}
		}
	}
	
	/**
	 * Prompt the user for input.
	 * @param prompt the message displayed to describe the input that is needed
	 * @param pattern the pattern the input must match
	 * @param errorPrompt the message displayed if there is an input mismatch
	 * @return the string the user provided
	 * @throws IOException
	 */
	public String promptForString(String prompt, Pattern pattern, String errorPrompt) 
			throws IOException {
		String input = null;
		Matcher matcher;
		while (true) {
			this.print(prompt);
			input = this.rd.readLine();
			
			matcher = pattern.matcher(input);
			if ( matcher.find() )
				return matcher.group();
				
			this.println(errorPrompt);
		}
	}
	
	// TODO implement a way to cancel card input (cancel pattern)
	/**
	 * prompt the user to enter a set of cards
	 * @return an array of cards
	 * @throws IOException
	 */
	public Card[] promptForCards() throws IOException {
		final String prompt = "Select Cards: ";
		final String rankRegex = "([2-9]|10|J|Q|K|A|j|q|k|a)";
		final String suitRegex = "(h|s|d|c|H|S|D|C|♥|♦|♣|♠)";
		final Pattern rankPattern = Pattern.compile(rankRegex);
		final Pattern suitPattern = Pattern.compile(suitRegex);
		final Pattern cardPattern = Pattern.compile("("+rankRegex+suitRegex+")|("+suitRegex+rankRegex+")");
		String input = null;
		
		Matcher cardMatcher, suitMatcher, rankMatcher;
		Card[] cards = new Card[13];
		Rank rank;
		Suit suit;
		int index = 0;
		while (true) {
			this.print(prompt);
			input = this.rd.readLine();

			cardMatcher = cardPattern.matcher(input);
			while (cardMatcher.find()) {
				rankMatcher = rankPattern.matcher( cardMatcher.group() );
				suitMatcher = suitPattern.matcher( cardMatcher.group() );
				
				if (rankMatcher.find() && suitMatcher.find() ) {
					rank = getRank( rankMatcher.group() );
					suit = getSuit( suitMatcher.group() );
					System.out.println("Rank: "+ rank);
					System.out.println("Suit: "+ suit);
					cards[index++] = Card.getCard(rank, suit);
				} else {
					this.println("Card input error: " + cardMatcher.group());
				}
			}
			
			if (index > 1)
				return cards;
			
			this.println("Error: No cards read.");
		}
	}
	
	public static final String UNKNOWN_CARD = "[?]";
	/**
	 * Get Suit value from a String
	 * @param suit as a string
	 * @return actual Suit value (the enum)
	 */
	public static Suit getSuit(String suit) {
		switch(suit) {
		case "H":
		case "h":
			return HEARTS;
		case "D":
		case "d":
			return DIAMONDS;
		case "S":
		case "s":
			return SPADES;
		case "C":
		case "c":
			return CLUBS;
		default:
			return NULL_SUIT;
		}
	}
	
	/**
	 * Get Rank value from a String
	 * @param rank as a string
	 * @return actual Rank value (the enum)
	 */
	public static Rank getRank(String rank) {
		switch(rank) {
		case "2": return TWO;
		case "A": case "a": return ACE;
		case "K": case "k": return KING;
		case "Q": case "q": return QUEEN;
		case "J": case "j": return JACK;
		case "10": return TEN;
		case "9": return NINE;
		case "8": return EIGHT;
		case "7": return SEVEN;
		case "6": return SIX;
		case "5": return FIVE;
		case "4": return FOUR;
		case "3": return THREE;
		default: return NULL_RANK;
		}
	}
}
