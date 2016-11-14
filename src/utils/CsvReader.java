/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class need for reading CSV file
 *
 * @author Project1
 */
public class CsvReader {
	
	public static final String SEPARATOR = ",";
	
	private final File file;

	/**
	 * Private constructor
	 * 
	 * @param reader 
	 */
	public CsvReader(File file) {
		this.file = file;
	}
	
	/**
	 * Read only first row with CSV header
	 * 
	 * @return
	 * @throws IOException 
	 */
	public List<String> readHeader() throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			return reader.lines()
					.findFirst()
					.map(line -> Arrays.asList(line.split(SEPARATOR)))
					.get();
		}
	}

	/**
	 * Read data rows without CSV header
	 * 
	 * @return
	 * @throws IOException 
	 */
	public List<List<String>> readRecords() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            return reader.lines()
					.skip(1)
					.map(line -> Arrays.asList(line.split(SEPARATOR)))
					.collect(Collectors.toList());
		}
	}
	
}
