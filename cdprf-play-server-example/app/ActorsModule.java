import com.google.inject.AbstractModule;

import examples.akkaExamples.actors.HelloAnyone;
import examples.akkaExamples.actors.injection.SaySomethingActor;
import examples.akkaExamples.actors.injection.SaySomethingParentActor;
import play.libs.akka.AkkaGuiceSupport;

public class ActorsModule extends AbstractModule implements AkkaGuiceSupport {

	@Override
	protected void configure() {
		bindActor(HelloAnyone.class, "helloAnyoneActor");
		bindActor(SaySomethingParentActor.class, "parentActor");
		bindActorFactory(SaySomethingActor.class, SaySomethingActor.Factory.class);
	}

}
