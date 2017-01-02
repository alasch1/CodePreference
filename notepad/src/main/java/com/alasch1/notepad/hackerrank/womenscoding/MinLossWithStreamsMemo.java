package com.alasch1.notepad.hackerrank.womenscoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MinLossWithStreamsMemo {

	static int n;
	static ArrayList<Long> prices = new ArrayList<>();

	public static void main(String[] args) {
		if (getValidInput()) {
			findMinLoss();
		}
	}

	private static boolean getValidInput() {
//		try(Scanner in = new Scanner(System.in)) {
//
//			n = in.nextInt();
//			if (n<2 || n> Math.pow(10,5)) return false;
//
//			prices = new ArrayList<>(n);
//			for(int i=0; i < n; i++){
//				long p = in.nextLong();
//				if (p <1 || p > Math.pow(10,15)) return false;
//				else prices.add(p);
//			}			
//		}
		n=100000;
		prices = new ArrayList<>(n);
		for(int i=0; i < n; i++) {
			prices.add((long)(2*(n-i)-1));
		}
//		System.out.println("Input:" + prices);
		return true;
	}
	private static void findMinLossSS() {
		OptionalLong minLoss = IntStream
		.range(0, n-1).parallel()
		.mapToLong(i-> {
			//System.out.println("Mapping i="+i);
			return buyerMinLoss(i);
		})
		.filter(loss-> loss > 0L)
		.distinct()
		//.peek(l->System.out.println("filtered loss:" + l))
		.min();
		System.out.println(minLoss.orElse(0L));
	}
	
	private static Long buyerMinLoss(int buyerIndex) {
		Long buyCandidate = prices.get(buyerIndex);
		//System.out.println("buyCandidate:" + buyCandidate);
		Long loss = getMinLoss(buyCandidate, buyerIndex +1);
		if ( loss != null) {
			return loss;
		}
		else return 0L;
	}
	
	private static Long getMinLoss(long buyCandidate, int fromIndex) {
		//System.out.println(String.format("Checking sells for:buy=%s,fromIndex=%s",buyCandidate,fromIndex));
		Optional<Long> minLoss = prices.subList(fromIndex, n).parallelStream()
		.filter(sellCandidate -> buyCandidate > sellCandidate)
		.distinct()
		.map(sellCandidate -> buyCandidate-sellCandidate)
		//.peek(mapped -> System.out.println("mapped:" + mapped))
		.min(Comparator.comparing(Long::longValue));
		//if (minLoss.isPresent()) System.out.println("Min loss from " +fromIndex +":"+ minLoss.get());
		return minLoss.orElse(0L);
	}

private static void findMinLoss() {
	int iBuy=0;
	long minLoss = -1;
	for (Long buyCandidate:prices) {
		for (int iSell=iBuy+1; iSell < prices.size() -1; iSell++) {
			Long sellCandidate = prices.get(iSell);
			if (buyCandidate > sellCandidate) {
				long loss = buyCandidate - sellCandidate;
				if ( loss < minLoss || minLoss == -1) {
					minLoss = loss;
				}
				// Optimization: loss cannot be less than 1
				if (minLoss==1) break;

			}
		}
		iBuy++;
	}
	System.out.println(minLoss);

}
}
//class Combination {
//	int indexBuy;
//	int indexSell;
//
//	Combination(int ib, int is) {
//		indexBuy = ib;
//		indexSell = is;
//	}
//	@Override
//	public String toString() {
//		return "[indexBuy=" + indexBuy + ", indexSell=" + indexSell + "]";
//	}
//}
