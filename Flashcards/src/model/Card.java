/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.org.apache.regexp.internal.recompile;

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
	private int fkLangaugeBox;
	
	/**
	 * test
	 */
	public Card() {
		setDefaultValues();				
	}
	public Card(int idCard) {
		setDefaultValues();
		setData(idCard);
	}
	public Card(String sT, String dT, int lbNumber) {
		//creates Record on DB 		String sql = "insert into card (sText, dText, boxNumber) Values ('" + this.sText + "', '" + this.dText+ "', " + this.boxNumber + ")";
		setDefaultValues();
		this.sText = sT;
		this.dText = dT;
		this.fkLangaugeBox = lbNumber;
		createRecord();
	}
	
	private void setDefaultValues() {
		id = -1;
		sText = "";
		dText = "";
		boxNumber = 0;
		createdOn = new Date(1999,01,01);
		countWrong = 0;
		countLearned = 0;
		lastLearned = new Date(1999,01,01);
		lastCorrect = new Date(1999,01,01);
		highestBox = 0;
	}
	
	/**
	 * Opens Connection to DB
	 * @param connURL
	 * @return
	 */
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
	/**
	 * Closes Connection
	 * @param conn
	 * @return
	 */
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

	/**
	 * Gets Data from DB to fill the Object
	 * @param idCard Id of the Card
	 * @return true if successful
	 */
	private boolean setData(int idCard) {
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
			return false;
		}
		
		System.out.println(id + " - " + sText);
		
		if(closeConnection(conn)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Saves (Updates) Object data to DB
	 * @see model.IDBFunctions#save()
	 */
	@Override
	public boolean save() {
		Connection conn = connectDB(connURL);
		String sql = "Update Card set sText = '" + this.sText + "', dText = '" + this.dText + "', boxNumber = " + this.boxNumber + " where Card.id = " + this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				closeConnection(conn);
				return updateStatistic();
			}else {
				closeConnection(conn);
				return false;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Deletes this Card from the DB
	 * @see model.IDBFunctions#delete()
	 */
	@Override
	public boolean delete() {
		deleteS();
		Connection conn = connectDB(connURL);
		String sql = "delete from Card where id = " + this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				closeConnection(conn);
				System.out.println("Record deleted");
				return true;
			}else {
				closeConnection(conn);
				System.out.println("Record not deleted");
				return false;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Deletes this Cards statistics from the DB
	 * @see model.IDBFunctions#delete()
	 */
	private boolean deleteS() {
		Connection conn = connectDB(connURL);
		String sql = "delete from CardStatistics where fkCard = " + this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				closeConnection(conn);
				System.out.println("Record deleted");
				return true;
			}else {
				closeConnection(conn);
				System.out.println("Record not deleted");
				return false;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Updates Statistic Data in DB
	 * @return
	 */
	public boolean updateStatistic() {
		Connection conn = connectDB(connURL);
		String sql = "Update CardStatistic set countLearned = " + this.countLearned + ", countWrong = " + this.countWrong + ", highestBox = " + this.highestBox + ", lastCorrect = " + this.lastCorrect.getTime() + ", lastLearned = " + this.lastLearned.getTime() + " where fkCard = " + this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				closeConnection(conn);
				return true;
			}else {
				closeConnection(conn);
				return false;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * Create a new DB Record from Object
	 * @return
	 */
	private boolean createRecord() {
		Connection conn = connectDB(connURL);
		String sql = "Insert into Card (sText, dText, fkLanguageBox, createdOn) Values ('"+this.sText+"', '"+this.dText+"', "+this.fkLangaugeBox+", " + (long)new utilDate().getTime() + ")";
		String sql2 = "";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			int newID = (int)keys.getLong(1);
			Statement stmt = conn.createStatement();
			if(newID != 0) {
				setId(newID);
				sql2 = "Insert into CardStatistic (fkCard, countLearned, countWrong, highestBox, lastCorrect, lastLearned ) Values " +
						"(" + this.id + ", " + this.countLearned + ", " + this.countWrong + ", " + this.highestBox + ", " + this.lastCorrect + ", " + this.lastLearned + ")";
						
				if(stmt.executeUpdate(sql2) == 1) {
					closeConnection(conn);
					System.out.println("Record created");
					return true;	
				}else {
					closeConnection(conn);
					System.out.println("Records not created");
					return false;
				}
			}else {
				closeConnection(conn);
				System.out.println("Record not created");
				return false;
			}
			
		}catch(SQLException e) {
			closeConnection(conn);
			System.out.println(e.getMessage());
			return false;
		}
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
		return lastCorrect;
	}

	public void setLastCorrected(Date lastCorrected) {
		this.lastCorrect = lastCorrected;
	}

	public int getHighestBox() {
		return highestBox;
	}

	public void setHighestBox(int highestBox) {
		this.highestBox = highestBox;
	}
	@Override
	public String toString() {
		return sText;
	}
	public boolean isNotEmpty()
	{
		if(this.sText.length()>0 && this.dText.length()>0)
			return true;
		return true;
	}
	/**
	 * Checks if statisticValues have changed
	 * @param card
	 * @return
	 */
	public boolean staticNoUpdate(Card card)
	{
		if
		(		this.boxNumber==card.getBoxNumber()&& this.countWrong==card.countWrong && this.highestBox == card.getHighestBox() &&
				this.countLearned==card.countLearned && this.lastLearned.equals(card.getLastLearned()) && 
				this.lastCorrect.equals(card.getLastCorrected())
		)
		{
			return true;
		}
		return false; 
	}
}
