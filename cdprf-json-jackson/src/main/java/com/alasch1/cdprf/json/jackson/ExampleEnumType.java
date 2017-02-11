package com.alasch1.cdprf.json.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExampleEnumType {
	EnumA(1), 
	EnumB(2), 
	EnumC(3);
	
	private int code = 0;
	
	private ExampleEnumType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	@JsonCreator
	public static ExampleEnumType getEnumFromValue(String input) {
		return EnumDeserializer.getEnumFromValue(ExampleEnumType.class, input, (ExampleEnumType enumValue, int code) -> {			
			return enumValue.getCode() == code;		
		});
	}
	
	@Override
	@JsonValue
	public String toString() {
		return String.format("%d", code);
	}
}
