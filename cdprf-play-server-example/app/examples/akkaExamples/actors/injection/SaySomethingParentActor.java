package examples.akkaExamples.actors.injection;

import com.google.inject.Inject;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import play.libs.akka.InjectedActorSupport;

public class SaySomethingParentActor extends AbstractActor implements InjectedActorSupport {
	
	//Protocol
	public static class CreateChild {
		private final String something;
		private final String id;
		
		public CreateChild(String something, String id) {
			this.something = something;
			this.id  = id;
		}		
	}

	private SaySomethingActor.Factory childFactory;
	
	public static Props props(SaySomethingActor.Factory childFactory) {
		return Props.create(SaySomethingParentActor.class, childFactory);
	}
	
	@Inject
	public SaySomethingParentActor(SaySomethingActor.Factory childFactory) {
		this.childFactory = childFactory;
		receive(ReceiveBuilder
			.match(CreateChild.class, this::injectHelloActor)
			.matchAny(this::unhandled)
			.build());
	}
	
	private void injectHelloActor(CreateChild injectMsg) {
		ActorRef child =  injectedChild(() -> childFactory.create(injectMsg.something), "child-" +injectMsg.id);
		System.out.println("Injected " + "child-" +injectMsg.id);
		sender().tell(child, self());
	}
}
