package customization;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import akka.actor.ActorSystem;
import akkaExamples.actors.HelloAnyone;
import akkaExamples.actors.HelloScheduler;
import play.Configuration;
import play.libs.Json;

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
	public StartupHandler(final Configuration configuration, final ActorSystem system) {
		System.setProperty(LOG4J2_CONFIG_PROPERTY, configuration.getString(LOG4J2_CONFIG_PROPERTY));
		configureJson();
		startScheduledActor(system);
	}
	
	private void configureJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Json.setObjectMapper(mapper);
	}
	
	private void startScheduledActor(ActorSystem system) {
		system.actorOf(HelloScheduler.props(
				system.actorOf(HelloAnyone.props()), "Nobody"));
	}

}
