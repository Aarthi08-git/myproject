package com.gilead.ems.processor;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gilead.ems.connection.DBConnection;
import com.gilead.ems.csv.CSVProcessor;
import com.gilead.ems.dao.TraineeDao;
import com.gilead.ems.model.Trainee;

/**
 * Hello world!
 *
 */
public class Application {
	private static final Logger logger = LogManager.getLogger(Application.class);  
	public static void main(String[] args) {
		BasicConfigurator.configure();
		String fileName ="D:\\Gilead_projects\\ems-standalone\\src\\main\\java\\com\\gilead\\ems\\processor\\Gilead_Employee_Details.csv";
		CSVProcessor csvProcessor = new CSVProcessor();
		List<Trainee> trainees = new ArrayList<Trainee>();
		try {
			//will create a list of trainees from CSV file
			trainees = csvProcessor.getAllTrainees(fileName);
			Connection connection = DBConnection.getConnection("jdbc:mysql://localhost:3306/ems", "root", "Letmein@123");
			TraineeDao traineeDao = new TraineeDao();			
			for (Trainee trainee : trainees) {
				logger.info("Trainee ID " + traineeDao.saveToDB(connection, trainee)+ " inserted into DB successfully");
			}
			
			List<Trainee> traineesFromDb = traineeDao.readFromDB(connection);
			for(Trainee traineeFromDB : traineesFromDb) {
				logger.info("Trainee ID from DB : "+traineeFromDB.getName());
			}
			
		} catch (IOException | ParseException e) {			
			logger.error("Common Exception occurred " + e.toString());
		}
		
		} 
	}

