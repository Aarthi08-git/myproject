package com.gilead.ems.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gilead.ems.model.Trainee;

public class TraineeDao {

	final Logger log = LogManager.getLogger(TraineeDao.class);

	/*
	 * DAO class to save the trainee details
	 */
	public String saveToDB(Connection connection, Trainee trainee) {

		String name = null;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("INSERT INTO sakila.Trainee values(?,?,?,?,?,?,?)");
			Date dobSql = new Date(trainee.getDob().getTime());
			Date dojSql = new Date(trainee.getDoj().getTime());
			statement.setInt(1, trainee.getId());
			statement.setString(2, trainee.getCompany());
			statement.setString(3, trainee.getName());
			statement.setDate(4, dobSql);
			statement.setString(5, trainee.getGender());
			statement.setDate(6, dojSql);
			statement.setString(7, trainee.getRole());
			statement.executeUpdate();
			name = trainee.getName();
		} catch (Exception exception) {
			log.error("Error occurred while saving to database " + exception.toString());
		}
		return name;
	}

	/*
	 * DAO class to read the trainee information
	 */
	public List<Trainee> readFromDB(Connection connection) {
		List<Trainee> trainees = new ArrayList<Trainee>();
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from sakila.trainee"); // 6 records
			if (rs != null) {
				while (rs.next()) {
					Trainee trainee = new Trainee(); // 1,2,3,4,5,6
					trainee.setId(rs.getInt("id"));
					trainee.setName(rs.getString("name"));
					trainees.add(trainee);
				}
			}

		} catch (Exception exception) {
			log.error("Error occurred while reading record from database " + exception.toString());
		}
		return trainees;
	}

	/*
	 * DAO class to delete the trainee information
	 */
	public void deleteFromDb(Connection connection) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("DELETE from sakila.trainee"); // 6 records

		} catch (Exception exception) {
			log.error("Error occurred while deleting record from database " + exception.toString());
		}
	}

	/*
	 * DAO Class to update company name
	 */
	public void updateTrainee(Connection connection, Trainee trainee) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("UPDATE sakila.Trainee SET company =? where id =?");
			statement.setString(1, trainee.getCompany());
			statement.setInt(2, trainee.getId());
			statement.executeUpdate();
		} catch (Exception exception) {
			log.error("Error occurred while saving to database " + exception.toString());
		}

	}

	/*
	 * *DAO class to copy trainee information
	 */
	public void copyToFile(Connection connection) {
		log.debug("inside copy method file");
		Statement statement;
		String csvFilePath = "D://readfile.csv";
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from sakila.trainee");
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
			log.debug("File processing starts");
			fileWriter.append("id,company,name,dob,gender,doj,role");
			fileWriter.append("\n");
			log.debug("File header prints");
			if (rs != null) {
				while (rs.next()) {
					Integer id = Integer.valueOf(rs.getInt("id"));
					String ID = id.toString();
					fileWriter.append(ID);
					fileWriter.append(",");
					fileWriter.append(rs.getString("company"));
					fileWriter.append(",");
					fileWriter.append(rs.getString("name"));
					fileWriter.append(",");
					fileWriter.append(rs.getDate("dob").toString());
					fileWriter.append(",");
					fileWriter.append(rs.getString("gender"));
					fileWriter.append(",");
					fileWriter.append(rs.getDate("doj").toString());
					fileWriter.append(",");
					fileWriter.append(rs.getString("role"));
					fileWriter.append("\n");
				}
			}
			fileWriter.close();

		} catch (Exception exception) {
			log.error("Error occurred while copying record from database " + exception.toString());
		}
	}
}
