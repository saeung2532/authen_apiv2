package com.br.api.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class ConnectionDB2Service {

	public ConnectionDB2Service() {
	
	}
	
	public Connection ConnectionDB2(String username, String password) throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";
		Class.forName(jdbcClassName);

		return DriverManager.getConnection(url, username, password);

	}

}
