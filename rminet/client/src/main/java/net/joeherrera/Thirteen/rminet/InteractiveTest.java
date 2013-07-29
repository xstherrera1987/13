package net.joeherrera.Thirteen.rminet;

/**
 * Interactive test is a router/controller that maps command line arguments
 * to interactive testing methods in other classes. 
 */
public class InteractiveTest {
	private InteractiveTest(){}
	public static void startTest(String[] args) {
		String[] testArgs = null;
		if (args.length >= 2) {
			testArgs = new String[args.length];
			System.arraycopy(args, 1, testArgs, 0, args.length - 1);
		}

		switch (args[0]) {
		case "integerInput":
			IoInteractiveTest.getInteger();
		case "stringInput":
			IoInteractiveTest.getString();
		case "cardInput":
			IoInteractiveTest.getCard();
			break;
		default:
			printUsage();
			break;
		}
	}
	
	public static void printUsage() {
		System.out.println("Usage:");
		System.out.println("  program test testName");
		System.out.println("tests names:");
		System.out.println("  integerInput");
		System.out.println("  stringInput");
		System.out.println("  cardInput");
	}
}
