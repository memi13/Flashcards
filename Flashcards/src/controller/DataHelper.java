package controller;
import java.util.ArrayList;
import java.util.Date;
import Helper.DateHelper;
import Helper.EncodingHelper;
import enums.LeitnerNumbers;
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
			String pwEncode=EncodingHelper.createMd5(pw);
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
	public ArrayList<Card> getCards(int languageBox, int comp,boolean all) 
	{
		ArrayList<Card> cards=new ArrayList<Card>();
		Date nowDate = java.util.Calendar.getInstance().getTime();
		ArrayList<Card> selecedcCards= getAllCards(languageBox, comp);
		if(all)
		{
			return selecedcCards;
		}
		if(selecedcCards!=null && selecedcCards.size()>0)
		{
			for(Card card:selecedcCards)
			{
				Date nowDateComp=nowDate;
			
				switch (card.getBoxNumber()) {
				case 1:
					nowDateComp=DateHelper.addDays(card.getLastLearned(), LeitnerNumbers.Fach1.getValue());
					break;
				case 2:
					nowDateComp=DateHelper.addDays(card.getLastLearned(), LeitnerNumbers.Fach2.getValue());
					break;
				case 3:
					nowDateComp=DateHelper.addDays(card.getLastLearned(), LeitnerNumbers.Fach4.getValue());
					break;
				case 4:
					nowDateComp=DateHelper.addDays(card.getLastLearned(), LeitnerNumbers.Fach4.getValue());
					break;
				case 5:
					nowDateComp=DateHelper.addDays(card.getLastLearned(), LeitnerNumbers.Fach5.getValue());
					break;
				default:
					break; 
				}
				
				Date lastLenredDay =card.getLastLearned();	
				
				if(lastLenredDay==null|| nowDateComp.getTime()<nowDate.getTime())
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
		ArrayList<Card> allCars=new ArrayList<Card>();
		allCars=l.getCards();

		if(allCars!=null)
		{
			for(Card card:l.getCards())
			{
				if((comp==-1 || comp==card.getBoxNumber())&& card.getBoxNumber()<100)
				{
					cards.add(card);
				}
			}
		}
		// check if it has Card if not retrun null
		if(cards.size()==0)
			return null;
		return cards;
	}
	
	/**
	 * Creates a new Card (missing errorhandling)
	 * @param sText
	 * @param dText
	 * @param lbNumber
	 * @return
	 */
	public boolean createNewCard(String sText, String dText, int lbNumber) {
		Card c = new Card(sText, dText, lbNumber);
		return true;
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
	public ArrayList<Card> getStatisticData(int idUser,int type) {
		ArrayList<Card> all=new ArrayList<Card>();
		ArrayList<Card> resultCard=new ArrayList<Card>();
		User u =new User(idUser);
		for(LanguageBox langBox:u.getLanguageBoxes())
		{
			ArrayList<Card> tempAllcardBox=getAllCards(langBox.getId(), -1);
			if(tempAllcardBox!=null)
				all.addAll(tempAllcardBox);
		} 
		switch (type) {
		case 1:
			Date nowDate = java.util.Calendar.getInstance().getTime();
			for(Card card:all)
			{
				if(
				nowDate.getDay()==card.getLastLearned().getDay() &&
				nowDate.getMonth()==card.getLastLearned().getMonth()  &&
				nowDate.getYear()==card.getLastLearned().getYear()
				)
				{
					resultCard.add(card);
				}
			}
			break;
		case 2:
			resultCard=all;
			break;
		default:
			break;
		}
		return resultCard;
	}
	/**
	 * Get a textes for with ifos about the compartment
	 * @param languageBox the LangageBox for the info
	 * @param comp compartment for the info
	 * @return an Arrary whit infos
	 */
	public String[]  getStatisticDataBox(int languageBox, int comp) {
		ArrayList<Card> allCards=getAllCards(languageBox, comp);
		ArrayList<Card> toLearn=getCards(languageBox, comp,false);
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
	/**
	 * update the Card
	 * @param cardId the id of the Card
	 * @param sText value form the SourText
	 * @param dText value from the destination Text
	 * @return has it work
	 */
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
	/**
	 * Delete the Card
	 * @param cardid the id of the Card
	 * @return has it work
	 */
	public boolean deleteCard(int cardid)
	{
		return deleteCard(new Card(cardid));
	}
	@Override
	public boolean deleteCard(Card card) {
		// TODO Auto-generated method stub
		Card dbCard=new Card(card.getId());
		dbCard.delete();
		//dbCard=new Card(card.getId());
		//if(dbCard.isNotEmpty())
			return true;	
		//return false;
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
					cardDay=DateHelper.addDays(cardDay, LeitnerNumbers.Fach1.getValue());
					break;
				case 2:
					cardDay=DateHelper.addDays(cardDay, LeitnerNumbers.Fach2.getValue());
					break;
				case 3:
					cardDay=DateHelper.addDays(cardDay, LeitnerNumbers.Fach3.getValue());
					break;
				case 4:
					cardDay=DateHelper.addDays(cardDay, LeitnerNumbers.Fach4.getValue());
					break;
				case 5:
					cardDay=DateHelper.addDays(cardDay, LeitnerNumbers.Fach5.getValue());
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
}

