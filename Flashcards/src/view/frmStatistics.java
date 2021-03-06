package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ProgramController;


public class frmStatistics extends JFrame implements ActionListener {

	private ProgramController pController;

	private JPanel grafic;
	private JPanel chooes;
	private JPanel info;
	private JButton btnCancle;
	private JComboBox drpStatitic;
	JLabel r;
	JLabel w;
	
	
	public frmStatistics(ProgramController pc) throws HeadlessException {
		super("Statistics");
		this.pController = pc;
		initComponents();
		bindListener();
		initGui();
	}

	private void initComponents() 
	{
		info=new JPanel();
		grafic=new JPanel();
		chooes =new JPanel();	
		btnCancle = new JButton("Cancle");
		w=new JLabel("");
		r=new JLabel("");
		String[]textes=new String[2];
		textes[0]="right wrong today";
		textes[1]="right wrong complete";
		drpStatitic=new JComboBox<>(textes);
	}
	
	private void initGui() {
		JPanel mainPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(20,50,20,20));
		
		setSize(500,500);
		initInfo(1);
		initGrafic(1);
		initChooes();
		mainPanel.add(chooes,BorderLayout.NORTH);
		mainPanel.add(grafic,BorderLayout.CENTER);
		mainPanel.add(btnCancle, BorderLayout.SOUTH);
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
	}
	
	private void initInfo(int type)
	{
		String result= pController.getStatitcData(type);
		String[] r2= result.split(",");
		int value1=Integer.parseInt(r2[0]) ;
		int value2=Integer.parseInt(r2[1]) ;
		int completValue=value1+value2;
		int h1=0;
		int h2=0;
		if(completValue!=0)
		{
			h1=value1*100/completValue;
			h2=value2*100/completValue;
		}
		
		r.setText("Grenn: right :"+h1+" %    ");
		w.setText("Red: right :"+h2+" %      ");
		info.setLayout(new FlowLayout());
		info.add(r);
		info.add(w); 
	}
	private void initGrafic(int type)
	{
		String r= pController.getStatitcData(type);
		String[] r2= r.split(",");
		int value1=Integer.parseInt(r2[0]) ;
		int value2=Integer.parseInt(r2[1]) ;
		int completValue=value1+value2;
		int h1=0;
		int h2=0;
		if(completValue!=0)
		{
			h1=value1*100/completValue;
			h2=value2*100/completValue;
		}	
		grafic.setLayout(null);
		grafic.add(new GraphicBar(h1,100,110-h1,java.awt.Color.GREEN));
		grafic.add(new GraphicBar(h2,200,110-h2,java.awt.Color.RED));
	}
	private void initChooes() 
	{

		chooes.setLayout(new FlowLayout(FlowLayout.CENTER,0,50));
 	
 		chooes.add(drpStatitic);
 		chooes.add(info);
 		
	}
	
	private void bindListener() 
	{
		btnCancle.addActionListener(this);
		drpStatitic.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == drpStatitic) 
		{
			grafic.removeAll();
			info.removeAll();
			int selectValue=drpStatitic.getSelectedIndex()+1;
			initGrafic(selectValue);
			initInfo(selectValue);
			grafic.repaint();
			info.repaint();
		}
		else
		{
			JButton btn = (JButton)e.getSource();
			switch(btn.getText())
			{
			case "Cancle":
				System.out.println("click - Cancle");
				pController.openHome();
				this.dispose();
				break;
			}
		}
	}

}
