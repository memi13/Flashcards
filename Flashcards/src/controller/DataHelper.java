package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataHelper {

	public DataHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:/CardDB.db";
            //
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            String sql = "SELECT id, sText FROM Card";
            
            try (
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){
                
                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") +  "\t" + 
                                       rs.getString("sText"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                	conn.close();
                	System.out.println("Closed");
                }
        	}catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
	
	
	

}

