package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Card;
import model.IDBFunctions;
import model.LanguageBox;

public class DataHelper implements IDataHelper{

	public DataHelper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkLogin(String user, String pw) { 
		// TODO Auto-generated method stub
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

	public void getUser(String username) {
		// TODO Auto-generated method stub
		
	}
	
	
    
	
	
	

}

