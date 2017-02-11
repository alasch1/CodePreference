package com.alasch1.cdprf.json.jackson;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import com.alasch1.cdprf.json.masking.BodyPrinter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encapsulates JSON-POJO conversion 
  * 
 * @author aschneider
 *
 */
public final class CustomObjectMapper {
	
	private static final Logger LOG = LogManager.getLogger(CustomObjectMapper.class);	
	/**
	 * Converts a given Json string to the object of a class T
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString,  Class<T> clazz) throws Exception {
		if (Strings.isNotBlank(jsonString)) {
			LOG.trace("Extracting from json {}", BodyPrinter.body4print(jsonString));
			ObjectMapper mapper = getObjectMapper();
			T object = mapper.readValue(jsonString, clazz);
			LOG.trace("Extracted  object:{}",  object);
			return object;
		}
		else {
			LOG.warn("Empty json was received");
			return null;
		}
	}
	
	public static <T> List<T> listFromJson(String jsonString, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		if (Strings.isNotBlank(jsonString)) {
			LOG.debug("Extracting from json {}", BodyPrinter.body4print(jsonString));
			ObjectMapper mapper = getObjectMapper();
			List<T> list = mapper.readValue(jsonString, 
				    		mapper.getTypeFactory().constructCollectionType(List.class, clazz));
			LOG.debug("Extracted list size:{}", list.size());
			return list;
			}
		else {
			LOG.warn("Empty json was recieved");
			return null;
		}
	}
	
	/**
	 * Converts an object of type T into Json string
	 * @param data
	 * @return
	 */
	public static <T>String toJson(T data) throws Exception {
		LOG.trace("Converting to json:{}", data);
		ObjectMapper mapper = getObjectMapper();
		String json = mapper.writeValueAsString(data);
		LOG.trace("Created json: {}", json);
		return json;
	}
	
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// Properties names in Json starts from the capital letter 
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		// DeserializationFeature for changing how JSON is read as POJOs:
		// Enums are codes with number, differs from the ordered value  
		mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		return mapper;
	}
	
	private CustomObjectMapper() {
	}

}
