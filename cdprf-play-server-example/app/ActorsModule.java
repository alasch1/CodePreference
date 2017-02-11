import com.google.inject.AbstractModule;

import akkaExamples.actors.injection.SaySomethingParentActor;
import akkaExamples.actors.HelloAnyone;
import akkaExamples.actors.injection.SaySomethingActor;
import play.libs.akka.AkkaGuiceSupport;

public class ActorsModule extends AbstractModule implements AkkaGuiceSupport {

	@Override
	protected void configure() {
		bindActor(HelloAnyone.class, "helloAnyoneActor");
		bindActor(SaySomethingParentActor.class, "parentActor");
		bindActorFactory(SaySomethingActor.class, SaySomethingActor.Factory.class);
	}

}
