/**
 * 
 */
package com.gilead.ems.controller;

import java.sql.Connection;

import com.gilead.ems.service.TraineeService;

/**
 * 
 */
public class TraineeInsertController {

	public void save(String fileName, Connection connection) {
		TraineeService service = new TraineeService();
		service.saveTraineeInfo(fileName, connection);
	}

}
