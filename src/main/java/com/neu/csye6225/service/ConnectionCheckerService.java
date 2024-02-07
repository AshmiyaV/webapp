package com.neu.csye6225.service;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionCheckerService {
	
	private final DataSource dataSource;

	@Autowired
	public ConnectionCheckerService(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public void checkDatabaseConnection() {
		try {
			dataSource.getConnection();
			System.out.println("Health Check Connection Successful!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Health Check Connection Failed...");
			throw new RuntimeException(e);
		}
		}
	
}
