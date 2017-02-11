package com.alasch1.codepreference.akkaexamples.primecalculator;

import com.alasch1.codepreference.akkaexamples.primecalculator.Protocol.PrimeCalcResult;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class PrimeListener extends AbstractLoggingActor {

	public static Props props() {
		return Props.create(PrimeListener.class);
	}
	
	public PrimeListener() {
		receive(ReceiveBuilder
			.match(PrimeCalcResult.class, this::showResultsAndTerminate)
			.matchAny(this::unhandled)
			.build());
	}
	
	private void showResultsAndTerminate(PrimeCalcResult results) {
		log().info("Caclulated numbers:{}", results);
		getContext().system().terminate();
	}
}
