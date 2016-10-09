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
	
	private static final String ACTOR_SYSTEM = "MySystem";
	private static final String ACTOR = "scheduler";
	
	public static Props props() {
		return Props.create(SchedulerOnCreate.class, () -> new SchedulerOnCreate());
	}
	
	public SchedulerOnCreate() {
		receive(ReceiveBuilder
			.matchEquals(TICK, this::onTick)
			.matchAny(m -> unhandled(m))
			.build());
	}
	
	private void onTick(String tick) {
		log().info("ticking ...");
	}

    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create(ACTOR_SYSTEM);
    	ActorRef scheduler = system.actorOf(SchedulerOnCreate.props(), ACTOR);
    	
    	// If a tick is a data member, interval is ignored
    	Cancellable tick = system.scheduler().schedule(
    			Duration.create(10, TimeUnit.SECONDS), 
    			Duration.create(10, TimeUnit.SECONDS),
    			scheduler, 
    			TICK,
    			system.dispatcher(), 
    			null);

        AppUtil.terminate(system, scheduler);
        tick.cancel();
        
    }
}
