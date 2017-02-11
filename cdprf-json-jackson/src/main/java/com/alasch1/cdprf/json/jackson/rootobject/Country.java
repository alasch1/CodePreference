package com.alasch1.cdprf.json.jackson.rootobject;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@JsonRootName("country")
public class Country {
	public int id;
	public String name;
	public Collection<String> cities;
	
	public Country() {
	}
	
	public Country(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Country [name=%s, cities=%s]", name, cities);
	}
	
	public static void main(String[] arg) throws JsonProcessingException {
		Country tested = new Country(55, "Neverland");	 
	    ObjectMapper mapper = new ObjectMapper()
	    		.configure(SerializationFeature.WRAP_ROOT_VALUE, true)
	    		.configure(SerializationFeature.INDENT_OUTPUT, true);
	    String json = mapper.writeValueAsString(tested);
		System.out.println("json:" + json);
	}
} 

