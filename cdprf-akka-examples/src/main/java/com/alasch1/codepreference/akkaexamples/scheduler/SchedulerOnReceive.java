package com.alasch1.codepreference.akkaexamples.scheduler;

import java.util.concurrent.TimeUnit;

import com.alasch1.codepreference.akkaexamples.utils.AppUtil;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

public class SchedulerOnReceive extends AbstractLoggingActor {

	// Protocol
	private static final String TICK = "tick";
	
	private static final String ACTOR_SYSTEM = "MySystem";
	private static final String ACTOR = "scheduler";
	
	private long initialDelayMillis = 0L;
	private long intervalSecs = 0L;
	
	public static Props props(long initialDelayMillis, long intervalSecs) {
		return Props.create(SchedulerOnReceive.class, () -> new SchedulerOnReceive(initialDelayMillis, intervalSecs));
	}
	
	public SchedulerOnReceive(long initialDelayMillis, long intervalSecs) {
		this.initialDelayMillis = initialDelayMillis;
		this.intervalSecs = intervalSecs;
		receive(ReceiveBuilder
			.matchEquals(TICK, this::onTick)
			.matchAny(m -> unhandled(m))
			.build());
	}
	
	@Override
	public void preStart() throws Exception {
		log().info("Scheduling the first tick..");
		getContext().system().scheduler().scheduleOnce(
			Duration.create(initialDelayMillis, TimeUnit.MILLISECONDS),
			self(), 
			TICK,
			getContext().dispatcher(),
			null);
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		// No call to preStart
	}

	private void onTick(String tick) {
		log().info("Scheduling next tick..");
		getContext().system().scheduler().scheduleOnce(
			Duration.create(intervalSecs, TimeUnit.SECONDS),
			self(), 
			TICK,
			getContext().dispatcher(),
			null);
	}
	
    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create(ACTOR_SYSTEM);
    	ActorRef scheduler = system.actorOf(SchedulerOnReceive.props(500, 10), ACTOR);
        AppUtil.terminate(system, scheduler);
        
    }

}
