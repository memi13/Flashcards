package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ProgramController;
import model.Card;

public class frmLearning extends JFrame implements ActionListener {

	private ProgramController pController;
	private JButton btnCheck;
	private JTextField txtAnswer;
	private JLabel lblMessage;
	private JLabel lblOrigin;
	private JLabel lblResult;
	
	private ArrayList<Card> cards;
	private int cardCount = -1;
	private int correctAnswers = 0;

	public frmLearning(ProgramController pc, ArrayList<Card> c) throws HeadlessException {
		super("Learning");
		this.pController = pc;
		if(c == null || c.isEmpty()) {
			System.out.println("No Cards in Array");
			c = new ArrayList<Card>();
		}
		this.cards = c;
		Collections.shuffle(cards);
		initComponents();
		bindListener();
		initGui();
	}
	
	private void initComponents() {
		btnCheck = new JButton("Check");
		txtAnswer = new JTextField("");
		lblMessage = new JLabel("");
		lblOrigin = new JLabel("");
		nextCard();
		lblResult = new JLabel("");
	}
	
	private void nextCard() {
		if(cardCount<cards.size()-1) {
			cardCount++;
			lblOrigin.setText(cards.get(cardCount).getsText());
		}else {
			//finish program
			showEndScreen();
		}
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel learningPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(150,50,150,50));
		
		setSize(500,500);
		
		learningPanel.setLayout(new GridLayout(5, 1, 10, 10));
		learningPanel.setBorder(new EmptyBorder(0,0,0,0));
		learningPanel.add(lblMessage);
		learningPanel.add(lblOrigin);
		learningPanel.add(txtAnswer);
		learningPanel.add(btnCheck);
		learningPanel.add(lblResult);
		mainPanel.add(learningPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
	}
	
	private void bindListener() {
		btnCheck.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText() == "Check"){
			if(cards.get(cardCount).getdText() == null) {
				System.out.println("EMPTY STRING");
			}else {
				if(pController.checkCardAnswer(cards.get(cardCount).getdText(), txtAnswer.getText())) {
					System.out.println("CORRECT");
					correctAnswers++;
					//send statistics correct
					pController.updateCardStatistics(cards.get(cardCount), true);
					nextCard();
					resetLabels();
				}else{
					System.out.println(cards.get(cardCount).getdText() + " - " + txtAnswer.getText());
					System.out.println("FALSE");
					//lblCorrrectAnswer.setText("Correct Answer: \n" + cards.get(cardCount).getdText());
					lblMessage.setText("Anser incorrect!");
					txtAnswer.setText("Correct Answer: \n" + cards.get(cardCount).getdText());
					btnCheck.setText("Next");
					//send statistics false
					pController.updateCardStatistics(cards.get(cardCount), false);
				}
			}
			
			System.out.println("Check - " + cardCount);
			
		}else if(btn.getText() == "Next") {
			btnCheck.setText("Check");
			resetLabels();
			nextCard();
		}
		
	}
	
	private void resetLabels() {
		this.lblMessage.setText("");
		this.lblResult.setText("");
		this.txtAnswer.setText("");
	}
	
	/**
	 * EndScreen after all Cards are learned
	 */
	private void showEndScreen() {
		JOptionPane.showMessageDialog(this, "Congratulations!\n You completed the learning phase! \n You answered " + correctAnswers + "/" + cards.size() + " correctly!", "Congratulations!", JOptionPane.PLAIN_MESSAGE);
		this.dispose();
		pController.openHome();
	}

}
