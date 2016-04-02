package com.alasch1.logging.mocks;

import com.alasch1.logging.api.ErrorsPatterns;


public class ErrorsPatterns4Tests implements ErrorsPatterns {

	public static String ERROR_PATTERN_1 = "AAA_BBB_CCC";
	public static String ERROR_PATTERN_2 = "1234567_OOOOO";
	public static String ERROR_PATTERN_3 = "Harry Potter";
	
	public static String[] TEST_VALUES = {ERROR_PATTERN_1, ERROR_PATTERN_2, ERROR_PATTERN_3};
	
	@Override
	public String[] values() {		
		return TEST_VALUES;
	}

}
