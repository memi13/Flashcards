package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import view.frmHome;
import view.frmLogin;
import model.Card;
import model.IDBFunctions;
import model.LanguageBox;

public class ProgramController {

	private DataHelper dataHelper;
	private User u;
	private LanguageBox lb;
	
	public ProgramController(DataHelper dh) {
		this.dataHelper = dh;
		u = null;
		lb = null;
	}
	
//Start---------------------------------------------------------------------------------------------------------------
	/**
	 * Initializes GUI with Login Form
	 */
	public void initGUI() {
		//start with LoginGUI
		frmLogin fLogin = new frmLogin(this);
		System.out.println("LOGIN FORM");
	
	}
//-------------------------------------------------------------------------------------------------------------------
	
//Login--------------------------------------------------------------------------------------------------------------
	/**
	 * Called from GUI "LOGIN" -> checks login data and opens the HOME Form
	 * @param user
	 * @param pw
	 */
	public boolean login(String user, String pw) {
		if(dataHelper.checkLogin(user, pw)) {
			//open HOME Form and close LOGIN Form
			System.out.println("Login succesful");
			dataHelper.getUser(user);
			System.out.println("USER ID: " + u.getId());
			openHome();
			return true;
		}else{
			//show error on form
			System.out.println("Login not correct");
			return false;
		}		
	}
	
//-------------------------------------------------------------------------------------------------------------------
	
//HOME---------------------------------------------------------------------------------------------------------------
	/**
	 * Opens the STATISTICS Form
	 */
	public void openStatistics() {
		//Open Statistics Form
		System.out.println("STATISTICS FORM");
	}
	
	/**
	 * Retruns the LanguageBoxes connected to the logged in User
	 * @return
	 */
	public ArrayList<LanguageBox> loadLanguageBoxes() {
		return dataHelper.getLanguageBoxes(u.getId());
	}

	/**
	 * opens the OVERVIEW Form
	 */
	public void openOverview() {
		//Open Overview Form
		System.out.println("OVERVIEW FORM");
	}
	
	/**
	 * opens the NEWLANGUAGEBOX Form
	 */
	public void openNewLanguageBox() {
		//Open newLangaugeBox Form
		System.out.println("NEWLANGUAGEBOX FORM");
	}

	/**
	 * opens the SETTING Form
	 */
	public void openSettings() {
		//Open Settings Form
		System.out.println("SETTINGS FORM");
	}
	
	/**
	 * opens the ALLCARDS Form
	 */
	public void openAllCards() {
		//Open AllCards Form
		System.out.println("ALLCARDS FORM");
	}


//--------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * opens the HOME menu (Please close the open JFrame)
	 */
	public void openHome() {
		//Open HOME Form
		frmHome fHome = new frmHome(this);
		System.out.println("HOME FORM");
	}
	
	/**
	 * Returns Statisticsdata from the choosen box (per user)
	 * @param box
	 * @return
	 */
	public String loadBoxStatistics(int comp) {
		return dataHelper.getStatisticDataBox(u.getId(), comp);
	}
	
	public void openLearning() {
		//Opens Learning Form 
		System.out.println("LEARNING FORM");
	}
	
	public void loadCards(int comp) {
		ArrayList<Card> cards = dataHelper.getCards(this.lb.getId(), comp);
	}
	
	
}
