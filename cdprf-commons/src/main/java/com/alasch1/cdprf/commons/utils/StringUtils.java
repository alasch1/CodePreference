package com.alasch1.cdprf.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Implements general purpose utilities
 * 
 * @author aschneider
 *
 */

/**
 * Extracts email domain as the tail part of the mail after the first appearance of @ character
 * If mail does not contain @ character the full email is retruened
 * 
 * @author aschneider
 *
 */
public final class StringUtils {
	
	private static final String SPACE = " ";
	private static final String ESC_SPACE="%20";

	public static String getEmailDomain(String email) {
		String[] mailParts = email.split("@");
		if (mailParts.length > 1) {
			return mailParts[1];
		}
		else {
			return email;
		}
	}

	public static String encodeUrl(String origUrl) {		
		try {
			return URLEncoder.encode(origUrl, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Make uncheked exception", e);
		}
	}

	public static String escapeSpace(String origValue) {
		return origValue.replace(SPACE, ESC_SPACE);
	}

	/**
	 * Returns a string representation of a local current time in ISO format;
	 * If a flag showNanos is true, presents a time with nano seconds, else truncates to seconds.
	 * 
	 * @param units
	 * @return
	 */
	public static String localTimeISO(boolean showNanos) {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		if (showNanos) {
			return formatter.format(date);
		}
		else {
			return formatter.format(date.truncatedTo(ChronoUnit.SECONDS));
		}
	}

	private StringUtils() {		
	}
}
