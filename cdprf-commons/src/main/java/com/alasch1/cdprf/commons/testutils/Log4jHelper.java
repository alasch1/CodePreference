package com.alasch1.cdprf.commons.testutils;

/**
 * Class utility encapsulates shortcuts to log4j2 manipulations
 * that are usefull for tests
 */
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

/**
 * 
 * @author aschneider
 *
 */
public final class Log4jHelper {
	
	public static String interpolate(String source) {
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext();
		Configuration loggerConfiguration = loggerContext.getConfiguration();
		StrSubstitutor strSubs = loggerConfiguration.getStrSubstitutor();
		return strSubs.replace(source);
	}
	
	public static String getAppLogHeader() {
		String header = null;
		Appender appender = getRollingFileAppender("AppLog");
		header = new String(appender.getLayout().getHeader());
		return header;
	}
	
	public static RollingFileAppender getRollingFileAppender(String appenderName) {
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext();
		Configuration loggerConfiguration = loggerContext.getConfiguration();
		Appender appender = loggerConfiguration.getAppender(appenderName);
		if (appender != null) {
			return (RollingFileAppender) appender;
		}
		else {
			throw new NullPointerException("AppLog appender was not found in log4j2 configuration");
		}
	}
	
	private Log4jHelper() {		
	}
}
