package com.alasch1.notepad.hackerrank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class CavitySolution {
	private static ArrayList<char[]> map = new ArrayList<>();
	private static int n = 0;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			n = sc.nextInt();
			if (!validDimension()) {
				return;
			}
			
			if (!readRows(sc)) {
				return;
			}
			
			for (int i=0; i<n; i++) {
				inspectRow(i);
			}
			
		}
	}
	
	// Returns false in case of invalid input
	private static boolean readRows(Scanner sc) {
		for (int i=0; i<n; i++) {
			BigInteger input = sc.nextBigInteger();
			if (!addValidRow(input)) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean validDimension() {
		return n >=1 && n <=100;
	}
	
	private static boolean addValidRow(BigInteger input) {
		String row = input.toString();
		if (row.length() == n) {
			map.add(row.toCharArray());
			return true;
		}
		else return false;
	}
	
	private static void inspectRow(int rowIndex) {
		char[] current =  map.get(rowIndex);
		if (rowIndex > 0 && rowIndex < n-1) {
			char[] prev = map.get(rowIndex-1);
			char[] next = map.get(rowIndex + 1);
			
			for (int i=1; i<n-1; i++) {
				if (current[i]>current[i-1] && //left
						current[i]>current[i+1] && //right
						current[i]>prev[i] && //top					
						current[i]>next[i] //bottom
						)
					current[i]='X';			
			}
		}
		System.out.println(new String(current));
	}

}
