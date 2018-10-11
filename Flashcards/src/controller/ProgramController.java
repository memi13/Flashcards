package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import view.dlgNewLanguageBox;
import view.frmAllCards;
import view.frmHome;
import view.frmLearning;
import view.frmLogin;
import view.frmOverview;
import view.frmStatistics;
import model.Card;
import model.IDBFunctions;
import model.LanguageBox;

/**
 * ProgramController controlls all Forms and uses the DataHelper to get Data from the DB to the Forms
 * @author linus
 */
public class ProgramController {

	private DataHelper dataHelper;
	private User u;
	private int languageBoxId;
	private int boxId;

	/**
	 * Constructor for ProgramController
	 * @param dh Instance of the DataHelper
	 */
	public ProgramController(DataHelper dh) {
		this.dataHelper = dh;
		u = null;
		languageBoxId = -1;
	}
	
	/**
	 * Initializes GUI with Login Form
	 */
	public void initGUI() {
		//start with LoginGUI
		frmLogin fLogin = new frmLogin(this);
		System.out.println("LOGIN FORM");	
	}

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
	
	/**
	 * Opens the STATISTICS Form
	 */
	public void openStatistics() {
		//Open Statistics Form
		frmStatistics frmS = new frmStatistics(this);
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
	 * @param jf Parent JFrame for Dialog
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
		System.out.println("NO SETTINGS IMPLEMENTED");
	}
	
	/**
	 * Opens the ALLCards Form (Modify Cards)
	 */
	public void openAllCards() {
		//Open AllCards Form
		frmAllCards frmA = new frmAllCards(this);
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
	 * Deletes a Cards
	 * @param toDel Cardid to be deleted
	 * @return true if succesfull
	 */
	public boolean deleteCard(int toDel) {
		if(dataHelper.deleteCard(toDel)) {
			System.out.println("Deleted - " + toDel);
			return true;
		}else {
			System.out.println("Cared not deleted - " + toDel);
			return false;
		}
	}
	
	/**
	 * Updates a Cards (SAVE)
	 * @param toSave ID of the Card
	 * @param sText Source or Origin Text
	 * @param dText Destination of Text
	 */
	public boolean updateCard(int toSave, String sText, String dText) {
		if(dataHelper.updateCard(toSave, sText, dText)) {
			System.out.println("Saved " + toSave);
			return true;
		}else {
			System.out.println("Not Saved " + toSave);
			return false;
		}
	}
	
	/**
	 * Checks if the Answer is correct
	 * @param t1 Source one
	 * @param t2 Answer two
	 * @return true if answer is correct
	 */
	public boolean checkCardAnswer(String t1, String t2) {
		if(t1.equals(t2)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean createCard(String sText, String dText) {
		if(dataHelper.createNewCard(sText, dText, this.languageBoxId)) {
			return true;
		}
		return false;
	}

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
	
	/**
	 * opens the HOME menu
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
	
	/**
	 * Updates the Statistics of a Card
	 * @param c Card
	 * @param correct true if the answer was correct
	 */
	public void updateCardStatistics(Card c, Boolean correct) {
		//Update Card Statistics
		dataHelper.moveCard(correct, c.getId());
	}
	
	/**
	 * Load all Cards in a Compartment
	 * @param comp witch compartment (-1 = all compartments)
	 */
	public ArrayList<Card> loadCards(int comp) {
		return dataHelper.getCards(this.languageBoxId, comp);
	}
	
	/**
	 * GetUser
	 * @return
	 */
	public User getU() {
		return u;
	}

	/**
	 * Set User
	 * @param u
	 */
	public void setU(User u) {
		this.u = u;
	}

	public int getLanguageBox() {
		return languageBoxId;
	}

	/**
	 * Set LanguageBox per id
	 * @param lb languageboxid
	 */
	public void setLanguageBox(int lb) {
		this.languageBoxId = lb;
	}
	
	/**
	 * Set LanguageBox per LanguageBox Object
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
	public String getStatitcData(int type)
	{
		ArrayList<Card> cards= dataHelper.getStatisticData(u.getId(), type);
		int countCorrect=0;
		int countWrong=0;
		for(Card card:cards)
		{
			if(type==2)
			{
				countCorrect=+card.getCountLearned()-card.getCountWrong();
				countWrong=+card.getCountWrong();
			}
			else
			{
				if(card.getBoxNumber()==1)
				{
					countWrong++;
				}
				else
				{
					countCorrect++;
				}
			} 
		}
		if(countCorrect==0)
			countCorrect=1;
		if(countWrong==0)
			countWrong=1;
		return countCorrect+","+countWrong;
		//if(type==1)
			//return "5,4";
		//else
			//return "100,50";
	}
}
