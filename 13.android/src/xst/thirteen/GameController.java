package xst.thirteen;
import xst.thirteen.game.Game;
import static xst.thirteen.ControlCodes.*;

public abstract class GameController {
	Game game;
	private int[] cardsChosen = new int[13];
	private int[] temp = new int[13];
	private int playerNumber = -1;
	
	public GameController(Game game) {
		this.game = game;
		playerNumber = 0;
	}
	
	public GameController(Game game, int playerNumber) {
		this(game);
		this.playerNumber = playerNumber;
	}
	
	// precondition: this is called by the platform (main) code to start the game
	// postcondition: begin initialization
	public abstract void startGame();
	
	// precondition: none
	// postcondition: the game has changed state to "roundState"
	public abstract void newState(int roundState);
	
	// precondition: hand is owned by current player
	// postcondition: makes this play (hand) on the game and returns RoundState
	public int makePlay(Hand hand) {
		try {
		return game.makePlay(hand, playerNumber);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// probably dump game state to file
			return ERROR;
		}
	}
	
	public void skipTurn() {
		
	}
	public void chooseCard(int card) {
		
	}
	public void unchooseCard(int card) {
		
	}
	public void unchooseAll() {
		
	}
	public boolean isChosenPlay() {
		return false;
	}
	
	// precondition: cards is length 13
	// postcondition: strong cards (low numbers) are at the front
	//		and any -1 in the array are at the end
	public void siftAndSort() {
		// insertion sort is efficient for small data sets like this
		int i, j, newValue;
		for (i=0; i<13; i++) temp[i] = NULLCARD;
		// replace NULLCARD with 127 so they go to end during sort
		for (i=0; i<13; i++) {
			if (cardsChosen[i] >= 0) 
				temp[i] = cardsChosen[i];
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
		
		// copy back to cardsChosen (replace 127's with NULLCARD)
		for (i=0; i<13; i++) cardsChosen[i] = NULLCARD;
		for (i=0; i<13; i++) {
			if (temp[i] != 127)
				cardsChosen[i] = temp[i];
			else
				cardsChosen[i] = NULLCARD;
		}
	}
		
}
