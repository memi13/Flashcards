package controller;

import java.util.ArrayList;

import model.Card;
import model.LanguageBox;
import model.User;

public class Test 
{
	public static void main(String[] args) {
		
		DataHelper d=new DataHelper();
		//System.out.println(d.newLanguageBox("dsafd",2));
		//Card c=new Card("lol", "lol", 1);
		d.getCards(1, 1);
		//Card c=new Card(4);
		//System.out.println(c.getLastLearned());
	}
}
