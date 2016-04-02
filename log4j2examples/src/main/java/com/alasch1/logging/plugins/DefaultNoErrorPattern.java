package com.alasch1.logging.plugins;

import com.alasch1.logging.api.ErrorsPatterns;


/**
 * The default implementation has no patterns to be written into error logs.
 * 
 * @author aschneider
 *
 */
public class DefaultNoErrorPattern implements ErrorsPatterns {

	public static String[] emptyValues= {};
	
	@Override
	public String[] values() {		
		return emptyValues;
	}

}
