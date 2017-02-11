package com.alasch1.cdprf.json.masking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

/**
 * Implements masking of a json property String value with the 5-asterisks
 * The original json is not changed; produces a new json string with masked values of the property
 *  
 * @author aschneider
 *
 */
public class JsonPropertyMasker { 
	private static final String PROP_NAME_REGEX = "(?i)\"%s\":\"";
	private static final String PROP_VALUE_REGEX = "\"";
	private static final String VALUE_HIDEN = "*****\"";
	private static final int SPLIT_SIZE = 2;
	
	private String originalBody;
	private String propertyName;
	private List<String> outputValues = new ArrayList<String>();
	
	/**
	 * Substitutes all entries of a given property name and returns the modified Json
	 * Can be applied only to string values 
	 * 
	 * @param originalBody
	 * @param propertyName
	 * @return
	 */
	public static String mask(String originalBody, String propertyName) {
		JsonPropertyMasker replacer = new JsonPropertyMasker(originalBody, propertyName);
		return replacer.execute();
	}
	
	private JsonPropertyMasker(String originalBody, String propertyName) {
		this.originalBody = originalBody;
		this.propertyName = propertyName;
	}
	
	private String execute() {
		splitByPropertyName(originalBody);
		String updatedBody = outputValues.stream().collect(Collectors.joining(""));
		return updatedBody;
	}
	
	private boolean splitByPropertyName(String bodyFragment) {
		String[] pwdKeySplits =  bodyFragment.split(String.format(PROP_NAME_REGEX, propertyName), SPLIT_SIZE) ;
		if (pwdKeySplits.length == SPLIT_SIZE) {
			// Password key was found in bodyFragment
			String fragmentPrefix = bodyFragment.substring(0, bodyFragment.indexOf(pwdKeySplits[SPLIT_SIZE-1]));
			String nextFragment = substitutePropertyValue(fragmentPrefix, pwdKeySplits[SPLIT_SIZE-1]);
			if (Strings.isNotBlank(nextFragment)) {
				return splitByPropertyName(nextFragment);
			}
		}
		outputValues.add(bodyFragment);
		return false;
	}
	
	private String substitutePropertyValue(String prefix, String fragmentWithPropertyValue) {
		String[] pwdValueSplits = fragmentWithPropertyValue.split(PROP_VALUE_REGEX, SPLIT_SIZE);
		if (pwdValueSplits.length !=SPLIT_SIZE) {
			// Cannot substitute due to wrong number of splits
			outputValues.add(fragmentWithPropertyValue);
			return null;
		}
		else {
			outputValues.add(prefix);
			outputValues.add(VALUE_HIDEN);
			return pwdValueSplits[SPLIT_SIZE-1];
		}
	}
}