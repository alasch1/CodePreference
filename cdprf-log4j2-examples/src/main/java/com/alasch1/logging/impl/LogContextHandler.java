package com.alasch1.logging.impl;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;

import com.alasch1.cdprf.logging.api.AppContextValues;
import com.alasch1.cdprf.logging.api.AppContextValues.InOutDirection;
import com.alasch1.cdprf.logging.api.LogContext;
import com.alasch1.logging.plugins.ConfigurationLookup;

/**
 * Encapsulates implementation of LogContext
 * LogContext adds the following tags into logs messages:
 * - process id
 * - thread id
 * - user id (if exists) or tag ANONYMOUS 
 * - additional (optional) tags - configurable
 * 
 * Each createContext method works in similar way:
 * 	- creates LogContext for a current thread and returns the context
 * 	- the user which received the created context should take care to clear it in the end
 * 
 * @author ala schneider
 *
 */
public class LogContextHandler implements LogContext {
	
	public static final String IN_ARROW = "-->";
	public static final String OUT_ARROW = "<--";

	private static Logger logger;	
	protected static LogConfiguration configuration;

	/**
	 * Log configuration should be initialized on start-up
	 * @param configuration
	 */
	public static void setConfiguration(LogConfiguration configuration) {
		ConfigurationLookup.setConfiguration(configuration.getAppConfiguration());
		LogContextHandler.configuration = configuration;
		logger = LoggerFactory.getLogger(LogContextHandler.class);
	}

	/**
	 * Creates a default CsvpLogContext with with application standard values
	 * 
	 * @return
	 */
	public static LogContext createContext(AppContextValues appContextValues) {
		return createContext(appContextValues, null);
	}
	
	/**
	 * Creates a default CsvpLogContext with application standard values and additional context tags
	 * 
	 * @param addContextTags
	 * @return
	 */
	public static LogContext createContext(AppContextValues appContextValues, Map<String,String> addContextTags) {
		LogContextHandler context = new LogContextHandler();
		context.init(appContextValues, addContextTags);
		return context;
	}
	
	/**
	 * If tag values already exists, returns a context with the current tags. Otherwise creates a new context with the default tags
	 * 
	 * @param userId
	 * @param addContextTags
	 * @return
	 */
	public static LogContext useCurrent() {
		return setUser(null, null);
	}
	
	/**
	 * If tag values already exists, returns a context with the current tags. 
	 * Replaces existing application standard values with provided userId and additional tags values.
	 * If no tag values exist, creates a new context with provided userId and additional tags values.
	 * 
	 * @param userId
	 * @param addContextTags
	 * @return
	 */
	public static LogContext setUser(String userId, Map<String,String> addContextTags) {
		LogContextHandler context = new LogContextHandler();
		if (ThreadContext.isEmpty()) {
			// Use already existing context tags
			context.init(new AppContextValues(userId), addContextTags);
		}
		else {
			updateUserId(userId);				
			if (addContextTags != null) {
				context.putAdditionalTags(addContextTags);
			}
		}
		return context;
	}

	/**
	 * Updates user ID of the current thread context
	 * @param userId
	 */
	public static void updateUserId(String userId) {
		if (!ThreadContext.containsKey(configuration.getUserIdTag())) {
			ThreadContext.put(configuration.getUserIdTag(), userId != null ? userId : AppContextValues.ANONYMOUS_USER);
		}
	}
	
	protected LogContextHandler() {
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LogContextHandler.logger = logger;
	}

	@Override
	public void init(AppContextValues appContextValues) {
		init(appContextValues, null);
	}

	@Override
	public void init(AppContextValues appContextValues, Map<String, String> additionalTags) {
		logger.traceEntry();
		clear();
		putApplicationContextTags(appContextValues);
		if (additionalTags != null) {
			putAdditionalTags(additionalTags);
		}
	}

	protected static void putApplicationContextTags(AppContextValues appContextValues) {
		putInvocationTags(appContextValues);
		ThreadContext.put(configuration.getOperationTag(), appContextValues.operation);
		if (Strings.isNotBlank(appContextValues.userId)) {
			ThreadContext.put(configuration.getUserIdTag(), appContextValues.userId);
		}
	}
	
	private void putAdditionalTags(Map<String, String> additionalTags) {
		for (Map.Entry<String, String> entry : additionalTags.entrySet()) {
			if (!ThreadContext.containsKey(entry.getKey())) {
				ThreadContext.put(entry.getKey(), entry.getValue());
			}
		}		
	}

	private static void putInvocationTags(AppContextValues appContextValues) {
		ThreadContext.put(configuration.getInvocationTag(), buildInvocationValue(appContextValues));		
	}
	
    private static void logInvocation(Logger logger, String format, Object... args) {
    	logger.info(String.format(format, args));
    	ThreadContext.remove(configuration.getInvocationTag());
    }

	private static String buildInvocationValue(AppContextValues appContextValues) {
		return String.format("%s%s%s", appContextValues.source, asString(appContextValues.direction), appContextValues.destination);
	}
	
	private static String asString(InOutDirection direction) {
		switch(direction) {
		case IN: return IN_ARROW;
		case OUT: return OUT_ARROW;
		default: return Strings.EMPTY;
		}
	}
	
	public void clear() {
		ThreadContext.clearAll();
	}
	
	public boolean isValid() {
		return !ThreadContext.isEmpty();
	}
	
	public LogConfiguration getConfiguration() {
		return configuration;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("Log context: {\n");
		Map<String, String> contextTags = ThreadContext.getContext();
		contextTags.forEach((k, v) -> {
			temp.append(String.format("\t[%s]: {%s}%n", k,v));
		});
		temp.append("}");
		return temp.toString();
	}
	
}
