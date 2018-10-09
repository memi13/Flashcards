/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author linus
 *
 */
public class Card implements IDBFunctions {

	private static String connURL = "C:/sqlite/db/CardDB.db";
	private int id;
	private String sText;
	private String dText;
	private int boxNumber;
	private Date createdOn;
	private int countWrong;
	private int countLearned;
	private Date lastLearned;
	private Date lastCorrected;
	private int highestBox;
	
	/**
	 * test
	 */
	public Card() {
		// TODO Auto-generated constructor stub
	}
	public Card(int idCard) {
		//gets Card with id idCard
	}
	public Card(String sT, String dT, int bNumber) {
		//creates Record on DB
	}

	public Connection connectDB(String connURL) {
        Connection conn = null;
        try {
            // connection String
            String url = "jdbc:sqlite:" + connURL;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return conn;
        }catch(SQLException e) {
        	System.out.println(e.getMessage());
        	System.out.println("DB could not be connected");
        	return conn;
        }
    }
	public boolean closeConnection(Connection conn) {
	/*	String sql = "SELECT id, sText FROM Card";
        
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
    }*/
	}
	
	
	/* (non-Javadoc)
	 * @see model.IDBFunctions#save()
	 */
	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see model.IDBFunctions#delete()
	 */
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getsText() {
		return sText;
	}

	public void setsText(String sText) {
		this.sText = sText;
	}

	public String getdText() {
		return dText;
	}

	public void setdText(String dText) {
		this.dText = dText;
	}

	public int getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getCountWrong() {
		return countWrong;
	}

	public void setCountWrong(int countWrong) {
		this.countWrong = countWrong;
	}

	public int getCountLearned() {
		return countLearned;
	}

	public void setCountLearned(int countLearned) {
		this.countLearned = countLearned;
	}

	public Date getLastLearned() {
		return lastLearned;
	}

	public void setLastLearned(Date lastLearned) {
		this.lastLearned = lastLearned;
	}

	public Date getLastCorrected() {
		return lastCorrected;
	}

	public void setLastCorrected(Date lastCorrected) {
		this.lastCorrected = lastCorrected;
	}

	public int getHighestBox() {
		return highestBox;
	}

	public void setHighestBox(int highestBox) {
		this.highestBox = highestBox;
	}

}
