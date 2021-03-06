package com.alasch1.logging.plugins;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * NullAppender plugin implementation
 * 
 * @author ala schneider
 *
 */
@Plugin(name = "NullAppender", category = "Core", elementType = "appender", printObject = true)
public class NullAppender extends AbstractAppender {

	protected NullAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}

	@Override
	public void append(LogEvent event) {
	}
	
	@PluginFactory
	public static NullAppender createAppender(
			@PluginAttribute("name") String name,
			@PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filters") Filter filter) {

		if (name == null) {
            LOGGER.error("No name provided for NullAppender");
            return null;
        }
		return new NullAppender(name, filter, layout, ignoreExceptions);
	}

}
