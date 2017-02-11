package com.alasch1.cdprf.notepad.hackerrank;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Scanner;

public class DayOfWeekSolution {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a month number:");
		String month = in.next();
		System.out.println("Enter a day of month:");
		String day = in.next();
		System.out.println("Enter a year in range 2000-3000 (exclusive):");
		String year = in.next();
		in.close();
		int yearNumber = extractYear(year);
		int monthNumber = extractMonth(month);
		int dayNumber = extractValidNumber(day);
		if (yearNumber != 0 && monthNumber != 0 && dayNumber !=0) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(yearNumber, monthNumber-1, dayNumber);
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			System.out.println("Day of week:" + getDayOfWeek(dayOfWeek));
		}
		else {
			System.out.println("Invalid data input");
		}
	}
	
	private static DayOfWeek getDayOfWeek(int calendarDayOfWeek) {
		DayOfWeek[] week = { 
				DayOfWeek.SUNDAY, 
				DayOfWeek.MONDAY,
				DayOfWeek.TUESDAY,
				DayOfWeek.WEDNESDAY,
				DayOfWeek.THURSDAY,
				DayOfWeek.FRIDAY,
				DayOfWeek.SATURDAY
		};
		
		return week[calendarDayOfWeek-1];
	}

	private static int extractMonth(String input) {
		int number = extractValidNumber(input);
		if (number < 1 || number > 12) {
			number = 0;
		}
		return number;
	}
	
	private static int extractYear(String input) {
		int number = extractValidNumber(input);
		// Year should be inside range of 2000-3000
		if (number <= 2000 || number >= 3000) {
			number = 0;
		}
		return number;
	}
	
	private static int extractValidNumber(String s) {
		try {
			return Integer.parseInt(s);
		}
		catch (Exception e) {
			return 0;
		}
	}
}
