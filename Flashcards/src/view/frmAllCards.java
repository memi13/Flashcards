package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.ScrollPane;
import java.awt.Toolkit;
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
import javax.swing.JScrollPane;
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
	private int cardCount;
	
	JPanel mainPanel = new JPanel();	
	//scrollable?
	JPanel tablePanel = new JPanel();
	GridLayout dynGrid = new GridLayout(1, 4);
	BorderLayout brdLayout = new BorderLayout();
	EmptyBorder brdEmpty = new EmptyBorder(20,50,20,50);
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

		try{
			cards = pController.loadCardsAll(-1);
			cardCount = cards.size();
		}catch(Exception ex) {
			System.out.println("Error");
			cards = new ArrayList<Card>();
			cardCount = 0;
		}
		
		btnCancle = new JButton("Close");
		txtFields = new JTextField[cardCount*2];
		btnButtonsDel = new JButton[cardCount];
		btnButtonsSave = new JButton[cardCount];
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
		
		mainPanel.add(btnNew, BorderLayout.NORTH);
		mainPanel.add(btnCancle, BorderLayout.SOUTH);
	
		//Grid Control
		dynGrid.setRows(cards.size());
		tablePanel.setLayout(dynGrid);
		tablePanel.setBorder(brdEmpty2);
		this.validate();
		for(JComponent c : components) {
			tablePanel.add(c);
		}
		//scrollpane
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
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
		case "Close":
			System.out.println("click - Cancle");
			pController.openHome();
			this.dispose();
			break;
		case "New":
			System.out.println("click - Save");
			addNewCard(e);
			//Open a Dialog to create a new Card
			break;
		case "X":		
			System.out.println(getCardID(e));
			pController.deleteCard(getCardID(e));
			updateForm(e);
			break;	
		case "Save":
			System.out.println(e.getActionCommand());
			setCardUpdate(e);
			break;
		}
	}
	
	private void updateForm(ActionEvent e) {		
		this.dispose();
		frmAllCards frm = new frmAllCards(pController);
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
	
	/**
	 * Creates a small dialog to create a new Card
	 */
	private void addNewCard(ActionEvent e) {
		JTextField txtSource = new JTextField();
		JTextField txtDestination = new JTextField();
		//Textfelder in einen Array vom Dtentyp Object
		Object[] message = {"Source Text: ", txtSource, "Target Text: ", txtDestination};
		
		//Dialog erstellen
		JOptionPane dlgNew = new JOptionPane();
		int response = dlgNew.showConfirmDialog(this, message, "New Card", JOptionPane.OK_CANCEL_OPTION);
		dlgNew.setVisible(true);
		
		//Werte auslesen
		if(response == JOptionPane.OK_OPTION) {
			pController.createCard(txtSource.getText(), txtDestination.getText());
			updateForm(e);
			System.out.println("Eingabe: " + txtSource.getText() + ", Eingabe: " + txtDestination.getText());
		}
	}
	
}
