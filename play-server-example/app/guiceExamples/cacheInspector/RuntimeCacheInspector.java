package guiceExamples.cacheInspector;

import javax.inject.Inject;

import play.cache.CacheApi;

public class RuntimeCacheInspector implements CacheInspector {

	@Inject
	private CacheApi tokenCache;
	
	@Override
	public Object getValue(String key) {
		return tokenCache.get(key);
	}

	@Override
	public void removeEntry(String key) {
		tokenCache.remove(key);		
	}

	@Override
	public void restartExpiration(String key) {
		Object value = tokenCache.get(key);
		// Probably null value should be handled (for example, skip set in case of null)
		tokenCache.set(key, value);
	}

}
