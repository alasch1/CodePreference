package com.alasch1.notepad.hackerrank;

import java.util.Scanner;

public class StringsIntroSolution {

	public static void main(String[] args) {
		try (Scanner sc=new Scanner(System.in)) {
	        String A=sc.next();
	        String B=sc.next();
	        System.out.println("" + (A.length() + B.length()));
	        System.out.println(A.compareToIgnoreCase(B) < 0? "Yes": "No");
	        System.out.println(capitalize(A) + " " + capitalize(B));
		}
	}
	
	private static String capitalize(String input) {
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;
	}
}
