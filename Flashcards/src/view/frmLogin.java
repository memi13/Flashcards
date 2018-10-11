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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

import controller.ProgramController;

public class frmLogin extends JFrame implements ActionListener {

	private ProgramController pController;
	private JButton btnLogin;
	private JTextField txtUser;
	private JPasswordField txtPW;
	private JLabel lblError;

	public frmLogin(ProgramController pc) throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Login");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() {
		btnLogin = new JButton("Login");
		txtUser = new JTextField("Username");
		txtPW = new JPasswordField("Password");
		lblError = new JLabel("Password or Username not correct!");
		lblError.setVisible(false);
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel loginPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(120,50,200,50));
		
		setSize(500,500);
		
		mainPanel.add(lblError, BorderLayout.NORTH);
		loginPanel.setLayout(new GridLayout(3, 1, 10, 10));
		loginPanel.setBorder(new EmptyBorder(0,0,0,0));
		loginPanel.add(txtUser);
		loginPanel.add(txtPW);
		loginPanel.add(btnLogin);
		mainPanel.add(loginPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
	}
	
	private void bindListener() {
		btnLogin.addActionListener(this);
	}
	
	/**
	 * Shows an errormessage if login is not successful
	 */
	public void showError() {
		//show errormessage
		lblError.setVisible(true);
		System.out.println("LOGIN FORM - Errormessage");
	}
	
	public frmLogin(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public frmLogin(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public frmLogin(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText() == "Login") {
			System.out.println("click");
			if(pController.login(txtUser.getText(), new String(txtPW.getPassword()))) {
				this.dispose();
			}else {
				showError();
			}
		}
		
	}

}
