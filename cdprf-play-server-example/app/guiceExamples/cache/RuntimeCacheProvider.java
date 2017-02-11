package guiceExamples.cache;

import javax.inject.Inject;

import play.cache.CacheApi;
import play.cache.NamedCache;

public class RuntimeCacheProvider implements CacheProvider {

	@Inject
	@NamedCache("example-cache")
	private CacheApi cacheExample;

	@Override
	public CacheApi getCache() {
		return cacheExample;
	}

}
