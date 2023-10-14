/**
 * 
 */
package com.gilead.ems.controller;

import java.sql.Connection;

import com.gilead.ems.model.Trainee;
import com.gilead.ems.service.TraineeService;

/**
 * 
 */
public class TraineeUpdateController {

	public void update(Connection connection, Trainee trainee) {
		TraineeService service = new TraineeService();
		service.updateTrainee(connection,trainee);		
	}
}
