package controller;

import java.util.ArrayList;

import model.Card;
import model.IDBFunctions;
import model.LanguageBox;

public interface IDataHelper {

	public boolean checkLogin(String user, String pw);
	public ArrayList<LanguageBox> getLanguageBoxes(int idUser);
	public ArrayList<Card> getCards(int languageBox, int comp);
	public boolean moveCard(int correct); //if card was correct "correct" = 1
	public ArrayList<Card> getStatisticData(int idUser);
	public boolean updateCard(IDBFunctions card);
	public boolean deleteCard(IDBFunctions card);
	public boolean newLanguageBox(String name,int user);
	public boolean saveSetting(IDBFunctions user);
	
}
