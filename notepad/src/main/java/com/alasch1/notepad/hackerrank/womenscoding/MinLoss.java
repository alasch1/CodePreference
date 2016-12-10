package com.alasch1.notepad.hackerrank.womenscoding;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MinLoss {

	static int n;
	//static ArrayList<Long> prices = new ArrayList<>();
	static long prices[];
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
		//prices = new ArrayList<>(n);
		prices = new long[n];
		for(int i=0; i < n; i++) {
			//prices.add((long)(2*(n-i)-1));
			prices[i] = ((long)(2*(n-i)-1));
		}
		//		System.out.println("Input:" + prices);
		return true;
	}

	private static void findMinLoss() {
		int iBuy=0;
		long minLoss = -1;
		for (Long buyCandidate:prices) {
			for (int iSell=iBuy+1; iSell < n; iSell++) {
				//Long sellCandidate = prices.get(iSell);
				long sellCandidate = prices[iSell];
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
