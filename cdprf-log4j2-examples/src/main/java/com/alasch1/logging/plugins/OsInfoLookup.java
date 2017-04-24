package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.JavaLookup;

/**
 * Implements format lookup ${osinfo} for PatternLayout
 * Interpolates the lookup into OS info
 * 
 * @author as390x
 *
 */
@Plugin(name = "osinfo", category = "Lookup")
public class OsInfoLookup extends InfoLookupBase {
	
	public static final String OS_INFO_TITLE = "OS and Java info";
	
	private final JavaLookup javaLookup = new JavaLookup();
	
	private static final String USER_NAME_PROP = "user.name";
	private static final String USER_LANG_PROP = "user.language";
	private static final String USER_TZONE_PROP = "user.timezone";
	private static final String USER_COUNTRY_PROP = "user.country";
	private static final String USER_DIR_PROP = "user.dir";
	
	@Override
	protected String getInfo() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%s:", OS_INFO_TITLE));
		
		sb.append(String.format("%n\tOS: %s", javaLookup.getOperatingSystem()));
		
		sb.append(String.format("%n\tJava: %s, %s", javaLookup.getRuntime(), javaLookup.getVirtualMachine()));
	
		sb.append(String.format("%n\tHardware: %s", javaLookup.getHardware()));
		
		sb.append(String.format("%n\tLocale: %s", javaLookup.getLocale()));
		
		sb.append(String.format("%n\tUser: %s %s %s %s UserDir: %s", System.getProperty(USER_NAME_PROP), System.getProperty(USER_LANG_PROP),
				System.getProperty(USER_TZONE_PROP), System.getProperty(USER_COUNTRY_PROP), System.getProperty(USER_DIR_PROP)));
	
		sb.append(String.format("%n\tLocal machine hostname: %s", HostNameInfo.get()));
		
		sb.append(END_LINE);	
		return sb.toString();
	}

}
