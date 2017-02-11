package guiceExamples.cache;

import play.cache.CacheApi;

import com.google.inject.ImplementedBy;

/**
 * Facade to cache access 
 * 
 * @author aschneider
 *
 */
@ImplementedBy(RuntimeCacheProvider.class)
public interface CacheProvider {
	CacheApi getCache();
}
