package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

import controller.ProgramController;
import model.LanguageBox;

public class frmHome extends JFrame implements ActionListener {

	private ProgramController pController;
	private JButton btnLearningOverview;
	private JButton btnSettings;
	private JButton btnNewLanguageBox;
	private JButton btnAllCards;
	private JButton btnStatistics;
	private JComboBox<LanguageBox> cbLanguageBox;
	
	public frmHome(ProgramController pc) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("HOME");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() {
		btnLearningOverview = new JButton("Learning");
		btnSettings = new JButton("Settings");
		btnNewLanguageBox = new JButton("New Language Box");
		btnAllCards = new JButton("Modify Cards");
		btnStatistics = new JButton("Statistics");
		cbLanguageBox = new JComboBox(pController.loadLanguageBoxes().toArray());
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel tempButtonPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(50,50,50,50));
		
		setSize(500,500);
		
		tempButtonPanel.setLayout(new GridLayout(6, 1));
		tempButtonPanel.setBorder(new EmptyBorder(0,0,0,0));
		tempButtonPanel.add(cbLanguageBox);
		tempButtonPanel.add(btnLearningOverview);
		tempButtonPanel.add(btnSettings);
		tempButtonPanel.add(btnNewLanguageBox);
		tempButtonPanel.add(btnAllCards);
		tempButtonPanel.add(btnStatistics);
		mainPanel.add(tempButtonPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void bindListener() {
		btnLearningOverview.addActionListener(this);
		btnSettings.addActionListener(this);
		btnNewLanguageBox.addActionListener(this);
		btnAllCards.addActionListener(this);
		btnStatistics.addActionListener(this);
	}
	
	public frmHome(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public frmHome(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public frmHome(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		switch(btn.getText()) {
		case "Learning":
			System.out.println("click - " + btn.getText());
			pController.openOverview();
			this.dispose();
			break;
		case "Settings":
			System.out.println("click - " + btn.getText());
			pController.openSettings();
			this.dispose();
			break;
		case "New Language Box":
			System.out.println("click - " + btn.getText());
			pController.openNewLanguageBox(this);
			break;
		case "Modify Cards":
			System.out.println("click - " + btn.getText());
			pController.openAllCards();
			this.dispose();
			break;
		case "Statistics":
			System.out.println("click - " + btn.getText());
			pController.openStatistics();
			this.dispose();
			break;
		default:
			System.out.println("Button not found");
		}
	}

}
