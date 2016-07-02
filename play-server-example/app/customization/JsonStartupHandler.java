package customization;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class JsonStartupHandler {

	@Inject
	public JsonStartupHandler() {
		configureJson();
	}
	
	private void configureJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}	

}
