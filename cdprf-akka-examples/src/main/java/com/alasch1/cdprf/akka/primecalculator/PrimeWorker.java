package com.alasch1.cdprf.akka.primecalculator;

import com.alasch1.cdprf.akka.primecalculator.Protocol.PrimeCalcResult;
import com.alasch1.cdprf.akka.primecalculator.Protocol.Range;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class PrimeWorker extends AbstractLoggingActor {

	public static Props props() {
		return Props.create(PrimeWorker.class);
	}
	
	public PrimeWorker() {
		receive(ReceiveBuilder
			.match(Range.class, this::calculate)
			.matchAny(this::unhandled)
			.build());
	}
	
	private void calculate(Range message) {
		log().info("Received {}", message);
		PrimeCalcResult workerResult = new PrimeCalcResult();
		for (long number = message.getFromNumber();
				number <= message.getToNumber();
				number ++) {
			if (isPrime(number)) {
				workerResult.getResults().add(number);
			}
		}
		sender().tell(workerResult, self());
	}
	
	private boolean isPrime(long num) {
		if (num == 1 || num == 2 || num ==3) return true;
		
		if (num%2==0) return false;
		
		for( long i=3; i*i <= num; i+=2 ) {
            if( num % i == 0) {
                return false;
            }
        }
		return true;
	}
	
}
