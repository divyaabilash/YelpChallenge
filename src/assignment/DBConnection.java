package assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

public Connection openConnection() throws SQLException {
	 // Load the Oracle database driver 
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 

	String host = "localhost"; 
     String port = "1521"; 
     String dbName = "xe"; 
     String userName = "system"; 
     String password = "Siddanth$12"; 

     // Construct the JDBC URL 
     String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
     return DriverManager.getConnection(dbURL, userName, password); 

	}
	
}
