package com.alasch1.logging.impl;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;

import com.alasch1.cdprf.logging.api.AbstractLogContext;
import com.alasch1.logging.impl.StdLogContextTags.InOutDirection;
import com.alasch1.logging.plugins.ConfigurationLookup;

/**
 * Implements standard logging context and adds the following tags into logs messages:
 * - invocation (optional) as <from>--><to> text
 * - operation (optional)
 * - user (optional) or tag ANONYMOUS 
 * - additional (optional) tags - configurable
 * 
 * Each of the tags may be modified separately
 * 
 * @author aschneider
 *
 */
public class StdLogContext extends AbstractLogContext<StdLogContextTags> {
	
	public static final String STD_FORMAT = "{}";
	
	private static final String IN_ARROW = "-->";
	private static final String OUT_ARROW = "<--";
	
	protected static LogConfiguration configuration;
	private static Logger logger;	
	
	/**
	 * Log configuration should be initialized on start-up
	 * @param configuration
	 */
	public static void setConfiguration(LogConfiguration configuration) {
		ConfigurationLookup.setConfiguration(configuration.getAppConfiguration());
		StdLogContext.configuration = configuration;
		logger = LoggerFactory.getLogger(StdLogContext.class);
	}

	/**
	 * Updates user ID tag of the current thread context
	 * 
	 * @param userId
	 */
	public static void setUser(String userId) {
		addTag(configuration.getUserIdTag(), userId);
	}
	
	/**
	 * Updates operation of the current thread context
	 * @param operation
	 */
	public static void setOperation(String operation) {
		addTag(configuration.getOperationTag(), operation);
	}
	
	/**
	 * Adds an info message into the provider logger with scope tags
	 * and cleans the src-destination tags immediately after this.
	 * 
	 * @param scope
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void setScopeIn(InvocationScope scope, Logger logger, String format, Object... args) {
		putInvocationTags(inScopeTags(scope));
		logInvocation(logger, format, args);
	}
	
	/**
	 * Adds an info message into the provider logger with scope tags
	 * and cleans the src-destination tags immediately after this.
	 * 
	 * @param scope
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void setScopeOut(InvocationScope scope, Logger logger, String format, Object... args) {
		putInvocationTags(outScopeTags(scope));
		logInvocation(logger, format, args);
	}
	
	/**
	 * Creates a default CsvpLogContext with standard tags
	 * 
	 * @return
	 */
	public static StdLogContext createDefaultContext() {
		return createUserContext(null);
	}
	
	/**
	 * Creates a default CsvpLogContext with standard tags and specified user
	 * 
	 * @param userId
	 * @return
	 */
	public static StdLogContext createUserContext(String userId) {
		return createContext(null, userId);
	}
	
	/**
	 * Creates a default CsvpLogContext with standard tags and additional tags;
	 * if userId is not null, it is assigned to the standard user tag
	 * 
	 * @param additionalTags
	 * @param nullableUserId
	 * @return
	 */
	public static StdLogContext createContext(Map<String, String> additionalTags, String nullableUserId) {
		StdLogContext context = new StdLogContext();
		context.init(new StdLogContextTags(nullableUserId), additionalTags);
		return context;
	}
	
	/**
	 * Returns a context with the current tags. Otherwise creates a new context with the default tags
	 * 
	 * @return
	 */
	public static StdLogContext useCurrent() {
		if (ThreadContext.isEmpty()) {
			// Use already existing context tags
			return createDefaultContext();
		}
		else {
			return new StdLogContext();
		}
	}
	
	public static void logInvocation(Logger logger, String format, Object... args) {
		logger.info(format, args);
		removeTag(configuration.getInvocationTag());
		removeTag(configuration.getOperationTag());
	}
	
	public static String toStringTags() {
		StringBuilder temp = new StringBuilder("LOG context:");
		Map<String, String> contextTags = ThreadContext.getContext();
		contextTags.forEach((k, v) -> {
			if (Strings.isNotBlank(v)) {
				temp.append(String.format("\t[%s: {%s}] ", k,v));
			}
		});
		return temp.toString();
	}
	
	//================================================================================
	// Private stuff
	//================================================================================
	
	private static void putApplicationContextTags(StdLogContextTags contextTagValues) {
		putInvocationTags(contextTagValues);
		addTag(configuration.getOperationTag(), contextTagValues.operation);
		addTag(configuration.getUserIdTag(), contextTagValues.userId);
	}
	
	private static void putInvocationTags(StdLogContextTags contextTagValues) {
		addTag(configuration.getInvocationTag(), buildInvocationValue(contextTagValues));	
		addTag(configuration.getOperationTag(), contextTagValues.operation);
	}
	
	private static String buildInvocationValue(StdLogContextTags appContextValues) {
		return String.format("%s%s%s", appContextValues.left, asString(appContextValues.direction), appContextValues.right);
	}
	
	private static void putAdditionalTags(Map<String, String> additionalTags) {
		for (Map.Entry<String, String> entry : additionalTags.entrySet()) {
			addTag(entry.getKey(), entry.getValue());
		}		
	}
	
	private static StdLogContextTags inScopeTags(InvocationScope scope) {
		StdLogContextTags appContextTags = new StdLogContextTags();
		appContextTags.left = scope.source;
		appContextTags.right = scope.destination;
		appContextTags.direction = InOutDirection.IN;
		if (Strings.isNotBlank(scope.operation)) appContextTags.operation = scope.operation;
		return appContextTags;
	}
	
	private static StdLogContextTags outScopeTags(InvocationScope scope) {
		StdLogContextTags appContextTags = new StdLogContextTags();
		appContextTags.left = scope.destination;
		appContextTags.right = scope.source;
		appContextTags.direction = InOutDirection.OUT;
		if (Strings.isNotBlank(scope.operation)) appContextTags.operation = scope.operation;
		return appContextTags;
	}
	
	private static String asString(InOutDirection direction) {
		switch(direction) {
		case IN: return IN_ARROW;
		case OUT: return OUT_ARROW;
		default: return Strings.EMPTY;
		}
	}
	
	@Override
	public void init(StdLogContextTags appContextValues) {
		init(appContextValues, null);
	}
	
	@Override
	public void init(StdLogContextTags appContextValues, Map<String, String> additionalTags) {
		logger.traceEntry();
		clear();
		putApplicationContextTags(appContextValues);
		if (additionalTags != null) {
			putAdditionalTags(additionalTags);
		}
	}
	
	@Override
	public String toString() {
		return toStringTags();
	}

}


