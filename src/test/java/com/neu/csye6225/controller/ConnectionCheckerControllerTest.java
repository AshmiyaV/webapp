package com.neu.csye6225.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.neu.csye6225.service.ConnectionCheckerService;

public class ConnectionCheckerControllerTest {
	private ConnectionCheckerController connectionCheckerController;
	private ConnectionCheckerService connectionCheckerService;
	
	@BeforeEach
	public void setUp() {
		//creating mock
		connectionCheckerService = mock(ConnectionCheckerService.class); 
		
		//ConnectionCheckerController instantiation with mock ConnectionCheckerService data
		connectionCheckerController = new ConnectionCheckerController(connectionCheckerService); 
	}
	
	@Test
	public void testCheckDatabaseConnectionSuccess() {
		doNothing().when(connectionCheckerService).checkDatabaseConnection();
	    ResponseEntity<String> responseEntity = connectionCheckerController.checkDatabaseConnection(null, null, HttpMethod.GET, "");
	    assertEquals(200, responseEntity.getStatusCode().value());
    }
	
	@Test
	public void testCheckDatabaseConnectionFailure() {
		doThrow(new RuntimeException("Connection error")).when(connectionCheckerService).checkDatabaseConnection();
	    ResponseEntity<String> responseEntity = connectionCheckerController.checkDatabaseConnection(null, null, HttpMethod.GET, "");
	    assertEquals(503, responseEntity.getStatusCode().value());
    }
	@Test
	public void testCheckDatabaseConnectionFailureWithBody() {
		doThrow(new RuntimeException("Connection error")).when(connectionCheckerService).checkDatabaseConnection();
	    ResponseEntity<String> responseEntity = connectionCheckerController.checkDatabaseConnection(new HashMap<>(),"{}", HttpMethod.GET, "");
	    assertEquals(400, responseEntity.getStatusCode().value());
    }
}
