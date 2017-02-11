package com.alasch1.cdprf.notepad.hackerrank;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class CurrencySolution {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double payment = scanner.nextDouble();
		scanner.close();
		
		if (payment<0 || payment > 10E9) {
			return;
		}

		// Write your code here.
		String us = NumberFormat.getCurrencyInstance(Locale.US).format(payment);
		String china = NumberFormat.getCurrencyInstance(Locale.CHINA).format(payment);
		String france = NumberFormat.getCurrencyInstance(Locale.FRANCE).format(payment);
		Locale indiaLocale = new Locale("en", "IN");
		String india = NumberFormat.getCurrencyInstance(indiaLocale).format(payment);
		System.out.println("US: " + us);
		System.out.println("India: " + india);
		System.out.println("China: " + china);
		System.out.println("France: " + france);
	}

}
