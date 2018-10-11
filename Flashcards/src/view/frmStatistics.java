package view;

import java.awt.BorderLayout;
import java.awt.Component;
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


public class frmStatistics extends JFrame implements ActionListener {

	private ProgramController pController;

	private JPanel grafic;
	private JPanel chooes;
	private JButton btnCancle;
	
	public frmStatistics(ProgramController pc) throws HeadlessException {
		super("Statistics");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() 
	{
		grafic=new JPanel();
		chooes =new JPanel();
		btnCancle = new JButton("Cancle");
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(50,50,20,50));
		
		setSize(500,500);
		initGrafic();
		initChooes();
		mainPanel.add(chooes,BorderLayout.NORTH);
		mainPanel.add(grafic,BorderLayout.CENTER);
		mainPanel.add(btnCancle, BorderLayout.SOUTH);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	private void initGrafic()
	{
		int h1=80;
		int h2=100;
		grafic.setLayout(null);
		grafic.add(new GraphicBar(h1,100,110-h1,java.awt.Color.RED));
		grafic.add(new GraphicBar(h2,200,110-h2,java.awt.Color.BLUE));
	}
	private void initChooes()
	{
		String[]textes=new String[2];
		textes[0]="right wrong today";
		textes[1]="right wrong complete";
		chooes.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
 		JComboBox drpStatitics= new JComboBox(textes);
 		chooes.add(drpStatitics);
 		
	}
	private void bindListener() 
	{
		btnCancle.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		JButton btn = (JButton)e.getSource();
		switch(btn.getText()){
		case "Cancle":
			System.out.println("click - Cancle");
			pController.openHome();
			this.dispose();
			break;
		}
	}

}
