package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

import controller.ProgramController;
import model.Card;

public class frmAllCards extends JFrame implements ActionListener {

	private ProgramController pController;
	
	private JButton btnCancle;
	private JButton btnNew;
	private JTextField[] txtFields;
	private JButton[] btnButtonsDel;
	private JButton[] btnButtonsSave;
	private JLabel lblOrigin;
	private JLabel lblDestination;
	
	private ArrayList<Card> cards;
	private ArrayList<JComponent> components;
	
	JPanel mainPanel = new JPanel();	
	//scrollable?
	JPanel tablePanel = new JPanel();
	GridLayout dynGrid = new GridLayout(1, 4);
	BorderLayout brdLayout = new BorderLayout();
	EmptyBorder brdEmpty = new EmptyBorder(10,50,100,50);
	EmptyBorder brdEmpty2 = new EmptyBorder(0,0,0,0);
	

	public frmAllCards(ProgramController pc) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Modify Cards");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() {
		//Load all Cards
		cards = pController.loadCards(-1);
		btnCancle = new JButton("Cancle");
		txtFields = new JTextField[cards.size()*2];
		btnButtonsDel = new JButton[cards.size()];
		btnButtonsSave = new JButton[cards.size()];
		components = new ArrayList<JComponent>();
		btnNew = new JButton("New");
		
		int i = 0;
		int j = 0;
		while(i<txtFields.length) {
			txtFields[i] = new JTextField(cards.get(j).getsText());
			txtFields[i+1] = new JTextField(cards.get(j).getdText());
			btnButtonsDel[j] = new JButton("X");
			btnButtonsSave[j] = new JButton("Save");
			//Should be in bindListeners
			btnButtonsDel[j].addActionListener(this);
			btnButtonsDel[j].setActionCommand("del-" + cards.get(j).getId());
			btnButtonsSave[j].addActionListener(this);
			btnButtonsSave[j].setActionCommand("sav-" + cards.get(j).getId());
			
			components.add(txtFields[i]);
			components.add(txtFields[i+1]);
			components.add(btnButtonsDel[j]);
			components.add(btnButtonsSave[j]);
			i+=2;
			j+=1;
		}
	}
	
	private void initGui() {
		mainPanel.setLayout(brdLayout);
		mainPanel.setBorder(brdEmpty);
		
		setSize(500,500);
		
		mainPanel.add(btnCancle, BorderLayout.NORTH);
		mainPanel.add(btnNew, BorderLayout.SOUTH);
	
		//Grid Control
		//GridLayout dynGrid = new GridLayout(cards.size(), 4);
		dynGrid.setRows(cards.size());
		//dynGrid.setColumns(4);
		tablePanel.setLayout(dynGrid);
		tablePanel.setBorder(brdEmpty2);
		this.validate();
		for(JComponent c : components) {
			tablePanel.add(c);
		}
		
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		this.validate();
		this.repaint();
		this.validate();
	}
	
	private void bindListener() {
		btnCancle.addActionListener(this);
		btnNew.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		switch(btn.getText()){
		case "Cancle":
			System.out.println("click - Cancle");
			pController.openHome();
			this.dispose();
			break;
		case "New":
			System.out.println("click - Save");
			addNewCard();
			//Open a Dialog to create a new Card
			break;
		case "X":		
			System.out.println(getCardID(e));
			pController.deleteCard(getCardID(e));
			removeFields(e);
			break;	
		case "Save":
			System.out.println(e.getActionCommand());
			setCardUpdate(e);
			break;
		}
	}
	
	private void removeFields(ActionEvent e) {
		/*int id = 0;
		for(int i = 0; i< btnButtonsDel.length; i++) {
			if(i!=0) {
				id = i*4;
			}else {
				id = 0;
			}
			if(btnButtonsDel[i] == e.getSource()) {
				components.remove(id);
				components.remove(id+1);
				components.remove(id+2);
				components.remove(id+3);	
			}
		}*/
		
		initComponents();
		initGui();
		bindListener();
	}
	
	private void setCardUpdate(ActionEvent e) {
		String dText;
		String sText;
		int cardID = getCardID(e);
		int textboxid = 0;
		for(int i = 0; i< btnButtonsSave.length; i++) {
			if(i!=0) {
				textboxid = i*2;
			}else {
				textboxid = 0;
			}
			if(btnButtonsSave[i] == e.getSource()) {
				dText = txtFields[textboxid+1].getText();
				sText = txtFields[textboxid].getText();
				if(pController.updateCard(cardID, sText, dText)) {
					System.out.println("Card updated");
				}else {
					System.out.println("Card could not be updated");
				}
				System.out.println(i + " - " + btnButtonsSave[i].getActionCommand() + " - " + txtFields[textboxid].getText() + " - " + txtFields[textboxid+1].getText());
			}
		}
	}
	
	private int getCardID(ActionEvent e) {
		String strID = e.getActionCommand();
		strID = strID.substring(4);
		return Integer.parseInt(strID);
	}
	
	private void addNewCard() {
		JTextField txtSource = new JTextField();
		JTextField txtDestination = new JTextField();
		//Textfelder in einen Array vom Dtentyp Object
		Object[] message = {"Source Text: ", txtSource, "Target Text: ", txtDestination};
		
		//Dialog erstellen
		JOptionPane dlgNew = new JOptionPane();
		int response = dlgNew.showConfirmDialog(this, message, "New Card", JOptionPane.OK_CANCEL_OPTION);
		dlgNew.setVisible(true);
		//dlgNew.getV
		
		//if(dlgNew == JOptionPane.YES_OPTION)
		//Werte auslesen
		if(response == JOptionPane.OK_OPTION) {
			pController.
			System.out.println("Eingabe: " + txtSource.getText() + ", Eingabe: " + txtDestination.getText());
		}
	}
	
}
