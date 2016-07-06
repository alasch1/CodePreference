package guiceExamples.cacheInspector;

/**
 * Fake cache access for JUnit
 * 
 * @author aschneider
 *
 */
public class FakeCacheInspector implements CacheInspector {

	@Override
	public Object getValue(String key) {
		return new String("Fake cache facade");
	}

	@Override
	public void removeEntry(String key) {
	}

	@Override
	public void restartExpiration(String key) {
	}

}
