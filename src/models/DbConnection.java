package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnection {
	
	 private DbConnection() {
	        throw new UnsupportedOperationException();
	        }
	 
	public static Connection connect() {
		String url = "jdbc:sqlite:src/database/librairie";
		try {
			return DriverManager.getConnection(url);
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
