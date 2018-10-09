package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import model.User;
import model.IDBFunctions;
import model.LanguageBox;

public class ProgramController {

	private DataHelper dataHelper;
	private User u;
	
	public ProgramController(DataHelper dh) {
		this.dataHelper = dh;
	}
	
	/**
	 * Initializes GUI with Login Form
	 */
	public void initGUI() {
		//start with LoginGUI
		System.out.println("LOGIN FORM");
	
	}
	/**
	 * Called from GUI "LOGIN" -> checks login data and opens the HOME Form
	 * @param user
	 * @param pw
	 */
	public void login(String user, String pw) {
		if(dataHelper.checkLogin(user, pw)) {
			//open HOME Form and close LOGIN Form
			System.out.println("Login succesful");
			dataHelper.getUser(user);
			System.out.println("USER ID: " + u.getId());
		}else{
			//Show errormessage
			System.out.println("Login not correct");
		}		
	}
	/**
	 * Opens the STATISTICS Form
	 */
	public void openStatistics() {
		//Open Statistics Form
		System.out.println("STATISTICS FORM");
	}
	
	/**
	 * opens the HOME menu (Please close the open JFrame)
	 */
	public void openHome() {
		//Open HOME Form
		System.out.println("HOME FORM");
	}
	
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
	
	
	
	
	
	
	
}
