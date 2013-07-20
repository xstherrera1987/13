package net.joeherrera.Thirteen.rminet;

import java.io.*;
import java.util.regex.*;

import net.joeherrera.Thirteen.core.*;
import static net.joeherrera.Thirteen.core.Rank.*;
import static net.joeherrera.Thirteen.core.Suit.*;

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
	
	public int getInteger(String prompt, String errorPrompt, int lowerBound, 
			int upperBound) throws IOException, Exception {
		this.wr.write(prompt);
		
		String input = null;
		int retVal = 0;
		while (true) {
			input = this.rd.readLine();
			this.newLine();
			
			try {
				retVal = Integer.parseInt(input);
				if (lowerBound < retVal && retVal < upperBound)
					return retVal;
				
				this.wr.write("Out of range: " + lowerBound + " < x < " + upperBound);				
			} catch (NumberFormatException e) {
				this.wr.write(errorPrompt); 
			}
		}
	}
	
	public String getString(String prompt, Pattern pattern, String errorPrompt) 
			throws IOException {
		this.wr.write(prompt);
		
		String input = null;
		Matcher matcher;
		while (true) {
			input = this.rd.readLine();
			this.newLine();
			
			matcher = pattern.matcher(input);
			if ( matcher.find() )
				return matcher.group();
				
			this.wr.write(errorPrompt);
		}
	}
	
	// precondition: none
	// postcondition: prompts user for cards to choose, returns integer value of card
	//		or -1 if user wants to cancel selection
	public Card[] getCards() throws IOException {
		final String prompt = "Select Cards: ";
		final String rankRegex = "([2-9]|10|J|Q|K|A)";
		final String suitRegex = "(H|S|D|C|♥|♦|♣|♠)";
		final Pattern rankPattern = Pattern.compile(rankRegex);
		final Pattern suitPattern = Pattern.compile(suitRegex);
		final Pattern cardPattern = Pattern.compile(rankRegex+suitRegex);
		String input = null;
		
		this.wr.write(prompt);
		
		Matcher cardMatcher, suitMatcher, rankMatcher;
		Card[] cards = new Card[13];
		Rank rank;
		Suit suit;
		int index = 0;
		while (true) {
			input = this.rd.readLine();

			cardMatcher = cardPattern.matcher(input);
			while (cardMatcher.find()) {
				rankMatcher = rankPattern.matcher( cardMatcher.group() );
				suitMatcher = suitPattern.matcher( cardMatcher.group() );
				
				if (rankMatcher.find() && suitMatcher.find() ) {
					rank = getRank( rankMatcher.group() );
					suit = getSuit( suitMatcher.group() );
					cards[index++] = Card.getCard(rank, suit);
				} else {
					this.wr.write("Card input error: " + cardMatcher.group() + "\n");
				}
			}
			
			if (index > 1)
				return cards;
			else
				this.wr.write("Error: No cards read.\n");
		}
	}
	
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
	public static Rank getRank(String rank) {
		switch(rank) {
		case "2": return TWO;
		case "A": return ACE;
		case "K": return KING;
		case "Q": return QUEEN;
		case "J": return JACK;
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
