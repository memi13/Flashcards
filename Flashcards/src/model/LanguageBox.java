/**
 * 
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
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
	 * test
	 */
	public LanguageBox() {
		// TODO Auto-generated constructor stub
	}
	public LanguageBox(int idLanguageBox) {
		//loads per ID
		GetLanguageBox(idLanguageBox);
	}
	public LanguageBox(String n) {
		//new LanguageBox
	}

	/* (non-Javadoc)
	 * @see model.IDBFunctions#save()
	 */
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

	/* (non-Javadoc)
	 * @see model.IDBFunctions#delete()
	 */
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

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	private boolean GetLanguageBox(int id)
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

}
