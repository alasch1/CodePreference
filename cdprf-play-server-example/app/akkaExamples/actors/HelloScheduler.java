package akkaExamples.actors;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akkaExamples.actors.HelloAnyone.Anyone;
import scala.concurrent.duration.Duration;

public class HelloScheduler extends AbstractActor {
	
	// Protocol
	public static final String CANCEL = "cancel";
	private static final String TICK = "tick";
	
	// The first polling in 30 sec after the start
	private static final int ON_START_POLL_INTERVAL = 30;
	private static final int TICK_INTERVAL_SEC = 90;
	
	private final ActorRef helloSayer;
	private final String anyoneName;
	private Cancellable scheduler;
	
	public static Props props(ActorRef helloSayer, String name) {
		return Props.create(HelloScheduler.class,
				()->new HelloScheduler(helloSayer, name));
	}
	
	public HelloScheduler(ActorRef helloSayer, String anyoneName) {
		this.helloSayer = helloSayer;
		this.anyoneName = anyoneName;
		receive(ReceiveBuilder
			.matchEquals(TICK, m -> onTick())
			.matchEquals(CANCEL, this::cancelTick)
			.matchAny(this::unhandled)
			.build());
	}
	
	@Override
	public void preStart() throws Exception {
		scheduler = getContext().system().scheduler().scheduleOnce(
				Duration.create(ON_START_POLL_INTERVAL, TimeUnit.SECONDS),
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
		scheduler = getContext().system().scheduler().scheduleOnce(
				Duration.create(TICK_INTERVAL_SEC, TimeUnit.SECONDS),
				self(),
				TICK,
				getContext().dispatcher(),
				null);
	}
	
	public void cancelTick(String string) {
		if (scheduler != null) {
			scheduler.cancel();
		}
	}
	
}
