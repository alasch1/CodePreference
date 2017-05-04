package com.alasch1.appTest;

import org.apache.logging.log4j.Logger;

import com.alasch1.logging.impl.InvocationScope;
import com.alasch1.logging.impl.StdLogContext;

/**
 * Encapsulates logging of invocation scope (from/to)
 * A scope is SW module, which the application is interoperates with.
 * Supports predefined list of scopes defined by enum Scope
 * Adding a new scope demands class to be modified accordingly
 * 
 * @author ala schneider
 *
 */
public final class InvocationScopeLogger {
	
	/**
	 * Predefined scopes
	 */
	public enum Scope {
		APP,
		SCOPE1,
		SCOPE2
	};
	
	/**
	 * Logs scope enter message
	 * 
	 * @param scope
	 * @param operation
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void fromScope(Scope scope, String operation, Logger logger, String format, Object... args) {
		scopeIn( 
				new InvocationScope(scope.name(), Scope.APP.name(), operation),
				logger, format, args);
	}

	/**
	 * Logs scope enter message
	 * 
	 * @param scope
	 * @param operation
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void toScope(Scope scope, String operation, Logger logger, String format, Object... args) {
		scopeOut( 
				new InvocationScope(Scope.APP.name(), scope.name(), operation),
				logger, format, args);
	}

	/**
	 * Logs internal task invocation
	 * 
	 * @param operation
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void internalTaskStart(String operation, Logger logger, String format, Object... args) {
		scopeIn( 
				new InvocationScope(Scope.APP.name(), Scope.APP.name(), operation),
				logger, format, args);
	}
	
	/**
	 * Logs end of internal task invocation
	 * 
	 * @param operation
	 * @param logger
	 * @param format
	 * @param args
	 */
	public static void internalTaskEnd(String operation, Logger logger, String format, Object... args) {
		scopeOut( 
				new InvocationScope(Scope.APP.name(), Scope.APP.name(), operation),
				logger, format, args);
	}
	
	private static void scopeIn(InvocationScope scope, Logger logger, String format, Object... args) {
		StdLogContext.setScopeIn(scope, logger, format, args);
	}
	
	private static void scopeOut(InvocationScope scope, Logger logger, String format, Object... args) {
		StdLogContext.setScopeOut(scope, logger, format, args);
	}
	
	private InvocationScopeLogger() {
	}
	
}
