package guiceExamples.cacheInspector;

import com.google.inject.ImplementedBy;

/**
 * Facade to cache access 
 * 
 * @author aschneider
 *
 */
@ImplementedBy(RuntimeCacheInspector.class)
public interface CacheInspector {
	Object getValue(String key);
	void removeEntry(String key);
	void restartExpiration	(String key);
}
