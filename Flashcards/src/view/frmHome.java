package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	private JLabel lblTitle;
	
	//Menu
	private JMenuBar menuBar;
	private JMenu menuSettings;
	private JMenuItem menuModify;
	private JMenuItem menuNewLanguageBox;
	
	
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
		lblTitle = new JLabel("Select a Language Box:");
		ArrayList<LanguageBox> alLanguageBox = pController.loadLanguageBoxes();
		if(alLanguageBox == null) {
			cbLanguageBox = new JComboBox<>();
		}else {
			cbLanguageBox = new JComboBox(alLanguageBox.toArray());
		}
		
		menuBar = new JMenuBar();
		menuSettings = new JMenu("Settings");
		menuModify = new JMenuItem("Modify Cards");
		menuNewLanguageBox = new JMenuItem("New Language Box");
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(120,50,120,50));
		
		setSize(500,500);
		
		buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
		buttonPanel.setBorder(new EmptyBorder(0,0,0,0));
		buttonPanel.add(lblTitle);
		buttonPanel.add(cbLanguageBox);
		buttonPanel.add(btnLearningOverview);
		//tempButtonPanel.add(btnSettings);
		//tempButtonPanel.add(btnNewLanguageBox);
		//tempButtonPanel.add(btnAllCards);
		buttonPanel.add(btnStatistics);
		
		menuBar.add(menuSettings);
		menuSettings.add(menuModify);
		menuSettings.add(menuNewLanguageBox);
		this.setJMenuBar(menuBar);
		
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
	}
	
	private void bindListener() {
		btnLearningOverview.addActionListener(this);
		btnSettings.addActionListener(this);
		btnNewLanguageBox.addActionListener(this);
		btnAllCards.addActionListener(this);
		btnStatistics.addActionListener(this);
		menuModify.addActionListener(this);
		menuNewLanguageBox.addActionListener(this);
	}

	private void reopenHome() {
		this.dispose();
		frmHome frmH = new frmHome(pController);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton btn = (JButton)e.getSource();
			pController.setLanguageBox((LanguageBox)cbLanguageBox.getSelectedItem());
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
		
		if(e.getSource() == menuModify) {
		//THE DISPOSE FUNCTION DOES NOT WORK?
			openFormAllCards();
		}else if(e.getSource() == menuNewLanguageBox) {
			System.out.println("click - Menu New LanguageBox");
			pController.openNewLanguageBox(this);
			//cbLanguageBox = new JComboBox(pController.loadLanguageBoxes().toArray());
			//cbLanguageBox.repaint();
			reopenHome();
		}
	}

	private void openFormAllCards() {
		System.out.println("click");
		pController.openAllCards();
		//this
		this.dispose();
	}
	
}
