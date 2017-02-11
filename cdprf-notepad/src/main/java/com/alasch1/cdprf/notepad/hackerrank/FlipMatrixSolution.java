package com.alasch1.cdprf.notepad.hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlipMatrixSolution {

	public static void main(String[] args) {
		FlipMatrixSolution instance = new FlipMatrixSolution();
		instance.run();
	}
	
	private int q;
	private int n;
	private Integer [][] matrix;
	
	void run() {
		// read input
		// columns view
		// flip columns
		// rebuild matrix
		// row view
		// flip rows
		// rebuild matrix
		// calculate sum
	}
	
	ArrayList<Flipable> asColumns() {
		ArrayList<Flipable> columns = new ArrayList<>();
		for (int i=0; i<2*n; i++) {
			Flipable c = new Flipable();
			for (int j=0; j<2*n; j++) {
				c.append(matrix[i][j]);
			}
			columns.add(c);
		}
		return columns;
	}
	
	ArrayList<Flipable> asRaws() {
		ArrayList<Flipable> raws = new ArrayList<>();
		for (int i=0; i<2*n; i++) {
			Flipable r = new Flipable();
			for (int j=0; j<2*n; j++) {
				r.append(matrix[j][i]);
			}
			raws.add(r);
		}
		return raws;
	}

}

class Flipable {
	ArrayList<Integer> values = new ArrayList<>();
	int half = values.size()/2;
	
	void append(int value) {
		values.add(value);
	}
	int countHead() {
		return values.stream()
		.limit(half)
		.mapToInt(i -> i.intValue()).sum();
	}
	
	int countTail() {
		return values.stream()
				.skip(half)
				.mapToInt(i -> i.intValue()).sum();
	}
	
	boolean needsFlip() {
		return countHead() < countTail();
	}
	
	void flip() {
		Collections.reverse(values);
	}
}
