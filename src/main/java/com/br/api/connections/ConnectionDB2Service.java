package com.br.api.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

@Service
public class ConnectionDB2Service {

	public ConnectionDB2Service() {
	
	}
	
	public Connection doConnect() throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
		String url = "jdbc:as400://192.200.9.190;";
		Class.forName(jdbcClassName);
		
		return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
	    }
	
	public Connection dologin(String username, String password) throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";
		Class.forName(jdbcClassName);

		return DriverManager.getConnection(url, username, password);

	}

}
