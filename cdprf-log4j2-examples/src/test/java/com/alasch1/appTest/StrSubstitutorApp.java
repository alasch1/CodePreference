package com.alasch1.appTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import com.alasch1.cdprf.commons.utils.ConfigUtil;

/**
 * This simple application demonstrates how to access
 * log2j configuration properties and other lookups
 * It was created due to the question, which arrived because of 
 * my stackoverflow post
 * 
 * @author alasch1
 *
 */
public class StrSubstitutorApp {
	private static Logger LOG;

	public static void main(String[] args) {
		ConfigUtil.setLog4jConfig("log4j2-simple.xml");
		// !!! Log initialization must be after system properties setting
		LOG = LogManager.getLogger();

		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		Configuration configuration = context.getConfiguration();
		StrLookup lookup = configuration.getStrSubstitutor().getVariableResolver();
		LOG.info("lookup baseDir={}", lookup.lookup("baseDir"));
		LOG.info("lookup tag1={}", lookup.lookup("tag1"));
		LOG.info("lookup bundle:logCommons:tag1={}", lookup.lookup("bundle:logCommons:tag1"));
		LOG.info("lookup log4j:configParentLocation={}", lookup.lookup("log4j:configParentLocation"));
		LOG.info("lookup java:jvm={}", lookup.lookup("java:vm"));
		
		// Example of substitution
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("ddd", "AAA");
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String replaced = sub.replace(lookup.lookup("tag1"));
		LOG.info("repaced value of lookup(\"tag1\")={}", replaced);
	}

}
