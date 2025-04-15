package com.application.quiz;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	private final String DriverName="jdbc.oracle.OracleDriver";
	private final String DatabaseUrl="jdbc:oracle:@localhost:";
	private final String DatabaseUserName = "system";
	private final String DatabasePassword = "system";
	
	public Connection getDatabaseConnection()
	{
		Connection con = null;
		try {
			Class.forName(DriverName);
			con= DriverManager.getConnection(DatabaseUrl,DatabaseUserName,DatabasePassword);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return con;
	}
}
