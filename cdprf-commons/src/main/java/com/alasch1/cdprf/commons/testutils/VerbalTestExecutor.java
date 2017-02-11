package com.alasch1.cdprf.commons.testutils;
/**
 * Utility for test execution with output of test name start/stop
 * @author aschneider
 *
 */
public final class VerbalTestExecutor {
	private static final String PLUS_SEP = "++++++++++++++++++++++++++++++++++++++++++++++++++";
	private static final String MINUS_SEP = "--------------------------------------------------";
	
	public static void executeTest(String testName, VerbalTest test) {
		printStartedTest(testName);
		try {
			test.run();
		}
		catch (Exception e) {
			System.out.println(String.format("**** Unexpected exception on test %s : %s ", testName, e.toString()));
			e.printStackTrace();
		}
		finally {
			printFinishedTest(testName);
		}
	}
	
	public static void executeTestAllowEx(String testName, VerbalTest test) throws Exception {
		printStartedTest(testName);
		test.run();
		printFinishedTest(testName);
	}
	
	public static void printStartedTest(String testName) {
		System.out.println(
				String.format("%s%nStarted test %s%n%s%n", 
						PLUS_SEP, testName, PLUS_SEP));	
	}
	
	public static void printFinishedTest(String testName) {
		System.out.println(
				String.format("%s%nFinished test %s%n%s%n", 
				MINUS_SEP, testName, MINUS_SEP));
	}
	
	private VerbalTestExecutor() {		
	}
}
