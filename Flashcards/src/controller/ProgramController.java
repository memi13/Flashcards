package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import view.dlgNewLanguageBox;
import view.frmHome;
import view.frmLearning;
import view.frmLogin;
import view.frmOverview;
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
	private int languageBoxId;
	private int boxId;

	public ProgramController(DataHelper dh) {
		this.dataHelper = dh;
		u = null;
		languageBoxId = -1;
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
	 * @param lb Object of the selected LanguageBox
	 */
	public void openOverview() {
		//Open Overview Form
		frmOverview frmO = new frmOverview(this, languageBoxId);
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
	 * Opens the ALLCards Form
	 */
	public void openAllCards() {
		//Open AllCards Form
		
		System.out.println("ALLCARDS FORM");
	}
	
	/**
	 * opens the Learning Form
	 */
	public void openLearning() {
		frmLearning frmL = new frmLearning(this, dataHelper.getCards(this.languageBoxId, this.boxId));
		
		System.out.println("LEARNING FORM");
	}

	/**
	 * Checks your answers
	 * @param t1 String one
	 * @param t2 String two
	 * @return
	 */
	public boolean checkCardAnswer(String t1, String t2) {
		if(t1.equals(t2)) {
			return true;
		}else {
			return false;
		}
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
		String data = "";
		for(String line : dataHelper.getStatisticDataBox(languageBoxId, comp)) {
			data = data + line + "\n";
		}
		return data;
	}
	
	public void updateCardStatistics(Card c, Boolean correct) {
		//Update Card Statistics
		
	}
	
	/**
	 * Load all Cards in a Compartment
	 * @param comp witch compartment (-1 = all compartments)
	 */
	public ArrayList<Card> loadCards(int comp) {
		return dataHelper.getCards(this.languageBoxId, comp);
	}
	
	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public int getLanguageBox() {
		return languageBoxId;
	}

	/**
	 * Set LanguageBox id
	 * @param lb languageboxid
	 */
	public void setLanguageBox(int lb) {
		this.languageBoxId = lb;
	}
	/**
	 * Set LanguageBox id
	 * @param lb languagebox object
	 */
	public void setLanguageBox(LanguageBox lb) {
		this.languageBoxId = lb.getId();
	}
	
	public int getBoxId() {
		return this.boxId;
	}
	public void setBoxId(int b) {
		if(b != 0) {
			this.boxId = b;
		}else {
			this.boxId = -1;
		}
	}
}
