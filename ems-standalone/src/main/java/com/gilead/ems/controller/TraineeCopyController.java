package com.gilead.ems.controller;
import java.sql.Connection;

import com.gilead.ems.service.TraineeService;
public class TraineeCopyController {

	public void copy(Connection connection) {
		TraineeService service = new TraineeService();
		service.copyTrainees(connection);
	}

}
