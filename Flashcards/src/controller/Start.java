package controller;

import model.Card;

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataHelper dh = new DataHelper();
		ProgramController pc = new ProgramController(dh);
		pc.initGUI();
		
	}

}
