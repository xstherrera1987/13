package net.joeherrera.Thirteen.rminet;

/**
 * Interactive test is a router/controller that maps command line arguments
 * to interactive testing methods in other classes. 
 */
public class InteractiveTest {
	public static void startTest(String[] args) {
		String[] testArgs = null;
		if (args.length >= 2) {
			testArgs = new String[args.length];
			System.arraycopy(args, 1, testArgs, 0, args.length - 1);
		}

		switch (args[0]) {
		case "integerInput":
			integerInput();
			break;
		}
	}

	public static void integerInput() {
		IoInteractiveTest ioTest = new IoInteractiveTest();
		ioTest.testGetInteger();
	}

	public static void printUsage() {
		// TODO Auto-generated method stub

	}
}
