package com.alasch1.cdprf.commons.testutils;

/**
 * 
 * @author aschneider
 *
 */
public final class ConfigUtil {

	private static final String TEST_CONFIG_DIR = "./src/test/resources";
	private static final String CONFIG_DIR_PROPERTY = "config.dir";
	
	public static void resetConfigDirProperty() {
		setConfigDirProperty("");
	}
	
	public static void setConfigDirProperty() {
		setConfigDirProperty(TEST_CONFIG_DIR);
	}
	
	public static void setLog4jConfig(String log4jxml) {
		System.setProperty("log4j.configurationFile", log4jxml);
	}
	
	public static void setConfigDirProperty(String configDir) {
		String confDirProperty = getConfigDirProperty();
		if (null == confDirProperty || confDirProperty.isEmpty() || ! confDirProperty.equals(configDir)) {
			// When running as JUnit 
			System.setProperty(CONFIG_DIR_PROPERTY, configDir);
			System.out.println("Defined " + CONFIG_DIR_PROPERTY + ":" + System.getProperty(CONFIG_DIR_PROPERTY));
		}
		else {
			// When running as MAVEN (test or install)
			System.out.println("Predefined " + CONFIG_DIR_PROPERTY + ":" + confDirProperty);
		}
	}
	
	public static String getConfigDirProperty() {
		return System.getProperty(CONFIG_DIR_PROPERTY);
	}
	
	private ConfigUtil() {
	}

}
