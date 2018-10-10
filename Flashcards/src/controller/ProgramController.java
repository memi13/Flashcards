package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import view.dlgNewLanguageBox;
import view.frmHome;
import view.frmLogin;
import model.Card;
import model.IDBFunctions;
import model.LanguageBox;

/**
 * ProgramController controlls all Forms and uses the DataHelper to get Data from the DB to the Forms
 * @author linus
 *
 */
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
	 * @param user Username from Login
	 * @param pw Password from Login
	 */
	public boolean login(String user, String pw) {

		if(dataHelper.checkLogin(user, pw)) {
			//open HOME Form and close LOGIN Form
			System.out.println("Login succesful");
			u = dataHelper.getUser(user);
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
	 * @return Returns an ArrayList with LanguageBoxes
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
	public void openNewLanguageBox(JFrame jf) {
		//Open newLangaugeBox Form
		dlgNewLanguageBox dlg = new dlgNewLanguageBox(jf, this);
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
	
	
//New Language Box Dialog --------------------------------------------------------------------------------------------

	/**
	 * Create a new LanguageBox
	 * @param n Name of the new LanguageBox 
	 * @return
	 */
	public boolean createNewLanguageBox(String n) {
		//check if name already exists -> in dataHelper?
		if(dataHelper.newLanguageBox(n, u.getId())) {
			return true;
		}else {
			return false;
		}
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
	 * @param comp Compartment with Cards
	 * @return Returns a String with Infos about this compartment
	 */
	public String loadBoxStatistics(int comp) {
		return dataHelper.getStatisticDataBox(u.getId(), comp);
	}
	
	/**
	 * Opens the Main Learning Form
	 */
	public void openLearning() {
		//Opens Learning Form 
		System.out.println("LEARNING FORM");
	}
	
	/**
	 * Load all Cards in a Compartment
	 * @param comp witch compartment (-1 = all compartments)
	 */
	public void loadCards(int comp) {
		ArrayList<Card> cards = dataHelper.getCards(this.lb.getId(), comp);
	}
	
	
}
