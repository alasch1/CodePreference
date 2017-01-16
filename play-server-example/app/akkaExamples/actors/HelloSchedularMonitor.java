package akkaExamples.actors;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import play.inject.ApplicationLifecycle;

public class HelloSchedularMonitor {
	private ActorRef scheduler;
	private ActorSystem system;

	@Inject
	public HelloSchedularMonitor(ActorSystem system, ApplicationLifecycle lifeCycle) {
		this.system = system;
		initStopHook(lifeCycle);
	}
	public void startPolling() {
		scheduler = system.actorOf(HelloScheduler.props(
				system.actorOf(HelloAnyone.props()), "Nobody"));		
	}
	
	public void cancelTick() {
		if (scheduler != null) {
			scheduler.tell(HelloScheduler.CANCEL, null);
		}
	}
	
	private void initStopHook(ApplicationLifecycle lifeCycle) {
		lifeCycle.addStopHook(() -> {
			cancelTick();
			return CompletableFuture.completedFuture(null);
		});
	}
}
