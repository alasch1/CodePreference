package examples.akkaExamples.actors;

import com.google.inject.Inject;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class HelloAnyone extends AbstractActor {
	
	//Protocol
	public static class Anyone {
		private final String name;
		public Anyone(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	private static final String HELLO = "Hello";
	
	public static Props props() {
		return Props.create(HelloAnyone.class);
	}
	
	@Inject
	public HelloAnyone() {
		receive(ReceiveBuilder
			.match(Anyone.class, this::sayHello)
			.matchAny(this::unhandled)
			.build());
	}
	
	private void sayHello(Anyone anyone) {
		String helloString = String.format("%s %s", HELLO,anyone.getName());
		System.out.println(helloString);
		sender().tell(helloString, self());
	}
}
