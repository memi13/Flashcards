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

public class frmOverview extends JFrame implements ActionListener {

	private ProgramController pController;
	private int languageBox;
	private int box;
	private JButton btnOk;
	private JButton btnCancle;
	private JComboBox<String> cbBox ;
	private JLabel lblStatistics;
	
	private String[] boxes;

	/**
	 * Constructor of frmOverview
	 * @param pc ProgramController
	 * @param languageBox Selected LanguageBox ID
	 * @throws HeadlessException
	 */
	public frmOverview(ProgramController pc, int languageBox) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Learning Overview");
		this.pController = pc;
		this.languageBox = languageBox;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() {
		initBoxes();
		btnOk = new JButton("Ok");
		btnCancle = new JButton("Cancle");
		lblStatistics = new JLabel("Select a Box");
		cbBox = new JComboBox<>(boxes);
		setStatisticsLable(0);
	}
	
	private void initBoxes() {
		boxes = new String[6];
		boxes[0] = "All";
		boxes[1] = "Box 1";
		boxes[2] = "Box 2";
		boxes[3] = "Box 3";
		boxes[4] = "Box 4";
		boxes[5] = "Box 5";
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel learnPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(150,50,200,50));
		
		setSize(500,500);
		
		learnPanel.setLayout(new GridLayout(4, 1));
		learnPanel.setBorder(new EmptyBorder(0,0,0,0));
		learnPanel.add(cbBox);
		learnPanel.add(lblStatistics);
		learnPanel.add(btnOk);
		learnPanel.add(btnCancle);
		mainPanel.add(learnPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void bindListener() {
		btnOk.addActionListener(this);
		btnCancle.addActionListener(this);
		cbBox.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cbBox) {
			setStatisticsLable(cbBox.getSelectedIndex());
		}else {
			JButton btn = (JButton)e.getSource();
			System.out.println(btn.getText());
			if(btn.getText() == "Ok") {
				setStatisticsLable(cbBox.getSelectedIndex());
				pController.openLearning(cbBox.getSelectedIndex());
				this.dispose();
			}else if(btn.getText() == "Cancle") {
				pController.openHome();
				this.dispose();
			}
		}
			
	}
	
	private void setStatisticsLable(int index) {
		if(index != 0) {
			System.out.println(index);
			lblStatistics.setText("Boxnummer: " + index);
		}else {
			System.out.println(-1);
			lblStatistics.setText("Boxnummer: Alle (-1)");
		}
	}

}
