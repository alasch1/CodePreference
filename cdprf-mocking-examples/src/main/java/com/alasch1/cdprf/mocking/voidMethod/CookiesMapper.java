package com.alasch1.cdprf.mocking.voidMethod;

import java.util.HashMap;
import java.util.Map;

public final class CookiesMapper {
	
	public static Map<String,String> buildCookiesMap(Cookies rawCookies) {
		Map<String, String> cookiesMap = new HashMap<>();
		rawCookies.forEach(c -> {
			cookiesMap.put(c.getName(), c.getValue());
		});
		return cookiesMap;
	}

	private CookiesMapper() {
	}

}
