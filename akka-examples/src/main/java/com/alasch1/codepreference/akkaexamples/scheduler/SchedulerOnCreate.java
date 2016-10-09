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
	
	public static Props props() {
		return Props.create(SchedulerOnCreate.class);
	}
	
	// Schedule the first tick from now to 
	private final Cancellable tick = getContext().system().scheduler().schedule(
			Duration.Zero(),
			Duration.create(100, TimeUnit.MILLISECONDS), 
			self(), 
			TICK,
			getContext().dispatcher(), 
			null);

	public SchedulerOnCreate() {
		receive(ReceiveBuilder
			.matchEquals(TICK, this::onTick)
			.matchAny(m -> log().warning("Unknown message"))
			.build());
	}
	
	private void onTick(String tick) {
		log().info("ticking ...");
	}

	@Override
	public void postStop() throws Exception {
		tick.cancel();
		log().info("Cancelled");
	}
	
    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create("mySystem");
    	ActorRef scheduler = system.actorOf(SchedulerOnCreate.props(), "scheduler");
        AppUtil.terminate(system, scheduler);
        
    }
}
