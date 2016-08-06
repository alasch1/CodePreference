package customization;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.Configuration;
import play.libs.Json;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements customization to be done on an application startup
 * 
 * @author aschneider
 *
 */
@Singleton
public class StartupHandler {

	private static final String LOG4J2_CONFIG_PROPERTY = "log4j.configurationFile";
	
	@Inject
	public StartupHandler(final Configuration configuration) {
		System.setProperty(LOG4J2_CONFIG_PROPERTY, configuration.getString(LOG4J2_CONFIG_PROPERTY));
		configureJson();
	}
	
	private void configureJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Json.setObjectMapper(mapper);
	}	

}
