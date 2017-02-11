package com.alasch1.cdprf.notepad.hackerrank;

import java.util.Scanner;

public class PolindromSolution {

	public static void main(String[] args) {
		try (Scanner sc=new Scanner(System.in)) {
			String A = sc.next();
			StringBuffer sb = new StringBuffer(A);
			String inversA = sb.reverse().toString();
			if (!validInput(A)) return;
			
			if (A.equalsIgnoreCase(inversA)) {
				System.out.println("Yes");
			}
			else {
				System.out.println("No");
			}
		}
	}
	
	private static boolean validInput(String A) {
		return (A.length() <= 50 && A.equals(A.toLowerCase()));
	}

}
