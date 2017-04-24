package com.alasch1.logging.plugins;

import org.apache.logging.log4j.core.lookup.StrLookup;

public abstract class TestLookupBase {

	protected static <L extends StrLookup> String getLookup(String key, Class<L> lookupClazz) throws InstantiationException, IllegalAccessException {
		L lookup = lookupClazz.newInstance();
		System.out.println(lookup.lookup(key));
		return flatString(lookup.lookup(key));
	}

	protected static String flatString(String source) {
		return source.replace('\n', ' ');
	}
	

}
