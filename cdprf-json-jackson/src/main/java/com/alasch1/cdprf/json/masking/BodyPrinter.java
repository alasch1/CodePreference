package com.alasch1.cdprf.json.masking;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Limits size of body as a string 
 * 
 * @author aschneider
 *
 */
public final class BodyPrinter {

	private static final int MAX_BODY_STR_SZIE = 1000;
	
	public static String body4print(JsonNode body) {
		return body4print(body, MAX_BODY_STR_SZIE);
	}
	
	public static String body4print(JsonNode body, int maxSize) {
		return asPrinatble(body.toString(), maxSize);
	}
	
	public static String body4print(String body) {
		return asPrinatble(body, MAX_BODY_STR_SZIE);
	}
	
	public static String body4print(String body, int maxSize) {
		return asPrinatble(body, maxSize);
	}
	
	public static String maskedProperties(String origBody, String[] properties2mask) {
		String maskedBody = origBody;
		for (String s : properties2mask) {
			maskedBody = JsonPropertyMasker.mask(origBody, s); 
		}
		return maskedBody;
	}	
	
	protected static String asPrinatble(String bodyString, int maxSize) {
		if (bodyString.length() <= maxSize) {
			return maskedPasswords(bodyString);
		}
		else {
			return maskedPasswords(String.format("%s ...truncated (body length=%d exceeds max=%d)...", 
					bodyString.substring(0, maxSize), bodyString.length(), maxSize));
		}
	}
	
	private static String maskedPasswords(String origBody) {
		return maskedProperties(origBody, PasswordPropertiesNames.getNames());
	}
	
	private BodyPrinter() {
	}

}
