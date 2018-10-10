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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JButton btnSave;
	private JTextField[] txtFields;
	private JButton[] btnButtons;
	private JLabel lblOrigin;
	private JLabel lblDestination;
	private ArrayList<Card> cards;
	private ArrayList<Card> cardsDelete;
	private ArrayList<Card> cardsUpdate;
	
	private ArrayList<JComponent> components;
	

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
		btnButtons = new JButton[cards.size()];
		components = new ArrayList<JComponent>();
		cardsDelete = new ArrayList<Card>();
		cardsUpdate = new ArrayList<Card>();
		btnSave = new JButton("Save");
		
		int i = 0;
		int j = 0;
		while(i<txtFields.length) {
			txtFields[i] = new JTextField(cards.get(j).getsText());
			txtFields[i+1] = new JTextField(cards.get(j).getdText());
			btnButtons[j] = new JButton("X");
			components.add(txtFields[i]);
			components.add(txtFields[i+1]);
			components.add(btnButtons[j]);
			i+=2;
			j+=1;
		}
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();	
		//scrollable?
		JPanel tablePanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(150,50,200,50));
		
		setSize(500,500);
		
		mainPanel.add(btnCancle, BorderLayout.NORTH);
		mainPanel.add(btnSave, BorderLayout.SOUTH);
	
		//Grid Control
		GridLayout dynGrid = new GridLayout(cards.size(), 2);
		tablePanel.setLayout(dynGrid);
		tablePanel.setBorder(new EmptyBorder(0,0,0,0));
		this.validate();
		for(JComponent c : components) {
			tablePanel.add(c);
		}
		
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void bindListener() {
		btnCancle.addActionListener(this);
		int i = 0;
		for(JButton btn : btnButtons) {
			btn.addActionListener(this);
			btn.setActionCommand("" + i);
			i++;
		}
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
		case "Save":
			System.out.println("click - Save");
			if(cardsDelete.size()>0) {
				pController.deleteCardArray(cardsDelete);
			}
			break;
		case "X":		
			System.out.println("added to Del" + e.getActionCommand());
			cardsDelete.add(cards.get(Integer.parseInt(e.getActionCommand())));
			break;	
		}
	}
	
}
