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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JLabel lblStatistics2;
	private JLabel lblStatistics3;
	
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
		lblStatistics2 = new JLabel("");
		lblStatistics3 = new JLabel("");
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
		mainPanel.setBorder(new EmptyBorder(150,50,150,50));
		
		setSize(500,500);
		
		learnPanel.setLayout(new GridLayout(6, 1, 10, 10));
		learnPanel.setBorder(new EmptyBorder(0,0,0,0));
		learnPanel.add(cbBox);
		learnPanel.add(lblStatistics);
		learnPanel.add(lblStatistics2);
		learnPanel.add(lblStatistics3);
		learnPanel.add(btnOk);
		learnPanel.add(btnCancle);
		mainPanel.add(learnPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
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
				int cbIndex = cbBox.getSelectedIndex();
				setStatisticsLable(cbIndex);
				System.out.println(cbIndex);
				if(cbIndex == 0) {
					if(pController.loadCards(-1) == null) {
						showError();
						return;
					}else {
						if(pController.loadCards(-1).size()<=0) {
							showError();
							return;
						}else {
							if(pController.loadCards(cbIndex).size()<=0) {
								showError();
								return;
							}
						}
					}	
				pController.setBoxId(cbIndex);
				pController.openLearning();
				this.dispose();
				}
			}else if(btn.getText() == "Cancle") {
				pController.openHome();
				this.dispose();
			}
		}
	}
	
	/**
	 * Sets the Statistics on 3 different Labels
	 * @param index Row of the Label (0-2)
	 */
	private void setStatisticsLable(int index) {
		System.out.println(index);
		String s1 = "";
		String s2 = "";
		String s3 = "";
		
		if(index != 0 && index < 6) {
			System.out.println(index);
			s1 = pController.loadBoxStatistics(index, 0);
			s2 = pController.loadBoxStatistics(index, 1);
			s3 = pController.loadBoxStatistics(index, 2);
		}else {
			System.out.println(-1);
			s1 = pController.loadBoxStatistics(-1, 0);
			s2 = pController.loadBoxStatistics(-1, 1);
			s3 = pController.loadBoxStatistics(-1, 2);
		}
		
		lblStatistics.setText(s1);
		lblStatistics2.setText(s2);
		lblStatistics3.setText(s3);
	}
	
	private void showError() {
		JOptionPane.showMessageDialog(this, "There are no Cards to Learn Today!", "No Cards today!", JOptionPane.PLAIN_MESSAGE);
		this.dispose();
		pController.openHome();
	}

}
