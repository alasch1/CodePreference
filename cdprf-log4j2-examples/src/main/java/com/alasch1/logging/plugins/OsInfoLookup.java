package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.JavaLookup;

/**
 * Implements format lookup ${osinfo:all} for PatternLayout
 * Interpolates the lookup into OS info
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "osinfo", category = "Lookup")
public class OsInfoLookup extends InfoLookupBase {
	
	public static final String OS_INFO_TITLE = "OS and Java info";
	
	private static final String OS = "OS";
	private static final String JAVA = "Java";
	private static final String HARDWARE = "Hardware";
	private static final String LOCALE = "Locale";
	private static final String USER = "User";
	private static final String USER_DIR = "UserDir";
	private static final String LOCALHOST = "Local machine hostname";

	private static final String USER_NAME_PROP = "user.name";
	private static final String USER_LANG_PROP = "user.language";
	private static final String USER_TZONE_PROP = "user.timezone";
	private static final String USER_COUNTRY_PROP = "user.country";
	private static final String USER_DIR_PROP = "user.dir";
	
	private final JavaLookup javaLookup = new JavaLookup();
	
	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%s:", OS_INFO_TITLE));
		
		sb.append(END_LINE).append(String.format(VAL_STD_FORMAT, OS, javaLookup.getOperatingSystem()))
			.append(END_LINE).append(String.format(VAL_STD_FORMAT, JAVA, javaLookup.getRuntime(), javaLookup.getVirtualMachine()))
			.append(END_LINE).append(String.format(VAL_STD_FORMAT, HARDWARE, javaLookup.getHardware()))
			.append(END_LINE).append(String.format(VAL_STD_FORMAT, LOCALE, javaLookup.getLocale()))
			.append(END_LINE).append(TAB).append(USER).append(String.format(": %s %s %s %s", 
					System.getProperty(USER_NAME_PROP), 
					System.getProperty(USER_LANG_PROP),
					System.getProperty(USER_TZONE_PROP), 
					System.getProperty(USER_COUNTRY_PROP)))
			.append(String.format("%s: %s", USER_DIR, System.getProperty(USER_DIR_PROP)))
			.append(END_LINE).append(String.format(VAL_STD_FORMAT,LOCALHOST, HostNameInfo.get()));
		
		sb.append(END_LINE);	
		return sb.toString();
	}

}
