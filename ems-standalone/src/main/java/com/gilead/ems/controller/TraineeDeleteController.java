/**
 * 
 */
package com.gilead.ems.controller;

import java.sql.Connection;

import com.gilead.ems.service.TraineeService;

/**
 * 
 */
public class TraineeDeleteController {

	public void delete(Connection connection) {
		TraineeService service = new TraineeService();
		service.deleteTrainees(connection);
	}

}
