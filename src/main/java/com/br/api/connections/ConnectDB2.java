package com.br.api.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB2 {

	public Connection doConnect() throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.as400.access.AS400JDBCDriver";
		String url = "jdbc:as400://192.200.9.190;";

		Class.forName(jdbcClassName);
		return DriverManager.getConnection(url, "M3SRVICT", "ICT12345");
	}

	public static Connection doLogin(String user, String pass) throws ClassNotFoundException, SQLException {
		String jdbcClassName = "com.ibm.jtopenlite.database.jdbc.JDBCDriver";
		String url = "jdbc:jtopenlite://192.200.9.190";

		Class.forName(jdbcClassName);
		return DriverManager.getConnection(url, user, pass);
	}

}
