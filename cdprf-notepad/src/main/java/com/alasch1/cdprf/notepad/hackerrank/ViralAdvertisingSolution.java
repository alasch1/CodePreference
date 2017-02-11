package com.alasch1.cdprf.notepad.hackerrank;

import java.util.Scanner;

public class ViralAdvertisingSolution {
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int n = sc.nextInt();
			if (n<1 || n>50) {
				return;
			}
			
			int likes = 0, recieved = 5, newLikes=0;
			for (int day=1; day<=n; day++) {
				// new likes
				newLikes = Math.floorDiv(recieved,2);
				likes += newLikes;
				recieved = newLikes *3;
			}
			
			System.out.println(likes);			
		}

	}

}

