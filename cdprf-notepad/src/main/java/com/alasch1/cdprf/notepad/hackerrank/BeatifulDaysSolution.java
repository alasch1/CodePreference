package com.alasch1.cdprf.notepad.hackerrank;

import java.util.Scanner;

public class BeatifulDaysSolution {

	private static int i,j,k;
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			i = scan.nextInt();
			j = scan.nextInt();
			k = scan.nextInt();
			if (!validInput()) {
				return;
			}
			int countBeauty = 0;
			for (int x=i; x<=j; x++) {
				if (isBeautiful(x)) {
					countBeauty++;
				}
			}
			System.out.println(countBeauty);
		}
	}
	
	private static boolean validInput() {
		if (i>=1 && i<=j && j<= 2000000) {
			return (k>=1 && k<=2000000000);
		}
		return false;
	}
	
	private static boolean isBeautiful(int x) {
		StringBuilder sb = new StringBuilder(Integer.toString(x));
		int inverseX = Integer.parseInt(sb.reverse().toString());
		return (x-inverseX)%k == 0;
	}

}
