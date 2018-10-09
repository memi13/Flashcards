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
	private Date lastCorrect;
	private int highestBox;
	
	/**
	 * test
	 */
	public Card() {
		// TODO Auto-generated constructor stub
		id = -1;
		sText = "";
		dText = "";
		boxNumber = 0;
		createdOn = new Date(1999,01,01);
		countWrong = 0;
		countLearned = 0;
		lastLearned = new Date(1999,01,01);
		lastCorrected = new Date(1999,01,01);
		highestBox = 0;		
	}
	public Card(int idCard) {
		//gets Card with id idCard
	}
	public Card(String sT, String dT, int bNumber) {
		//creates Record on DB
	}

	public static Connection connectDB(String connURL) {
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

	
	public boolean setData(int idCard) {
		Connection conn = connectDB(connURL);
		String sql = "select Card.id, Card.sText, Card.dText, Card.boxNumber, Card.createdOn, CardStatistic.countLearned, CardStatistic.countWrong, CardStatistic.highestBox, CardStatistic.lastCorrect, CardStatistic.lastLearned from Card LEFT Join CardStatistic on Card.id = CardStatistic.fkCard where Card.id = " + idCard;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			this.id = rs.getInt("id");
			this.sText = rs.getString("sText");
			this.dText = rs.getString("dText");
			this.boxNumber = rs.getInt("boxNumber");
			this.createdOn = rs.getDate("createdOn");
			this.countWrong = rs.getInt("countWrong");
			this.countLearned = rs.getInt("countLearned");
			this.lastLearned = rs.getDate("lastLearned");
			this.lastCorrect = rs.getDate("lastCorrect");
			this.highestBox = rs.getInt("highestBox");
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(id + " - " + sText);
		
		closeConnection(conn);
		return true;
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
