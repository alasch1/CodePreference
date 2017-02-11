package controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import play.mvc.Http.Request;

/**
 * Implements  utilities for input request processing
 * 
 * @author aschneider
 *
 */
public final class InputRequestHelper {
	
	public static String getInputQueryParameter(String paramName, Request request, boolean ignoreCase) {
		String paramValue = null;
		if (ignoreCase) {
			paramValue = request.getQueryString(paramName);
		}
		else {
			Map<String, String[]> queryString = request.queryString();
			List<String> insenstitiveKeys = queryString.keySet().stream()
					.filter(k -> {return k.equalsIgnoreCase(paramName);})
					.collect(Collectors.toList());
			if (insenstitiveKeys.size() > 0) {
				String insensitiveParamName = insenstitiveKeys.get(0);
				paramValue = request.getQueryString(insensitiveParamName);
			}
		}
		return paramValue;
	}
	
	private InputRequestHelper() {		
	}
}
