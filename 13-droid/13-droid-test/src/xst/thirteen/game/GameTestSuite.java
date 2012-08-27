package xst.thirteen.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameTestSuite extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(GameTestSuite.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestGameLogic.class);
		suite.addTestSuite(TestGameState.class);
		//$JUnit-END$
		return suite;
	}

}
