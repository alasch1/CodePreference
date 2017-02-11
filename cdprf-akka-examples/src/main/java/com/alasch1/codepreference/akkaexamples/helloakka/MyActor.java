package com.alasch1.codepreference.akkaexamples.helloakka;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class MyActor extends AbstractActor {
     static class Message1 {}
     static class Message2 {}
     
     MyActor() {
    	 receive(ReceiveBuilder
    			 .match(Message1.class, m->System.out.println("Message1"))
    			 .match(Message2.class, m->System.out.println("Message2"))
    			 .matchAny(m->unhandled(m))
    			 .build());
     }

	@Override
	public PartialFunction<Object, BoxedUnit> receive() {
		return ReceiveBuilder.match(Message1.class, m->System.out.println("Message1"))
			 .match(Message2.class, m->System.out.println("Message2"))
			 .matchAny(m->unhandled(m))
			 .build();
	}
     
     
}
