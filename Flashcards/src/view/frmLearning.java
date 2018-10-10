package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

import controller.ProgramController;
import model.Card;

public class frmLearning extends JFrame implements ActionListener {

	private ProgramController pController;
	private JButton btnCheck;
	private JTextField txtAnswer;
	private JLabel lblCorrrectAnswer;
	private JLabel lblOrigin;
	private JLabel lblResult;
	
	private ArrayList<Card> cards;
	private int cardCount = 0;

	public frmLearning(ProgramController pc, ArrayList<Card> c) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Learning");
		this.pController = pc;
		this.cards = c;
		initComponents();
		bindListener();
		initGui();
	}
	
	private void initComponents() {
		btnCheck = new JButton("Check");
		txtAnswer = new JTextField("Enter Answer");
		lblCorrrectAnswer = new JLabel("-Answer-");
		lblOrigin = new JLabel("Origin Word");
		nextCard();
		lblResult = new JLabel("-Result-");
	}
	
	private void nextCard() {
		if(cardCount<cards.size()-1) {
			cardCount++;
			lblOrigin.setText(cards.get(cardCount).getsText());
		}else {
			//finish programm
		}
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel learningPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(150,50,200,50));
		
		setSize(500,500);
		
		learningPanel.setLayout(new GridLayout(5, 1));
		learningPanel.setBorder(new EmptyBorder(0,0,0,0));
		learningPanel.add(lblOrigin);
		learningPanel.add(txtAnswer);
		learningPanel.add(lblCorrrectAnswer);
		learningPanel.add(btnCheck);
		learningPanel.add(lblResult);
		mainPanel.add(learningPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void bindListener() {
		btnCheck.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText() == "Check"){
			if(pController.checkCardAnswer(cards.get(cardCount).getdText(), txtAnswer.getText())) {
				System.out.println("CORRECT");
			}else {
				System.out.println("FALSE");
			}
			nextCard();
			System.out.println("Check - " + cardCount);
			
		}
		
	}

}
