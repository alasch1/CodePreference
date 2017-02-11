package com.alasch1.cdprf.notepad.hackerrank;

import java.util.Scanner;

public class StaticBlockSolution {
	private static int B, H;
	private static boolean flag = false;
	private static final String errorMsg = "java.lang.Exception: Breadth and height must be positive";
	static {
	    Scanner in = new Scanner(System.in);
	    System.out.println("Enter value of B1:");
	    B = in.nextInt();
	    System.out.println("Enter value of H:");
	    H = in.nextInt();
	    in.close();
	    if (H>0 && B>0) {
	    	flag = true;
	    }	    
	}
	
	public static void main(String[] args) {
		if(flag){
			int area=B*H;
			System.out.print(area);
		}
		else {
			System.out.println(errorMsg);
		}
	}

}
