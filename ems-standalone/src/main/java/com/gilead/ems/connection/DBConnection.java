package com.gilead.ems.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DBConnection {
	
	public static Connection getConnection(String dbUrl, String userName, String password,String driverClass) {
		final Logger log = LogManager.getLogger(DBConnection.class);
		Connection connection = null;
		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(dbUrl,userName,password);
		}
		 catch (Exception exception) {
				log.error("Error occurred while saving to database " + exception.toString());
			}
		return connection;
	}

}
