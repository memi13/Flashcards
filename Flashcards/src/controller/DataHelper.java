package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Card;
import model.IDBFunctions;
import model.LanguageBox;
import model.User;

public class DataHelper implements IDataHelper{

	public DataHelper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkLogin(String user, String pw) { 
		User u= getUser(user);
		if(user!=null)
		{
			String pwEncode=createMd5(pw);
			if(u.getUsername().equals(u.getUsername()) && u.getPassword().equals(pwEncode))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<LanguageBox> getLanguageBoxes(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Card> getCards(int languageBox, int comp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveCard(int correct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Card> getStatisticData(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}
	public String getStatisticDataBox(int idUser, int box) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean updateCard(IDBFunctions card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCard(IDBFunctions card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean newLanguageBox(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveSetting(IDBFunctions user) {
		// TODO Auto-generated method stub
		return false;
	}

	public User getUser(String username) {
		// TODO Auto-generated method stub
		User u =new User(username);
		if(u.isNotEmpty())
			return new User(username);
		return null;
	}
	private String createMd5(String value)
	{
		try  
		{
			MessageDigest md = MessageDigest.getInstance("MD5");			
			byte[] hashInBytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) 
	        {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
		}
		catch(Exception ex)
		{
			
		}
		return "";
	}
	
    
	
	
	

}

