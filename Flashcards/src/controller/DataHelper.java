package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.sun.corba.se.impl.orbutil.RepositoryIdUtility;

import javafx.scene.chart.PieChart.Data;
import model.Card;
import model.IDBFunctions;
import model.LanguageBox;
import model.User;

public class DataHelper implements IDataHelper{

	public DataHelper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkLogin(String user, String pw) { 
		User u= getUser(user);
		if(u!=null)
		{
			String pwEncode=createMd5(pw);
			if(u.getUsername().equals(u.getUsername()) && u.getPassword().equals(pwEncode))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<LanguageBox> getLanguageBoxes(int idUser) {
		User u= new User(idUser);
		return u.getLanguageBoxes();
	}

	@Override
	public ArrayList<Card> getCards(int languageBox, int comp) 
	{
		ArrayList<Card> cards=new ArrayList<Card>();
		Date nowDate = java.util.Calendar.getInstance().getTime();
		ArrayList<Card> selecedcCards= getAllCards(languageBox, comp);
		if(selecedcCards!=null && selecedcCards.size()>0)
		{
			for(Card card:selecedcCards)
			{
				Date nowDateComp=nowDate;
			
				switch (card.getBoxNumber()) {
				case 1:
					nowDateComp=addDays(nowDate, 1);
					break;
				case 2:
					nowDateComp=addDays(nowDate, 2);
					break;
				case 3:
					nowDateComp=addDays(nowDate, 10);
					break;
				case 4:
					nowDateComp=addDays(nowDate, 30);
					break;
				case 5:
					nowDateComp=addDays(nowDate, 90);
					break;
				default:
					break; 
				}
				
				Date lastLenredDay =card.getLastLearned();	
				
				if(lastLenredDay==null|| nowDateComp.getTime()>lastLenredDay.getTime())
				{
					cards.add(card);
				}
			}	
		}
		if(cards.size()==0)
			return null;
		return cards;
	}
	/**
	 * Get all Cards from a LanguageBox and a compartment.
	 * @param languageBox The LangageBox from the Card
	 * @param comp in witch compartment is the Card, for all -1, exist compartment(1-100)
	 * @return A List with Card whit in this box and compartment
	 */
	private ArrayList<Card> getAllCards(int languageBox, int comp)
	{
		LanguageBox l=new LanguageBox(languageBox);
		ArrayList<Card> cards=new ArrayList<Card>();
		for(Card card:l.getCards())
		{
			if((comp==-1 || comp==card.getBoxNumber())&& card.getBoxNumber()<100)
			{
				cards.add(card);
			}
		}
		// check if it has Card if not retrun null
		if(cards.size()==0)
			return null;
		return cards;
	}

	@Override
	public boolean moveCard(boolean correct,int idCard) 
	{
		if(correct)
			return correcCard(correct,idCard);
		else
			return incorrecCard(correct,idCard);
			
	}
	/**
	 * Set the Card to the next Compartment and set all statistic Property 
	 * @param correct is the answer correct 
	 * @param idCard the id from the Card
	 * @return has save on DB
	 */
	private boolean correcCard(boolean correct,int idCard)
	{
		Date nowDate = java.util.Calendar.getInstance().getTime();
		Card card=new Card(idCard);
		if(card!=null  && card.isNotEmpty())
		{
			if(card.getBoxNumber()>=5)
				card.setBoxNumber(card.getBoxNumber()+100);
			else
				card.setBoxNumber(card.getBoxNumber()+1);
			card.setCountLearned(card.getCountLearned()+1);
			card.setLastCorrected(new java.sql.Date(nowDate.getTime()));
			card.setLastLearned(new java.sql.Date(nowDate.getTime()));
			if(card.getBoxNumber()>card.getHighestBox())
			{
				card.setHighestBox(card.getBoxNumber());
			}
			card.save();
			return true;
		}
		return false;
	}
	/**
	 * Set the Card to the next Compartment and set all statistic Property 
	 * @param correct is the answer correct 
	 * @param idCard the id from the Card
	 * @return has save on DB
	 */
	private boolean incorrecCard(boolean correct,int idCard)
	{
		Date nowDate = java.util.Calendar.getInstance().getTime();
		Card card=new Card(idCard);
		if(card!=null && card.isNotEmpty())
		{
			card.setBoxNumber(1);
			card.setCountLearned(card.getCountLearned()+1);
			card.setCountWrong(card.getCountWrong()+1);
			card.setLastLearned(new java.sql.Date(nowDate.getTime()));
			card.save();
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Card> getStatisticData(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Get a textes for with ifos about the compartment
	 * @param languageBox the LangageBox for the info
	 * @param comp compartment for the info
	 * @return an Arrary whit infos
	 */
	public String[]  getStatisticDataBox(int languageBox, int comp) {
		ArrayList<Card> allCards=getAllCards(languageBox, comp);
		ArrayList<Card> toLearn=getCards(languageBox, comp);
		if(allCards==null)
			allCards=new ArrayList<Card>();
		if(toLearn==null)
			toLearn=new ArrayList<Card>();
		String [] data=new String[3];
		data[0]="In this Box: "+ allCards.size();
		data[1]="Learning today: "+ toLearn.size();
		if(allCards.size()>0)
			data[2]="Next lernTime: "+ Learning(allCards);
		else
			data[2]="Next lernTime: never";
		return data;
	}
	
	public boolean updateCard(int cardId,String sText,String dText)
	{
		Card newCard=new Card(cardId);
		newCard.setsText(sText);
		newCard.setdText(dText);
		return updateCard(newCard); 
	}
	@Override
	public boolean updateCard(Card card) 
	{
		Card cardDB=new Card(card.getId());
		if(card.staticNoUpdate(cardDB))
		{
			cardDB.setsText(card.getsText());
			cardDB.setdText(card.getdText()); 
			cardDB.save();
			return true;
		}
		return false;
	}
	public boolean deleteCard(int cardid)
	{
		return deleteCard(new Card(cardid));
	}
	@Override
	public boolean deleteCard(Card card) {
		// TODO Auto-generated method stub
		Card dbCard=new Card(card.getId());
		if(dbCard.isNotEmpty())
			return false;
		dbCard.delete();
		dbCard=new Card(card.getId());
		if(dbCard.isNotEmpty())
			return true;	
		return false;
	}

	@Override
	public boolean newLanguageBox(String name,int user) {
		LanguageBox newLangageBox=new LanguageBox(name,user);
		if(newLangageBox.equals(new LanguageBox(newLangageBox.getId())))
			return true;
		return false;
	}

	@Override
	public boolean saveSetting(IDBFunctions user) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Get the user which this name
	 * @param username the username which is search
	 * @return a user
	 */
	public User getUser(String username) {
		// TODO Auto-generated method stub
		User u =new User(username);
		if(u.isNotEmpty())
			return new User(username);
		return null;
	}
	/**
	 * a encode mh5 hasch
	 * @param value the value to anecode
	 * @return
	 */
	private String createMd5(String value)
	{
		try  
		{
			MessageDigest md = MessageDigest.getInstance("MD5");			
			byte[] hashInBytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
	        for (byte b : hashInBytes) 
	        {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
		}
		catch(Exception ex)
		{
			
		}
		return "";
	}
	/**
	 * get the time wenn is the next lernign time
	 * @param cards
	 * @return
	 */
	private String Learning(ArrayList<Card> cards)
	{
		Date nextLearnigTime=null;
		Date nowDate = java.util.Calendar.getInstance().getTime();
		for(Card card:cards)
		{
			Date cardDay=card.getLastLearned(); 
			if(cardDay!=null)
			{ 
				switch (card.getBoxNumber()) 
				{
				case 1:
					cardDay=addDays(cardDay, 1);
					break;
				case 2:
					cardDay=addDays(cardDay, 2);
					break;
				case 3:
					cardDay=addDays(cardDay, 10);
					break;
				case 4:
					cardDay=addDays(cardDay, 30);
					break;
				case 5:
					cardDay=addDays(cardDay, 90);
					break;
				default:
					break;
				}
			}
			else
			{
				cardDay=nowDate;
			}
			if(nextLearnigTime==null ||nowDate.getTime()>=cardDay.getTime())
			{
				if(nextLearnigTime==null || nextLearnigTime.getTime()>cardDay.getTime())
				{
					nextLearnigTime=cardDay;
				}
			}
		}
		return nextLearnigTime.toString();
	}
	/**
	 * add a days to a date
	 * @param date the state day 
	 * @param days number of the to remove 
	 * @return the new Date
	 */
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);			
		return cal.getTime();
	}
	/**
	 * removes a days to a date
	 * @param date the state day 
	 * @param days number of the to remove 
	 * @return the new Date
	 */
	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
				
		return cal.getTime();
	}

}

