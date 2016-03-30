package com.alasch1.codepreference.commons.configuration.api;

/**
 * An error object which will be thrown when configuration fails early in the
 * lifetime of the program. It does not require try-catch.
 * It is propagated, since the program can't function if the configuration
 * doesn't work 
 * 
 * @author aschneider
 *
 */
public class ConfigurationError extends Error {

	private static final long serialVersionUID = 1L;

	public ConfigurationError() {
	}

	public ConfigurationError(String message) {
		super(message);
	}

	public ConfigurationError(Throwable cause) {
		super(cause);
	}

	public ConfigurationError(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationError(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
