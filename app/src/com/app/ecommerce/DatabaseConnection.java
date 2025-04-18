package com.app.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnection {

	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private String databaseUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private String databaseUserName = "system";
	private String databasePassword = "system";
	
	public Optional<Connection> getDatabaseConnection() 
	{
		Connection con = null;
		try {
			Class.forName(driverName);
			con = DriverManager.getConnection(databaseUrl,databaseUserName,databasePassword);
			return Optional.of(con);
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println(" Class Not Imported Properly || Class Not Found " +cnfe.getLocalizedMessage());
		}
		catch(SQLException se)
		{
			System.out.println(se);
		}
		return Optional.empty();
	}
}
