package com.alasch1.codepreference.akkaexamples.helloakka;

import com.alasch1.codepreference.akkaexamples.utils.AppUtil;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class HelloMessager2 extends AbstractLoggingActor {
	// Protocol
	static class HelloRequest {			
	}
	
	private static final String HELLO = "Hello";
	private int counter;
	
	public static Props props() {
		return Props.create(HelloMessager2.class);
	}
	
	public HelloMessager2() {
		receive(ReceiveBuilder
			.match(HelloRequest.class, this::sayHello)
			.matchAny(this::unhandled)
			.build());
	}
	
	private void sayHello(HelloRequest message) {		
		System.out.println(HELLO + " #" + (++counter));
		log().info("Done");
	}
	
    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create("MySystem");
    	ActorRef messager = system.actorOf(HelloMessager2.props(), "HelloMessager");
    	for (int i=0; i<5; i++) {
    		new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j<5; j++) {					
						messager.tell(new HelloRequest(), ActorRef.noSender());
					}
				}    			
    		}).start();
    	}
        AppUtil.terminate(system, messager);
    }
}
