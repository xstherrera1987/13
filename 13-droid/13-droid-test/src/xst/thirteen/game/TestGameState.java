package xst.thirteen.game;

import junit.framework.TestCase;


public class TestGameState extends TestCase {
	GameState g;
	
	public void testPass() {
		assertTrue (6 == 6);
	}
	
	public void testFail() {
		assertFalse (6 == 6);
	}
}
