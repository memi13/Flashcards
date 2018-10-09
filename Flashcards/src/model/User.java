/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author linus
 *
 */
public class User implements IDBFunctions {

	private ArrayList<LanguageBox> languageBoxes;
	private int id;
	private String username;
	private String password;
	private int pwCounter;
	private boolean enabled;
	
	/**
	 * test
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(int idUser) {
		//gets User
	}
	public User(String username) {
		//gets User
	}
	

	/* (non-Javadoc)
	 * @see model.IDBFunctions#save()
	 */
	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see model.IDBFunctions#delete()
	 */
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<LanguageBox> getLanguageBoxes() {
		return languageBoxes;
	}

	public void setLanguageBoxes(ArrayList<LanguageBox> languageBoxes) {
		this.languageBoxes = languageBoxes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPwCounter() {
		return pwCounter;
	}

	public void setPwCounter(int pwCounter) {
		this.pwCounter = pwCounter;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
