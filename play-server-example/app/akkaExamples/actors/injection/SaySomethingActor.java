package akkaExamples.actors.injection;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

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

	// Factory method for explicit creation
	public static Props props(String something, Configuration configuration) {
		return Props.create(SaySomethingActor.class, something, configuration);
	}
	
	private static final String SIGNATURE_PROPERTY = "signature";
	private String something;	
	private final Configuration configuration;
	
	@Inject
	public SaySomethingActor(@Assisted String something, Configuration configuration) {
		this.something = something;
		this.configuration = configuration;
	}
	
	private void sayHello(Someone someone) {
		String helloString = something + " " + someone.getName() + ", signature:" + configuration.getString(SIGNATURE_PROPERTY);
		System.out.println(helloString);
		sender().tell(helloString, self());
	}
	
	public interface Factory {
		SaySomethingActor create(String something);
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
