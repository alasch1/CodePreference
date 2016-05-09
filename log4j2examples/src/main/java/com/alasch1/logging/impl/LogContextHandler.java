package com.alasch1.logging.impl;

import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import com.alasch1.logging.api.LogContext;
import com.alasch1.logging.plugins.ConfigurationLookup;

/**
 * Encapsulates implementation of CsplLogContext
 * CsplLogContext adds the following tags into logs messages:
 * - process id
 * - thread id
 * - user id (if exists) or tag ANONYMOUS 
 * - additional (optional) tags - configurable
 * 
 * Each createContext method works in similar way:
 * 	- creates CsplLogContext for a current thread and returns the context
 * 	- the user which received the created context should take care to clear it in the end
 * 
 * @author aschneider
 *
 */
public class LogContextHandler implements LogContext {
	
	public static final String ANONYMOUS_USER = "";
	
	private static Logger logger;	
	
	protected static LogConfiguration configuration;
	
	private long processId;
	private String threadId;
	private String userId;
	
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
	 * Creates a default LogContext with ANONYMOUS_USER as a user
	 * 
	 * @return
	 */
	public static LogContext createContext() {
		return createContext(null, null);
	}
	
	/**
	 * Creates a default LogContext with ANONYMOUS_USER and puts additional context tags
	 * 
	 * @param addContextTags
	 * @return
	 */
	public static LogContext createContext(Map<String,String> addContextTags) {
		return createContext(null, addContextTags);
	}
	
	/**
	 * Creates LogContext with given value of userId
	 * 
	 * @param userId
	 * @return
	 */
	public static LogContext createContext(String userId) {
		return createContext(userId, null);
	}
	
	/**
	 * Creates CsplLogContext with a given value of userId and additional context tags
	 * 
	 * 
	 * @param userId
	 * @param addContextTags
	 * @return
	 */
	public static LogContext createContext(String userId, Map<String,String> addContextTags) {
		LogContextHandler context = new LogContextHandler(userId);
		context.init(addContextTags);
		return context;
	}
	
	/**
	 * Updates user ID of the current thread context
	 * @param userId
	 */
	public static void updateUserId(String userId) {
		ThreadContext.put(configuration.getUserIdTag(), userId);
	}
	
	protected LogContextHandler(String nullableUserId) {
		this.userId = nullableUserId != null ? nullableUserId : ANONYMOUS_USER;
		this.processId = JvmProcessID.get();
		this.threadId = Long.toString(Thread.currentThread().getId());
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LogContextHandler.logger = logger;
	}

	@Override
	public void init() {
		init(null);
	}

	@Override
	public void init(Map<String, String> addContextTagsTags) {
		logger.entry();
		clear();
		putDefaultContextTags();
		if (addContextTagsTags != null) {
			for (Map.Entry<String, String> entry : addContextTagsTags.entrySet()) {
				ThreadContext.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void clear() {
		ThreadContext.removeStack();
	}
	
	public long getProcessId() {
		return processId;
	}
	
	public String getThreadId() {
		return threadId;
	}
	
	public String getUserId() {
		return userId;
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
	
	protected void putDefaultContextTags() {
		ThreadContext.put(configuration.getUserIdTag(), userId);
		ThreadContext.put(configuration.getProcessIdTag(), String.valueOf(processId));
		ThreadContext.put(configuration.getThreadIdTag(), String.valueOf(threadId));
	}

}
