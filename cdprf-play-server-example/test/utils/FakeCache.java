package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import play.cache.CacheApi;

public class FakeCache implements CacheApi {
	private final Map<String,Object> data = new HashMap<>();

	@Override
	public <T> T get(String key) {
		return (T) data.get(key);
	}

	@Override
	public <T> T getOrElse(String key, Callable<T> callable, int expiration) {
		return getOrElse(key, callable);
	}

	@Override
	public <T> T getOrElse(String key, Callable<T> callable) {
		try {
			T value = get(key);
			if (value == null) {
				value = callable.call();
				set(key, value);
			}
			return value;
		} 
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public void set(String key, Object value, int expiration) {
		set(key, value);		
	}

	@Override
	public void set(String key, Object value) {
		data.put(key, value);		
	}

	@Override
	public void remove(String key) {
		data.remove(key);		
	}

}
