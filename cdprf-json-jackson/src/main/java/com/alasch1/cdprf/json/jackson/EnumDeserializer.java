package com.alasch1.cdprf.json.jackson;

/**
 * Generic implementation of de-serializing from integer to enum  of type T
 * 
 * @author aschneider
 *
 * @param <T>
 */

public final class EnumDeserializer<T extends Enum<T>> {

	public static <T>  T getEnumFromValue(Class<T> clazz, String input, EnumComparator<T> comparator) {
		int code = Integer.parseInt(input);
		for (T enumValue : clazz.getEnumConstants()) {
			if ( comparator.isEqual(enumValue, code)) {
				return enumValue;
			}
		}
		throw new IllegalArgumentException("Unrecognized value: " + input);
	}
	
	@FunctionalInterface
	public interface EnumComparator<T>  {
		boolean isEqual(T value, int code);
	}
	
	private EnumDeserializer() {}
	
}
