package xst.thirteen;
import static xst.thirteen.ControlCodes.*;

public class GameController {
	// TODO have reference back to the controlling activity
	public GameSystem sys;
	public int[] cardsChosen = new int[13];
	public int[] temp = new int[13];
	public Hand current = null;
	
	// precondition: this is called by the platform (main) code to start the 
	// 	game
	// postcondition: begin initialization
	public void startGame() {
		
	}
	
	// precondition: hand is owned by current player
	// postcondition: makes this play (hand) on the game and returns 
	// 	RoundState
	public int makePlay(Hand hand) {
		try {
			return sys.makePlay(current);
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
		//TODO implement
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
