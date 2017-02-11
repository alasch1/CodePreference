package com.alasch1.codepreference.akkaexamples.scheduler;

import java.util.concurrent.TimeUnit;

import com.alasch1.codepreference.akkaexamples.utils.AppUtil;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

public class SchedulerOnCreate extends AbstractLoggingActor {
	
	// Protocol
	private static final String TICK = "tick";
	private static final String START = "start";
	
	private static final String ACTOR_SYSTEM = "MySystem";
	private static final String ACTOR = "scheduler";
	
	private Cancellable tick;
	private long intervalSecs;
	
	public static Props props(long intervalSecs) {
		return Props.create(SchedulerOnCreate.class, () -> new SchedulerOnCreate(intervalSecs));
	}
	
	public SchedulerOnCreate(long intervalSecs) {
		this.intervalSecs = intervalSecs;
		receive(ReceiveBuilder
			.matchEquals(TICK, this::onTick)
			.matchEquals(START, m -> schedule())
			.matchAny(m -> unhandled(m))
			.build());
	}
	
	private void schedule() {
    	tick = getContext().system().scheduler().schedule(
    			Duration.Zero(), 
    			Duration.create(intervalSecs, TimeUnit.SECONDS),
    			self(), 
    			TICK,
    			getContext().dispatcher(), 
    			null);
	}	
	
	@Override
	public void postStop() throws Exception {
		tick.cancel();
		super.postStop();
	}

	private void onTick(String tick) {
		log().info("ticking ...");
	}

    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create(ACTOR_SYSTEM);
    	ActorRef actor = system.actorOf(SchedulerOnCreate.props(10), ACTOR);
    	actor.tell(START, ActorRef.noSender());
        AppUtil.terminate(system, actor);
        
    }
}
