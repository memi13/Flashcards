package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static String CONNURL = "C:/sqlite/db/CardDB.db";
	
	public DBConnect() {

	}

	/**
	 * Opens Connection to DB
	 * @param connURL
	 * @return
	 */
	public static Connection connectDB() {
        Connection conn = null;
        try {
            // connection String
            String url = "jdbc:sqlite:" + CONNURL;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return conn;
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println("DB could not be connected");
        	return conn;
        }
    }
	/**
	 * Closes Connection
	 * @param conn
	 * @return
	 */
	public static boolean closeConnection(Connection conn) {
		try {
            if (conn != null) {
            	conn.close();
            	System.out.println("Closed");
            }
            return true;
    	}catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection not closed");
            return false;
        }
	}

	public static String getConnURL() {
		return CONNURL;
	}

	public static void setConnURL(String connURL) {
		DBConnect.CONNURL = connURL;
	}

	
}
