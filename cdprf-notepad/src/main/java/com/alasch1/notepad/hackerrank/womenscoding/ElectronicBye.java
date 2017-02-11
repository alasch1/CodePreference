package com.alasch1.notepad.hackerrank.womenscoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ElectronicBye {
	
	static ArrayList<Integer> validKeybords;
	static ArrayList<Integer> validPendrives;
	static int s;
	
	public static void main(String[] args) {
		if (getValidInput()) {
			findBestCombination();
		}
	}
	
	private static void findBestCombination() {
		Map<CombinedIndex, Integer> difs = new HashMap<>();
		int ki = 0, pi=0;
		for (Integer kPrice: validKeybords) {
			for (Integer pPrice: validPendrives) {
				int sum = kPrice + pPrice;
				difs.put(new CombinedIndex(ki,pi,sum), s - sum);
				pi++;
			};
			ki++;
		};
//		System.out.println(String.format("Combinations: keys=%s values=%s", 
//				Arrays.toString(difs.keySet().toArray()),
//				Arrays.toString(difs.values().toArray())));
		
		Optional<Map.Entry<CombinedIndex, Integer>> minDif = difs.entrySet().stream()
				.filter(e -> e.getValue() >= 0)
				.min(Map.Entry.comparingByValue(Integer::compareTo));
		
		if (minDif.isPresent()) {
			System.out.println(minDif.get().getKey().sum);
		}
		else {
			System.out.println(-1);
		}
	}
	
	private static boolean getValidInput() {
		
		try(Scanner in = new Scanner(System.in)) {
			s = in.nextInt();
			if (!isValidSum(s)) return false;
			
			int n = in.nextInt();
			if (!isValidLength(n)) return false;
			
			int m = in.nextInt();
			if (!isValidLength(m)) return false;
			
			int[] keyboards = new int[n];			
			for(int keyboards_i=0; keyboards_i < n; keyboards_i++){
				keyboards[keyboards_i] = in.nextInt();
			}
			int[] pendrives = new int[m];
			for(int pendrives_i=0; pendrives_i < m; pendrives_i++){
				pendrives[pendrives_i] = in.nextInt();
			}
			validKeybords = validPrices(keyboards);
			if (validKeybords==null) return false;
			
			validPendrives = validPrices(pendrives);
			if (validPendrives==null) return false;
			
			return true;
		}
			
	}
	
	private static boolean isValidSum(int s) {
		return s>=1 && s<=1000000;		   
	}
	
	private static boolean isValidLength(int l) {
		return l>=1 && l<=1000;		   
	}
	
	// Returns 0 in case of out of range price
	private static ArrayList<Integer> validPrices(int[] prices) {
		ArrayList<Integer> validPrices = new ArrayList<>();
		for (int i=0; i<prices.length; i++) {
			if (isValidSum(prices[i])) {
				validPrices.add(prices[i]);
			}
			else {
				return null;
			}
		}
		return validPrices;
	}

}

class CombinedIndex {
	int keybordIndex;
	int pendriveIndex;
	int sum;
	
	CombinedIndex(int k, int p, int s) {
		keybordIndex = k;
		pendriveIndex = p;
		sum = s;
	}
	
	@Override
	public String toString() {
		return "[ki=" + keybordIndex + ", pi=" + pendriveIndex + ", sum=" + sum
				+ "]";
	}
}
