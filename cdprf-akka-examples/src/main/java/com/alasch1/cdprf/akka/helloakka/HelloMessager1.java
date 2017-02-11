package com.alasch1.cdprf.akka.helloakka;

import com.alasch1.cdprf.akka.utils.AppUtil;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloMessager1 extends UntypedActor {
	// Protocol
	static class HelloRequest {			
	}
	
	private static final String HELLO = "Hello";
	private int counter;
	
	public static Props props() {
		return Props.create(HelloMessager1.class);
	}
	
	public HelloMessager1() {
	}
	
	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof HelloRequest) {
			sayHello((HelloRequest)message);
		}
		else {
			unhandled(message);
		}
		
	}
	
	private void sayHello(HelloRequest message) {		
		System.out.println(HELLO + " #" + (++counter));
	}
	
    public static void main( String[] args ) {
    	
    	ActorSystem system = ActorSystem.create("MySystem");
    	ActorRef messager = system.actorOf(HelloMessager1.props(), "HelloMessager");
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
