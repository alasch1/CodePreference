package com.alasch1.notepad.hackerrank.amazontests;

import java.util.Scanner;

public class StairCase {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		scanner.close();
		
		if (N<1 || N>100) return;
		final String empty = "";
		final String space = " ";
		final String stairs = "#";
		for (int i=0; i < N; i++) {
			String shift=empty;
			String values=empty;
			for (int s=N-i-2; s>=0; s--) {
				shift += space;				
			}
			for (int n=i+1; n>0; n--) {
				values += stairs;
			}
			System.out.println(String.format("%s%s", shift, values));
		}
	}

}
