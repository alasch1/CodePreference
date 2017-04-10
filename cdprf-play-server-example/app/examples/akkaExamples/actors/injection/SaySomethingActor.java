package examples.akkaExamples.actors.injection;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActor;
import play.Configuration;

public class SaySomethingActor extends UntypedActor {
	
	//Protocol
	public static class Someone {
		private final String name;
		public Someone(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	// The factory method for injection
	public interface Factory {
		Actor create(String something);
	}
	
	// The factory method for explicit creation
	public static Props props(Configuration configuration, String something) {
		return Props.create(SaySomethingActor.class, configuration, something);
	}
	
	private static final String SIGNATURE_PROPERTY = "signature";
	private String something;	
	private final Configuration configuration;
	
	@Inject
	public SaySomethingActor(Configuration configuration, @Assisted String something) {
		this.something = something;
		this.configuration = configuration;
	}
	
	private void sayHello(Someone someone) {
		String helloString = something + " " + someone.getName() + ", signature:" + configuration.getString(SIGNATURE_PROPERTY);
		System.out.println(helloString);
		sender().tell(helloString, self());
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Someone) {
			sayHello((Someone)msg);
		}		
		else {
			unhandled(msg);
		}
	}
}
