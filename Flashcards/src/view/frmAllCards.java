package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

import controller.ProgramController;

public class frmAllCards extends JFrame implements ActionListener {

	private ProgramController pController;
	
	private JButton btnCancle;
	private JTextField[] txtFields;
	
	private int cards;
	

	public frmAllCards(ProgramController pc) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Modify Cards");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() {
		btnCancle = new JButton("Cancle");
		txtFields = new JTextField[6];
		for(int i = 0; i < txtFields.length; i++) {
			System.out.println("TEXTFIELD Initialized");
			txtFields[i] = new JTextField("-");
			
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
	
		//Grid Control
		int numRows = 0;
		if((txtFields.length % 2) == 0) {
			
		}
		GridLayout dynGrid = new GridLayout(txtFields.length/2, 2);
		tablePanel.setLayout(dynGrid);
		tablePanel.setBorder(new EmptyBorder(0,0,0,0));
		this.validate();
		for(int i = 0; i < txtFields.length; i++) {
			System.out.println("TEXTFIELD");
			tablePanel.add(txtFields[i]);
			
		}
		
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void bindListener() {
		btnCancle.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText() == "Cancle") {
			System.out.println("click - Cancle");
			this.dispose();
		}
		
	}

}
