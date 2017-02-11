package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

import com.alasch1.configuration.api.AppConfiguration;
/**
 * Class implements format converter %config for PatternLayout
 * Interpolates the converter into configuration toString method
  * 
 * @author aschneider
 *
 */
@Plugin(name = "config", category = "Converter")
@ConverterKeys({"config"})
public class ConfigConverter extends LogEventPatternConverter {
	
	private static final String FORMAT_KEY="config";
	private static final String PREFIX="\nActive configuration:\n";
	private static final String NULL_CONFIGURATUION="null - check the execution flow";
	
	private static AppConfiguration configuration;

	protected ConfigConverter(String[] options) {
		super(FORMAT_KEY, FORMAT_KEY);
	}

	public static ConfigConverter newInstance(final String[] options) {
		return new ConfigConverter(options);
	}
	
	public static void setConfiguration(AppConfiguration configuration) {
		ConfigConverter.configuration = configuration;
	}
	
	@Override
	public void format(LogEvent arg0, StringBuilder toAppendTo) {
		toAppendTo.append(getConfig());
	}

	public String getConfig() {
		StringBuilder configString = new StringBuilder(PREFIX);
		if (null == configuration) {
			configString.append(NULL_CONFIGURATUION);
		}
		else {
			configString.append(configuration.toString());
		}
		return configString.toString().replace('\n', ' ');
	}	
}
