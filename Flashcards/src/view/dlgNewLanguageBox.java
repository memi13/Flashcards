/**
 * 
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ProgramController;

/**
 * Dialog to create a new LanguageBox
 * @author linus
 *
 */
public class dlgNewLanguageBox extends JDialog implements ActionListener{
	
	private ProgramController pController;
	private JButton btnCancle;
	private JButton btnOk;
	private JTextField txtName;
	
	/** Constructor for custom Dialog
	 * @param owner Parent JFrame
	 * @param pc ProgramController
	 */
	public dlgNewLanguageBox(JFrame owner, ProgramController pc) {
		super(owner, "New Language Box");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	/**
	 * Initialize Components
	 */
	private void initComponents() {
		// TODO Auto-generated method stub
		btnOk = new JButton("Ok");
		btnCancle = new JButton("Cancle");
		txtName = new JTextField("example: Englisch <-> Deutsch");
	}
	
	/**
	 * Initialize GUI
	 */
	private void initGui() {
		JPanel mainPanel = new JPanel();
		JPanel languageboxPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(150,50,200,50));
		
		setSize(500,500);
		
		languageboxPanel.setLayout(new GridLayout(3, 1));
		languageboxPanel.setBorder(new EmptyBorder(0,0,0,0));
		languageboxPanel.add(txtName);
		languageboxPanel.add(btnOk);
		languageboxPanel.add(btnCancle);
		mainPanel.add(languageboxPanel, BorderLayout.CENTER);
		this.add(mainPanel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
	}

	/**
	 * Bind Listeners
	 */
	private void bindListener() {
		// TODO Auto-generated method stub
		btnOk.addActionListener(this);
		btnCancle.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton)e.getSource();
		if(btn.getText() == "Ok") {
			//save and back to home
			if(txtName.getText() != "") {
				pController.createNewLanguageBox(txtName.getText());
				System.out.println("Created new LangugeBox: " + txtName.getText());
			}else {
				//Errorhandling
			}
			this.dispose();
			System.out.println("click - OK - Save and back to Home");
		}else {
			//back to home without save
			this.dispose();
			System.out.println("click - Cancle - Back Home without save");
		}
	}

	

}
