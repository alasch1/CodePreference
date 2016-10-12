package com.alasch1.codepreference.akkaexamples.primecalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Protocol {

	public static class Range implements Serializable {

		private static final long serialVersionUID = 3422447334696719787L;
		private final long fromNumber;
		private final long toNumber;
		
		public Range(long fromNumber, long toNumber) {
			super();
			this.fromNumber = fromNumber;
			this.toNumber = toNumber;
		}

		public long getFromNumber() {
			return fromNumber;
		}

		public long getToNumber() {
			return toNumber;
		}

		@Override
		public String toString() {
			return "RangeMessage [fromNumber=" + fromNumber + ", toNumber=" + toNumber + "]";
		}		
	}
	
	// Protocol
	public static class PrimeCalcResult implements Serializable {
		
		private static final long serialVersionUID = -7373343236142238581L;

		private List<Long> results = new ArrayList<>();
		
		public List<Long> getResults() {
			return results;
		}
		
		@Override
		public String toString() {
			return "" + results;
		}		
	}
	
}
