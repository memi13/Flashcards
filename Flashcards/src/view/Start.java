package view;

import controller.DataHelper;
import controller.ProgramController;

public class Start {

	public static void main(String[] args) {
		DataHelper dh = new DataHelper();
		ProgramController pc = new ProgramController(dh);
		pc.initGUI();

	}

}
