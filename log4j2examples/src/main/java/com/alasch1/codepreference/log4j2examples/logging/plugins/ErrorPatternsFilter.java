package com.alasch1.codepreference.log4j2examples.logging.plugins;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

import com.alasch1.codepreference.log4j2examples.logging.api.ErrorsPatterns;

/**
 * This filter checks if a messages matches to any of ErrorPattern values. 
 * Decision to filter message or not depends on the value of dropOnMatch: 
 * 		false - allow the matched message;
 * 		true - deny the matched message
 * The filter matches against the values of ErrorPattern enumerator
 * 
 * @author aschneider
 *
 */
@Plugin(name = "ErrorPatternsFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class ErrorPatternsFilter extends AbstractFilter {

	private static final long serialVersionUID = 1L;
	
	private Set<String> matchingValues;
	
    private ErrorPatternsFilter(final Set<String> values, final boolean dropOnMatch) {
    	super(dropOnMatch ? Result.DENY : Result.NEUTRAL,
    		dropOnMatch ? Result.NEUTRAL:	Result.DENY	);
    	this.matchingValues = values;
    }
    
    @Override
	public Result filter(LogEvent event) {
    	final String text = event.getMessage().getFormat();
		return filter(text);
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        if (msg == null) {
            return onMismatch;
        }
        return filter(msg.getFormat());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        if (msg == null) {
            return onMismatch;
        }
        return filter(msg.toString());
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		return filter(msg);
	}
	
	private Result filter(final String msg) {
		if (msg == null) {
			return onMismatch;
		}
		
		boolean matchFound = false;
		for (String value : matchingValues) {
			if (msg.indexOf(value) != -1) {
				matchFound = true;
				break;
			}
		}
		return matchFound ? onMatch : onMismatch;
	}

	@Override
	public String toString() {
        return String.format("onMatch=%s,  onMismatch=%s, matcingValues=%s", onMatch, onMismatch, matchingValues.toString());
 	}

	@PluginFactory
	public static ErrorPatternsFilter createFilter(
        @PluginAttribute("errorsPatterns") final String errorsPatternsClassName,
        @PluginAttribute("dropOnMatch") final Boolean dropOnMatch) {
			final Set<String> matchingValues = fillMatchingValues(errorsPatternsClassName);
        	return new ErrorPatternsFilter(matchingValues, dropOnMatch);
    }
	
	private static Set<String> fillMatchingValues(final String errorsPatternsClassName) {
		Set<String> matchingValues = new HashSet<String>();
		
		try {
			Class<?> classTemp = Class.forName(errorsPatternsClassName);
			ErrorsPatterns errorsPatterns = (ErrorsPatterns)classTemp.newInstance();
			for (String pattern : errorsPatterns.values()) {
				matchingValues.add(pattern);
			}
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			LOGGER.error("Failed to init matching values for a filter:", e);
		}
		catch(ClassCastException e) {
			LOGGER.error("Invalid errors patterns class was provided:", e);
		}
		return matchingValues;
	}
    
    /**
     * @return a copy of existing matchingMap
     */
    public List<String> getMatchingValues() {
    	return new ArrayList<String>(matchingValues);
    }
}
