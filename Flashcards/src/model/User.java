/**
 * 
 */
package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.EncodingHelper;

/**
 * @author linus
 *
 */
public class User implements IDBFunctions 
{
	private ArrayList<LanguageBox> languageBoxes;
	private int id;
	private String username;
	private String password;
	private int pwCounter;
	private boolean enabled;
	/**
	 * set to default value
	 */
	public User() {
		id=-1;
		username="";
		password=EncodingHelper.createMd5("123456");
		languageBoxes=new ArrayList<LanguageBox>();
	}
	/**
	 * create a object wiht the value from DB value of this user
	 * @param idUser id to search
	 */
	public User(int idUser) {
		getUser(idUser);
	}
	/**
	 * create a object wiht the value from DB value of this user
	 * @param username usernmae to search
	 */
	public User(String username) {
		getUser(username);
	}
	
	@Override
	public boolean save() {
		Connection conn = DBConnect.connectDB();
		String sql = "Update User set password = '" + 
					this.password+"', pwCounter = "+this.pwCounter+",enabled = "+this.enabled+ 
					" where id="+this.id;
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
		String sql = "delete from User where id = " + this.id;
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
	 * get the all AlagaBoxes of the user
	 * @return
	 */
	public ArrayList<LanguageBox> getLanguageBoxes() {
		return languageBoxes;
	}

	/**
	 * set the list of langageBoxes
	 * @param languageBoxes all LangageBox of the user
	 */
	public void setLanguageBoxes(ArrayList<LanguageBox> languageBoxes) {
		this.languageBoxes = languageBoxes;
	}

	/**
	 * get the user id
	 * @return id of user
	 */
	public int getId() {
		return id;
	}
	/**
	 * get the name of the user
	 * @return get user name
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * get the user name
	 * @param username the username of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * get the Password
	 * @return passwort
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * set the Passwort 
	 * @param password the new Passwort 
	 */
	public void setPassword(String password) {
		this.password = EncodingHelper.createMd5(password);
	}
	/**
	 * set number of rongPw tries
	 * @return the number
	 */
	public int getPwCounter() {
		return pwCounter;
	}

	/**
	 * set the number of Rong pw sets
	 * @param pwCounter the new number
	 */
	public void setPwCounter(int pwCounter) {
		this.pwCounter = pwCounter;
	}

	/**
	 * get the status of the user
	 * @return the Status
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * set the user enabled
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * 
	 * @return if the isert Works
	 */
	private boolean createRecord() 
	{
		Connection conn = DBConnect.connectDB();
		String sql = "Insert into User (username,password,pwCounter,enabled) Values+"
				+" ('"+this.username+"', '"+this.password + "',0,1)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			DBConnect.closeConnection(conn);
			System.out.println(e.getMessage());
			return false;
		}
	}
	private boolean getUser(String user)
	{
		return setData("select id,username,password,pwCounter,enabled from User where username='"+user+"'");
	}
	private boolean getUser(int user)
	{
		return setData("select id,username,password,pwCounter,enabled from User where id="+user);
	}
	
	private boolean setData(String sqlStatment)
	{
		String sql = sqlStatment;//;		
		 try
		 {
			 Connection conn = DBConnect.connectDB();
			 Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql);
	         rs.next();
	         this.id=rs.getInt("id");
	         this.username=rs.getString("username");
	         this.password=rs.getString("password");
	         this.pwCounter=rs.getInt("pwCounter");
	         int enabele=rs.getInt("enabled");
	         this.enabled=(enabele != 0);
	         if(rs.next())
	         {
	        	 throw new Exception("found more date, as allowed");
	         }
	         System.out.println(this.username);
	         System.out.println(this.id);
	         System.out.println(this.pwCounter);
	         System.out.println(this.enabled);
	         sql="select id from LanguageBox where fkUser="+this.id;
	         rs = stmt.executeQuery(sql);
             while (rs.next()) 
             {
            	 int idLanguageBox=rs.getInt("id");
            	 if(languageBoxes==null)
            		 languageBoxes=new ArrayList<LanguageBox>();
            	 languageBoxes.add(new LanguageBox(idLanguageBox));
             }       
		 }
		 catch (Exception e) {
			 System.out.println(e.getMessage());
		}	 
		 return false;		
	}
	public boolean isNotEmpty()
	{
		if((password!=null && password.length()>0) || (username!=null && username.length()>0))
			return true;
		return false;
	}
}
