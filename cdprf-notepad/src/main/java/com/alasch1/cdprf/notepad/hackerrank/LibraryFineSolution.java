package com.alasch1.cdprf.notepad.hackerrank;

import java.util.Scanner;

public class LibraryFineSolution {

	private static LibraryDate 
			expectedDate = new LibraryDate(), 
			actualDate = new LibraryDate();
	private static int YEAR_FINE = 10000;
	private static int MONTH_FINE = 500;
	private static int DAY_FINE = 15;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			readDate(actualDate, sc);
			readDate(expectedDate, sc);
			if (!expectedDate.isValid() || !actualDate.isValid() ) {
				return; 
			}
			int fine = 0;
			if (actualDate.isLaterThan(expectedDate)) {
				LibraryDate dif = expectedDate.diff(actualDate);
				if (dif.year != 0) {
					// Fixed penalty for different year
					fine = YEAR_FINE;
				}
				else if (dif.month != 0) {
					fine = dif.month * MONTH_FINE;
				}
				else {
					fine = dif.day * DAY_FINE;
				}
			}
			System.out.println(fine);

		}
	}

	private static void readDate(LibraryDate date, Scanner sc) {
		date.day = sc.nextInt();
		date.month = sc.nextInt();
		date.year = sc.nextInt();
	}

}

class LibraryDate {
	int day, month,year;

	boolean isValid() {
		return isInScopeInclusive(1,31,day) &&
				isInScopeInclusive(1,  12,  month) &&
				isInScopeInclusive(1, 3000, year);
	}

	boolean isLaterThan(LibraryDate other) {
		if ((year > other.year) ||
				(year == other.year && month > other.month) ||
				(year == other.year && month == other.month && day > other.day)) {
			return true;
		}
		else return false;
	}
	
	LibraryDate diff(LibraryDate other) {
		LibraryDate difValues = new LibraryDate();
		difValues.year = other.year - year;
		difValues.month = other.month - month;
		difValues.day = other.day - day;
		return difValues;
	}

	private boolean isInScopeInclusive(int from, int to, int value) {
		return value >= from && value <= to;
	}

}
