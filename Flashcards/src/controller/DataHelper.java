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
		for(Card card:getAllCards(languageBox, comp))
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
		if(cards.size()==0)
			return null;
		return cards;
	}
	private ArrayList<Card> getAllCards(int languageBox, int comp)
	{
		LanguageBox l=new LanguageBox(languageBox);
		ArrayList<Card> cards=new ArrayList<Card>();
		for(Card card:l.getCards())
		{
			if(comp==-1 || comp==card.getBoxNumber())
			{
				cards.add(card);
			}
		}
		if(cards.size()==0)
			return null;
		return cards;
	}

	@Override
	public boolean moveCard(int correct) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Card> getStatisticData(int idUser) {
		// TODO Auto-generated method stub
		return null;
	}
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
		data[2]="Next lernTime: "+ Learning(allCards);
		return data;
	}
	
	@Override
	public boolean updateCard(IDBFunctions card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCard(IDBFunctions card) {
		// TODO Auto-generated method stub
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

	public User getUser(String username) {
		// TODO Auto-generated method stub
		User u =new User(username);
		if(u.isNotEmpty())
			return new User(username);
		return null;
	}
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
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);			
		return cal.getTime();
	}
	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
				
		return cal.getTime();
	}

}

