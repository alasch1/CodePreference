package com.alasch1.cdprf.csv.examples;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParserByEnum {

	private static final Logger LOG = LogManager.getLogger();
	public static class ParsedEntry {
		String strColumn;
		int intColumn;
		boolean boolColumn;
		List<String> strListColumn;
	}
	
	public enum Headers {
		STR_COLUMN_1, INT_COLUMN_2, BOOL_COLUMN_3, STR_LIST_COLUMN_4;
	}
	
	public static final char COLUMN_DELIM = ',';
	public static final String VALUE_DELIM = ";";
	private boolean parseStatusOk = true;
	
	public List<ParsedEntry> parse(File inputFile) {
		List<ParsedEntry> parsedData = new ArrayList<>();
		CSVFormat format = CSVFormat.EXCEL
				.withDelimiter(COLUMN_DELIM)
				.withHeader(Headers.class)
				.withIgnoreEmptyLines();
		Reader in;
		try {
			in = new FileReader(inputFile);
			
			Iterable<CSVRecord> records = format.parse(in);
			for (CSVRecord record : records) {
				LOG.trace("Parsing csv record: {}", record);
				if (record.getRecordNumber() == 1) {
					// Skip headers record
					continue;
				}
				ParsedEntry entry = extractData(record);
				if (entry !=null ) {
					parsedData.add(entry);
				}
				
			}
		} 
		catch (Exception e) {
			LOG.error("Exception while parsing", e);
			parseStatusOk = false;
		}
		
		if (parseStatusOk) {
			LOG.info("Extracted {} entries from the csv file {}", parsedData.size(), inputFile.getName());
			return parsedData;
		}
		else {
			LOG.error("Failed to read data from the csv file");
			return null;
		}
	}
	
	private ParsedEntry extractData(CSVRecord record) throws InstantiationException, IllegalAccessException {
		if (!record.isConsistent()) {
			LOG.error("Invalid record does not match the size of headers:{}", record);
			parseStatusOk = false;
			return null;
		}
		
		// Map record to POJO
		// The order is really matter.
		ParsedEntry parsedData = new ParsedEntry();
		parsedData.strColumn = record.get(Headers.STR_COLUMN_1);
		parsedData.intColumn = Integer.valueOf(record.get(Headers.INT_COLUMN_2));
		parsedData.boolColumn = Boolean.valueOf(record.get(Headers.BOOL_COLUMN_3));
		parsedData.strListColumn = parseListOfValues(record.get(Headers.STR_LIST_COLUMN_4));
		return parsedData;
	}	
	
	private List<String> parseListOfValues(String rawValue) {
		return Arrays.asList(rawValue.split(VALUE_DELIM));
	}
}
