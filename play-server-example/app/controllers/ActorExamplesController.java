package controllers;

import static akka.pattern.Patterns.ask;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akkaExamples.actors.HelloAnyone;
import akkaExamples.actors.HelloAnyone.Anyone;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;

public class ActorExamplesController extends Controller {
	
	private ActorRef helloActor;

	@Inject
	public ActorExamplesController(ActorSystem system) {
		helloActor = system.actorOf(HelloAnyone.props());
	}
	
	public CompletionStage<Result> sayHello(String name) {
		return FutureConverters.toJava(ask(helloActor, new Anyone(name), 1000))
				.thenApply(response-> ok((String)response));
	}
}
