package controllers;

import static akka.pattern.Patterns.ask;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.google.inject.name.Named;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akkaExamples.actors.HelloAnyone;
import akkaExamples.actors.HelloAnyone.Anyone;
import akkaExamples.actors.injection.SaySomethingActor;
import akkaExamples.actors.injection.SaySomethingParentActor;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;

public class ActorExamplesController extends Controller {
	
	private ActorRef helloActorInjected;
	private ActorRef helloActorCreated;
	private ActorRef parentActor;

	@Inject
	public ActorExamplesController(ActorSystem system, @Named("parentActor") ActorRef parentActor, 
			@Named("helloAnyoneActor") ActorRef helloActor) {
		this.helloActorInjected = helloActor;
		this.helloActorCreated = system.actorOf(HelloAnyone.props());
		this.parentActor = parentActor;
	}
	
	public CompletionStage<Result> sayHello(String name) {
		return FutureConverters.toJava(ask(helloActorInjected, new Anyone(name), 1000))
				.thenApply(response-> ok((String)response));
	}
	
	public CompletionStage<Result> saySomething(String name, String something) {
		CompletionStage<ActorRef> childActor = createChildActor(something);
		return childActor.thenComposeAsync(actorRef -> sayHelloByRef(actorRef, name));
	}
	
	private CompletionStage<ActorRef> createChildActor(String param) {
        // Use guice assisted injection to instantiate and configure the child actor.
        long timeoutMillis = 100L;
        return FutureConverters.toJava(
                ask(parentActor, new SaySomethingParentActor.CreateChild(param, String.valueOf(System.currentTimeMillis())), timeoutMillis)
        ).thenApply(response -> (ActorRef) response);
	}
	
	private CompletionStage<Result> sayHelloByRef(ActorRef actorRef, String name) {
		return FutureConverters.toJava(ask(actorRef, new SaySomethingActor.Someone(name), 1000))
				.thenApply(response-> ok((String)response));
	}
	
}
