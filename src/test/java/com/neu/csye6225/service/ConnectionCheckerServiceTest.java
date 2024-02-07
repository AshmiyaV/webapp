package com.neu.csye6225.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionCheckerServiceTest {
	private ConnectionCheckerService connectionCheckerService;
	private DataSource dataSource;
	
	@BeforeEach
	public void setUp() {
		//creating mock
		dataSource = mock(DataSource.class);
		
		//ConnectionCheckerService instantiation with mock dataSorce data
		connectionCheckerService = new ConnectionCheckerService(dataSource); 
	}
	
	@Test
	public void testCheckDatabaseConnectionSuccess() throws SQLException {
		Connection connectionMock = mock(Connection.class);
		when(dataSource.getConnection()).thenReturn(connectionMock);
		connectionCheckerService.checkDatabaseConnection();
		verify(dataSource, times(1)).getConnection();
	}
	
	@Test
	public void testCheckDatabaseConnectionFailure() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException("Connection Failed..."));
		assertThrows(RuntimeException.class, () -> {
			connectionCheckerService.checkDatabaseConnection();
		});
	}
	
}
