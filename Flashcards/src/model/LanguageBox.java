/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author linus
 *
 */
public class LanguageBox implements IDBFunctions {

	private ArrayList<Card> cards;
	private int id;
	private String name;
	
	/**
	 * test
	 */
	public LanguageBox() {
		// TODO Auto-generated constructor stub
	}
	public LanguageBox(int idLanguageBox) {
		//loads per ID
	}
	public LanguageBox(String n) {
		//new LanguageBox
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

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
