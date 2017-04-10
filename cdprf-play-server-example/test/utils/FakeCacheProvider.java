package utils;

import examples.guiceExamples.cache.CacheProvider;
import play.cache.CacheApi;

/**
 * Fake cache access for JUnit
 * 
 * @author aschneider
 *
 */
public class FakeCacheProvider implements CacheProvider {
	private final CacheApi fakeCache = new FakeCache();

	@Override
	public CacheApi getCache() {
		System.out.println("getting  fakeCache..");
		return fakeCache;
	}

}
