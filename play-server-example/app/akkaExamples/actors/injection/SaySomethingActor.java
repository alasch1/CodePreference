package akkaExamples.actors.injection;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import akka.actor.Props;
import akka.actor.UntypedActor;

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

	private String something;
	
	public static Props props(String something) {
		return Props.create(SaySomethingActor.class, something);
	}
	
	@Inject
	public SaySomethingActor(@Assisted String something) {
		this.something = something;
	}
	
	private void sayHello(Someone someone) {
		String helloString = something + " " +someone.getName();
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
