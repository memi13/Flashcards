package controller;

import java.util.ArrayList;

import model.IDBFunctions;

public interface IDataHelper {

	public boolean checkLogin(String user, String pw);
	public ArrayList<IDBFunctions> getLanguageBoxes(int idUser);
	public ArrayList<IDBFunctions> getCards(int languageBox, int comp);
	public boolean moveCard(int correct); //if card was correct "correct" = 1
	public ArrayList<IDBFunctions> getStatisticData(int idUser);
	public boolean updateCard(IDBFunctions card);
	public boolean deleteCard(IDBFunctions card);
	public boolean newLanguageBox(String name);
	public boolean saveSetting(IDBFunctions user);
	
}
