package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DbConnection {

	private static String url = "jdbc:sqlite:src/database/librairie";
	private transient Connection connexion;
    private static DbConnection instance;
	
	private DbConnection() {
		try {
			connexion = DriverManager.getConnection(url);
		} catch(SQLException e) {
			  System.out.println(e.getMessage());
		}
	}
	
	public static DbConnection getInstance(){
        instance= new DbConnection();
        return instance;
    }

    public Connection getConnexion() {
        return connexion;
    }

}
