/**
 * 
 */
package com.gilead.ems.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gilead.ems.model.Trainee;

/**
 * 
 */
public class CSVProcessor {

	public List<Trainee> getAllTrainees(String fileName) throws IOException, ParseException {
		final Logger logger = LogManager.getLogger(CSVProcessor.class);
		List<Trainee> trainees = new ArrayList<Trainee>();
		try (Stream<String> lines = Files.lines(Paths.get(fileName));
				BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			List<String[]> csvData = lines.map(line -> line.split(",")) // Split each line by comma
					.collect(Collectors.toList());

			for (String[] row : csvData) {
				Trainee trainee = new Trainee();
				Integer id = Integer.parseInt(row[0]);
				trainee.setId(id);
				trainee.setCompany(row[1]);
				trainee.setName(row[2]);
				SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
				Date dob = formatter.parse(row[3]);
				trainee.setDob(dob);
				trainee.setGender(row[4]);
				Date doj = formatter.parse(row[5]);
				trainee.setDoj(doj);
				trainee.setRole(row[6]);
				trainees.add(trainee);
			}

		}
		logger.info("No of Records to be inserted into Database : "+trainees.size());
		return trainees;

	}
}
