package com.alasch1.cdprf.json.masking;

/**
 * List of all properties names containing password
 * 
 * @author aschneider
 *
 */
public final class PasswordPropertiesNames {
	
	private static final String PASSWORD_PROPERTY_CAPITAL_CASE = "Password";
	private static final String PASSWORD_PROPERTY_LCASE = "password";
	private static final String HOST_PASSWORD_PROPERTY = "hostpassword";
	private static final String COMPANY_PASSWORD="CompanyPswd";
	
	private static final String[] names = {
			PASSWORD_PROPERTY_LCASE, HOST_PASSWORD_PROPERTY, COMPANY_PASSWORD, PASSWORD_PROPERTY_CAPITAL_CASE};
	

	public static String[] getNames() {
		return names;
	}
	
	private PasswordPropertiesNames() {		
	}
	
}
