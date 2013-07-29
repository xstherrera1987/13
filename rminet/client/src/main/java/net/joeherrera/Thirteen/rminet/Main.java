package net.joeherrera.Thirteen.rminet;

import java.io.IOException;

/**
 *	Thirteen command-line client entry point. 
 */
public class Main {		
	public static void main(String[] args) {
		if (args.length >= 2 && "test".equals(args[0])) {
			if (null != args[1]) {
				String[] testArgs = new String[args.length]; 
				System.arraycopy(args, 1, testArgs, 0, args.length-1);
				InteractiveTest.startTest(testArgs);
			} else {
				InteractiveTest.printUsage();
			}
		} else {
			try {
				GameConfigurator.printIntroduction();
				GameConfigurator.mainMenuLoop();
				GameConfigurator.exitMessage();
			} catch(IOException e) { }
		}
	}
}
