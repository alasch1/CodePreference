package com.alasch1.cdprf.akka.primecalculator;

import com.alasch1.cdprf.akka.primecalculator.Protocol.PrimeCalcResult;
import com.alasch1.cdprf.akka.primecalculator.Protocol.Range;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

public class PrimeCalculator extends AbstractLoggingActor {
	
	private PrimeCalcResult accumulatedResult = new PrimeCalcResult();
	private int workersNumber;
	private int workersResponsesNumber;
	private ActorRef workersRouter;
	private ActorRef resultListener;
	
	// Creation
	public static Props props(int workersNumber, ActorRef resultsListener) {
		return Props.create(PrimeCalculator.class, 
				() -> new PrimeCalculator(workersNumber, resultsListener));
	}
	
	public PrimeCalculator(int workersNumber, ActorRef resultsListener) {
		this.workersNumber = workersNumber;
		this.resultListener = resultsListener;
		receive(ReceiveBuilder
			.match(Range.class, this::redirectToWorkers)
			.match(PrimeCalcResult.class, this::accumulateResults)
			.build());
		workersRouter = getContext()
				.actorOf(new RoundRobinPool(workersNumber)
					.props(PrimeWorker.props()));
	}
	
	private void redirectToWorkers(Range range) {
		log().info("Start calculation for {}", range);
		long chunkSize = (range.getToNumber() - range.getFromNumber())/workersNumber;
		for (int i=0; i<workersNumber; i++) {
			long from = range.getFromNumber() + chunkSize * i;
			long to = from + chunkSize -1;
			if (i == workersNumber -1) {
				to = range.getToNumber();
			}
			workersRouter.tell(new Range(from, to), self());
		}
	}
	
	private void accumulateResults(PrimeCalcResult workerResults) {
		accumulatedResult.getResults().addAll(workerResults.getResults());
		if (++workersResponsesNumber == workersNumber) {
			log().info("All workers responses arrived, stop now");
			resultListener.tell(accumulatedResult, self());
			getContext().stop(self());
		}
	}
	
	private static final String PRIME_CALC = "PrimeCalculator";
	private static final String PRIME_LISTENER = "PrimeListener";
	private static final String PRIME_SYSTEM = "PrimeCalculatorSystem";

    public static void main( String[] args ) {
		if (args.length < 3) {
			System.out.println( "Usage: java com.alasch1.codepreference.akkaexamples.primecalculator <from> <to> <workers number>" );
			System.exit( 0 );
		}
		
		int argIndex = 0;
		long from = Long.parseLong(args[argIndex]);
		long to = Long.parseLong(args[++argIndex]);
		int workersNumber = Integer.parseInt(args[++argIndex]);
		
		ActorSystem system = ActorSystem.create(PRIME_SYSTEM);
		final ActorRef listener = system.actorOf(
				PrimeListener.props(), PRIME_LISTENER);
		final ActorRef calculator = system.actorOf(
				PrimeCalculator.props(workersNumber, listener), PRIME_CALC);
		calculator.tell(new Range(from, to), ActorRef.noSender());		
	}
	
}
