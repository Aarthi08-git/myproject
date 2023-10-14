package com.gilead.ems.service;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gilead.ems.dao.TraineeDao;
import com.gilead.ems.model.Trainee;
import com.gilead.ems.processor.CSVProcessor;

public class TraineeService {
	private static final Logger logger = LogManager.getLogger(TraineeService.class);

	/*
	 * Service method to display all the trainees
	 */
	public void getAllTrainees(Connection connection) {
		TraineeDao traineeDao = new TraineeDao();
		List<Trainee> traineesFromDb = traineeDao.readFromDB(connection);
		for (Trainee traineeFromDB : traineesFromDb) {
			logger.info("Trainee ID from DB : " + traineeFromDB.getName());
		}
	}
	/*
	 * service method to copy all the trainees
	 */
	public void copyTrainees(Connection connection) {
		TraineeDao traineeDao = new TraineeDao();
		logger.info("Copying the trainee information");
		traineeDao.copyToFile(connection);
	}
	/*
	 * Service methood to insert all the trainees from input source
	 */
	public List<Trainee> saveTraineeInfo(String fileName, Connection connection) {
		CSVProcessor csvProcessor = new CSVProcessor();
		List<Trainee> trainees = new ArrayList<Trainee>();
		try { // will create a list of trainees from CSV file
			trainees = csvProcessor.getAllTrainees(fileName);
			TraineeDao traineeDao = new TraineeDao();
			for (Trainee trainee : trainees) {
				logger.info(
						"Trainee ID " + traineeDao.saveToDB(connection, trainee) + " inserted into DB successfully");
			}

		} catch (IOException | ParseException e) {
			logger.error("Common Exception occurred " + e.toString());
		}
		return trainees;

	}

	public void deleteTrainees(Connection connection) {
		TraineeDao traineeDao = new TraineeDao();
		logger.info("Deleting the trainee information");
		traineeDao.deleteFromDb(connection);
	}
	

	public void updateTrainee(Connection connection, Trainee trainee) {
		TraineeDao traineeDao = new TraineeDao();
		logger.info("Updating the trainee information");
		traineeDao.updateTrainee(connection,trainee);
		
	}
}
