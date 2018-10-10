/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author linus
 *
 */
public class LanguageBox implements IDBFunctions {
	
	private static String connURL = "C:/sqlite/db/CardDB.db";
	private ArrayList<Card> cards;
	private int id;
	private String name;
	
	/**
	 * crate default object
	 */
	public LanguageBox() {
		int id=-1;
		name="";
		cards=new ArrayList<Card>();
	}
	/**
	 * crate an object with the date of LanguageBox from the DB
	 * @param idLanguageBox id of LanguageBox
	 */
	public LanguageBox(int idLanguageBox) {
		//loads per ID
		getLanguageBox(idLanguageBox);
	}
	/**
	 * Create a new LanguageBox
	 * @param the name of the new LangaugeBox
	 */
	public LanguageBox(String n,int idUser) {
		//new LanguageBox
		this.name=n;
		createRecord(idUser);
	}

	@Override
	public boolean save() {
		Connection conn = connectDB(connURL);
		String sql = "Update LanguageBox set name = '" + 
					this.name + 
					"' where id="+this.id;
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

	@Override
	public boolean delete() {
		Connection conn = connectDB(connURL);
		String sql = "delete from LanguageBox where id = " + this.id;
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
	 * All Cards of the LanguageBox
	 * @return a list of Cards
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * Set all Cards of The LanguageBox
	 * @param cards a List of Cards
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public int getId() {
		return id;
	}

	/**
	 * gets the name of LangaugeBox
	 * @return name of LangaugeBox
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the name of The Language Box
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get Date from the DB, and set the date Felds from the object
	 * @param id the id of the search object
	 * @return if it work or not
	 */
	private boolean getLanguageBox(int id)
	{
		String sql = "SELECT id,name from LanguageBox where  id="+id;		
		 try
		 {
			 Connection conn = connectDB(connURL);
			 Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql);
	         rs.next();
	         this.id=rs.getInt("id");
	         this.name=rs.getString("name");
	         if(rs.next())
	         {
	        	 throw new Exception("found more date, as allowed");
	         }
	         System.out.println(this.name);
	         System.out.println(this.id);
	         sql="Select id from Card where fkLanguageBox="+this.id;
	         rs = stmt.executeQuery(sql);
             while (rs.next()) 
             {
            	 int idCard=rs.getInt("id");
            	 if(cards==null)
            		 cards=new ArrayList<Card>();
            	 cards.add(new Card(idCard));
             }       
		 }
		 catch (Exception e) {
			 System.out.println(e.getMessage());
		}	 
		 return false;		
	}
	/**
	 * Open the SQL connection
	 * @param connURL where is the DB
	 * @return the DB Connection 
	 */
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
	/**
	 * close the db connection
	 * @param conn
	 * @return if it works the true else false
	 */
	private boolean closeConnection(Connection conn) {
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
	 * 
	 * @param fkUser id From the user,  belongs to the LangageBox
	 * @return if the isert Works
	 */
	private boolean createRecord(int fkUser) 
	{
		Connection conn = connectDB(connURL);
		String sql = "Insert into LanguageBox (LanguageBox, fkUser) Values ('"+this.name+"', "+fkUser + ")";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			closeConnection(conn);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
