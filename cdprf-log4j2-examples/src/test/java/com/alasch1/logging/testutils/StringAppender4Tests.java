package com.alasch1.logging.testutils;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Implements simple in-memory appender for testing
 * 
 * @author aschneider
 *
 */
public class StringAppender4Tests extends AbstractOutputStreamAppender<StringAppender4Tests.StringOutput4TestsStreamManager> {
	
	static private LoggerContext context = (LoggerContext) LogManager.getContext(false);
	static private Configuration configuration = context.getConfiguration();
	StringOutput4TestsStreamManager outstreamMgr;	
	
	/**
	 * Creates an appender instance with the factory method and starts is, so each log
	 * event will be appended to it
	 * 
	 * @param logger
	 * @param nullablePatternString
	 * @param nullableHeaderString
	 * @return
	 */
	public static StringAppender4Tests startAppender(Logger logger, String nullablePatternString, String nullableHeaderString) {
		StringAppender4Tests appender = createStringAppender(nullablePatternString, nullableHeaderString, null);
		appender.addToLogger(logger, Level.INFO);
		appender.start();
		return appender;
	}
	
	/**
	 * Creates an appender instance with the factory method and starts is, so each log
	 * event will be appended to it
	 * 
	 * @param logger
	 * @param nullablePatternString
	 * @param nullableHeaderString
	 * @param filter
	 * @return
	 */
	public static StringAppender4Tests startAppenderWithFilter(
			Logger logger, 
			String nullablePatternString, 
			String nullableHeaderString, 
			Filter filter) {
		StringAppender4Tests appender = createStringAppender(nullablePatternString, nullableHeaderString, filter);
		appender.addToLogger(logger, Level.INFO);
		appender.start();
		return appender;
	}
	
	/**
	 * Stops appender instance, started with startAppender
	 * @param logger
	 */
	public void stopAppender(Logger logger) {
		LoggerConfig loggerConfig = configuration.getLoggerConfig(logger.getName());
		loggerConfig.removeAppender(StringAppender4Tests.class.getSimpleName());
		context.updateLoggers();
	}
	
	/**
	 * Executes logging implemented in AppendProcedure
	 * Starts/stops appender under the veil
	 * 
	 * @param procedure
	 * @param logger
	 * @param nullablePatternString
	 * @param nullableHeaderString
	 */
	public static void append(AppendProcedure procedure, Logger logger, String nullablePatternString, String nullableHeaderString) {
		StringAppender4Tests appender = startAppender(logger, nullablePatternString, nullableHeaderString);
		procedure.execute(appender);		
		appender.stopAppender(logger);
	}
	
	/**
	 * Executes logging implemented in AppendProcedure
	 * Starts/stops appender under the veil
	 * 
	 * @param procedure
	 * @param logger
	 * @param nullablePatternString
	 * @param nullableHeaderString
	 */
	public static void appendByFilter(AppendProcedure procedure, Logger logger, String nullablePatternString, Filter filter) {
		StringAppender4Tests appender = startAppenderWithFilter(logger, nullablePatternString, null, filter);
		procedure.execute(appender);		
		appender.stopAppender(logger);
	}
	
	private StringAppender4Tests(
			String name,
			Layout<? extends Serializable> layout, 
			Filter filter,
			StringOutput4TestsStreamManager outstreamMgr,
			boolean ignoreExceptions, 
			boolean immediateFlush) {
		super(name, layout, filter, ignoreExceptions, immediateFlush, outstreamMgr);
		this.outstreamMgr = outstreamMgr;
	}
	
	/**
	 * A factory method: creates appender instance with proprietary stream manager and pattern layout
	 * If argument nullablePatternString is null, default pattern layout is created

	 * @param nullablePatternString
	 * @param nullableHeaderString
	 * @return
	 */
	private static StringAppender4Tests createStringAppender(String nullablePatternString, String nullableHeaderString, Filter nullableFilter) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PatternLayout layout;
		if (nullablePatternString == null) {
			layout = PatternLayout.createDefaultLayout();
		}
		else {
			layout = PatternLayout.newBuilder()
					.withPattern(nullablePatternString)
					.withHeader(nullableHeaderString)
					.build();
		}
		
		StringOutput4TestsStreamManager streamManager = new StringOutput4TestsStreamManager(
				outputStream, 
				StringOutput4TestsStreamManager.class.getSimpleName(), 
				layout);
		
		return new StringAppender4Tests(
				StringAppender4Tests.class.getSimpleName(),
				layout,
				nullableFilter,
				streamManager,
				false, //ignoreExceptions
				true); //immediateFlush
	}
	
	private void addToLogger(Logger logger, Level level) {
		LoggerConfig loggerConfig = configuration.getLoggerConfig(logger.getName());
		loggerConfig.addAppender(this, level, null);
		context.updateLoggers();
	}
	
	/**
	 * Returns output appended so far 
	 * @return
	 */
	public String getOutput() {
		outstreamMgr.flush();
		return new String(outstreamMgr.getStream().toByteArray());
	}
	
	/**
	 * Proprietary StreamManager for StringAppender4Tests 
	 *
	 */
	protected static class StringOutput4TestsStreamManager extends OutputStreamManager {
		
		ByteArrayOutputStream stream;

		protected StringOutput4TestsStreamManager(final ByteArrayOutputStream os, final String streamName, Layout<?> layout) {
			super(os, streamName, layout, true);
			stream = os;
		}
		
		public ByteArrayOutputStream getStream() {
			return stream;
		}
	}
	
	/**
	 * AppendProcedure will be executed by append method
	 *
	 */
	@FunctionalInterface
	public static interface AppendProcedure {
		void execute(StringAppender4Tests appender);
	}
	
}
