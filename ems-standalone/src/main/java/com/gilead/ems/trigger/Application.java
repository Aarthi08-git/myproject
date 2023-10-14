package com.gilead.ems.trigger;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gilead.ems.connection.DBConnection;
import com.gilead.ems.controller.TraineeCopyController;
import com.gilead.ems.controller.TraineeDeleteController;
import com.gilead.ems.controller.TraineeInsertController;
import com.gilead.ems.controller.TraineeReadController;
import com.gilead.ems.controller.TraineeUpdateController;
import com.gilead.ems.model.Trainee;
import com.gilead.ems.util.PropertyReader;

/**
 * Main class that triggers the trainee data uploading functionality
 *
 */
public class Application {
	private static final Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		long startTime = Calendar.getInstance().getTimeInMillis();
		String propertyfilePath = args[0];
		Properties props = PropertyReader.loadProperties(propertyfilePath);
		String operation = args[1];
		String traineeId = null;
		if(operation.equals("update")) {
			traineeId = args[2];
		}
		String csvfilePath = props.getProperty("csvfilepath");
		logger.debug("CSV file Path : "+csvfilePath);
		String dbUrl = props.getProperty("dbUrl");
		logger.debug("DB URL : "+dbUrl);
		String dbUser = props.getProperty("dbUser");
		logger.debug("DB User : "+dbUser);
		String dbPassword = props.getProperty("dbPassword");
		logger.debug("DB Password : "+dbPassword);
		String dbDriverClass = props.getProperty("driverClass");
		logger.debug("DB Class : "+dbDriverClass);
		Connection connection = DBConnection.getConnection(dbUrl,dbUser, dbPassword,dbDriverClass);
		switch (operation) {
		case "insert": {
			TraineeInsertController insertController = new TraineeInsertController();
			logger.info("Inserting the trainee information to the database");
			insertController.save(csvfilePath, connection);
			break;
		}
		case "read": {
			TraineeReadController readController = new TraineeReadController();
			logger.info("Reading the trainee information from database");
			readController.read(connection);
			break;
		}
		case "update": {
			TraineeUpdateController updateController = new TraineeUpdateController();
			logger.info("Updating the trainee information in database");
			Trainee trainee = new Trainee();
			trainee.setId(Integer.parseInt(traineeId));
			trainee.setCompany("Gilead Hi-Tech");
			updateController.update(connection, trainee);
			break;
		}
		case "delete": {
			TraineeDeleteController deleteController = new TraineeDeleteController();
			logger.info("Deleting the trainee information from database");
			deleteController.delete(connection);
			break;
		}
		case "copy": {
			TraineeCopyController copyController = new TraineeCopyController();
			logger.info("copying the trainee information from database");
			copyController.copy(connection);
			break;
		}
		default:
			logger.error("The operation "+ operation + "cannot be performed");
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		double totalTimeTaken = (endTime - startTime)*0.001;
		logger.debug("Total time taken for the application to process "+totalTimeTaken+ " seconds");
	}
}
