package com.alasch1.logging.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerFactory {
	
	public static Logger getLogger(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

	private LoggerFactory() {
	}

}
