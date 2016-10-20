package akkaExamples.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akkaExamples.actors.HelloAnyone.Anyone;
import scala.concurrent.duration.Duration;

public class HelloScheduler extends AbstractActor {
	
	// Protocol
	private static final String TICK = "tick";
	
	private static final int TICK_INTERVAL_SEC = 90;
	
	public static Props props(ActorRef helloSayer, String name) {
		return Props.create(HelloScheduler.class,
				()->new HelloScheduler(helloSayer, name));
	}
	
	private final ActorRef helloSayer;
	private final String anyoneName;
	
	public HelloScheduler(ActorRef helloSayer, String anyoneName) {
		this.helloSayer = helloSayer;
		this.anyoneName = anyoneName;
		receive(ReceiveBuilder
			.matchEquals(TICK, m -> onTick())
			.matchAny(this::unhandled)
			.build());
	}
	
	@Override
	public void preStart() throws Exception {
		getContext().system().scheduler().scheduleOnce(
				Duration.create(TICK_INTERVAL_SEC, TimeUnit.SECONDS),
				self(),
				TICK,
				getContext().dispatcher(),
				null);
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		// No call to preStart
	}

	private void onTick() {
		helloSayer.tell(new Anyone(anyoneName), self());
		getContext().system().scheduler().scheduleOnce(
				Duration.create(TICK_INTERVAL_SEC, TimeUnit.SECONDS),
				self(),
				TICK,
				getContext().dispatcher(),
				null);
	}
}
