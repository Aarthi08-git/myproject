package com.gilead.ems.controller;

import java.sql.Connection;

import com.gilead.ems.service.TraineeService;

public class TraineeReadController {

	public void read(Connection connection) {
		TraineeService service = new TraineeService();
		service.getAllTrainees(connection);		
	}

}
