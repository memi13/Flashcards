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
		Connection conn = DBConnect.connectDB();
		String sql = "Update LanguageBox set name = '" + 
					this.name + 
					"' where id="+this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				DBConnect.closeConnection(conn);
				return true;
			}else {
				DBConnect.closeConnection(conn);
				return false;
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return false;  
		}
		
	}

	@Override
	public boolean delete() {
		Connection conn = DBConnect.connectDB();
		String sql = "delete from LanguageBox where id = " + this.id;
		try {
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(sql) == 1) {
				DBConnect.closeConnection(conn);
				System.out.println("Record deleted");
				return true;
			}else {
				DBConnect.closeConnection(conn);
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
			 Connection conn = DBConnect.connectDB();
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
	 * 
	 * @param fkUser id From the user,  belongs to the LangageBox
	 * @return if the isert Works
	 */
	private boolean createRecord(int fkUser) 
	{
		Connection conn = DBConnect.connectDB();
		String sql = "Insert into LanguageBox (name, fkUser) Values ('"+this.name+"', "+fkUser + ")";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			ResultSet keys = pstmt.getGeneratedKeys();
			this.id = (int)keys.getLong(1);
			return true;
			
		}catch(SQLException e) {
			DBConnect.closeConnection(conn);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	public boolean equals(LanguageBox l)
	{
		if(this.name.equals(l.getName()))
			return true;
		return false;
	}

}
