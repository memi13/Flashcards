package controller;

import model.Card;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Card c = new Card(3);
		c.setCountWrong(99);
		c.setsText("22CREATEDinclSSTatistik");
		c.createRecord();
	}

}
