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
	
	public static Props props() {
		return Props.create(SchedulerOnReceive.class);
	}
	
	public SchedulerOnReceive() {
		receive(ReceiveBuilder
			.matchEquals(TICK, this::onTick)
			.matchAny(o -> log().warning("Uknown message"))
			.build());
	}
	
//	@Override
//	public PartialFunction<Object, BoxedUnit> receive() {
//		return (ReceiveBuilder
//				.matchEquals(TICK, this::onTick)
//				.matchAny(o -> log().warning("Uknown message"))
//				.build());
//	}

	@Override
	public void preStart() throws Exception {
		log().info("Scheduling the first tick..");
		getContext().system().scheduler().scheduleOnce(
			Duration.create(100, TimeUnit.MILLISECONDS),
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
			Duration.create(1, TimeUnit.SECONDS),
			self(), 
			TICK,
			getContext().dispatcher(),
			null);
	}
	
    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create("mySystem");
    	ActorRef scheduler = system.actorOf(SchedulerOnReceive.props(), "schedulerByOne");
        AppUtil.terminate(system, scheduler);
        
    }

}
