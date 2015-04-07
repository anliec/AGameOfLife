package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionWindow extends JFrame{
	JPanel player1;
    JPanel player2;
    JPanel player3;
    JPanel player4;
    
    private JLabel Label1;
    private JLabel Label2;
    private JLabel Label3;
    private JLabel Label4;
    
    JTextField round;//number of rounds
    JTextField cell;//number of cells
    JButton Exit;
    
    private JCheckBox[] PlayerOption = new JCheckBox[12];
    
    FlowLayout P1;
    FlowLayout P2;
    FlowLayout P3;
    FlowLayout P4;
    
    GridLayout gridLayoutPrincipal;
    
    /*JButton jButton_AC;
    JButton jButton_C;
    JButton jButton_Egal;
    JButton jButton_Div;
    JButton jButton_Mult;
    JButton jButton_signe;
    JButton jButton_add;
    JButton jButton_moins;
    JButton boutonp;
    FlowLayout flowLayoutResultat;
    GridLayout gridLayoutPrincipal;
    GridLayout GridLayoutAc_C_Egal;
    GridLayout GridLayout7_8_9;
    GridLayout GridLayout4_5_6;
    GridLayout GridLayout1_2_3;
    GridLayout GridLayout0_signe;
    JPanel jPanelAC_C_Egal;
    JPanel jPanel1_2_3;
    JPanel jPanel4_5_6;
    JPanel jPanel7_8_9;
    JPanel jPanel0_signe;
    JPanel jPanel_Resultat;*/
	
	public OptionWindow(){
		setSize(new Dimension(1200,900));
		setTitle("Options");
		gridLayoutPrincipal = new GridLayout(7,1);
		getContentPane().setLayout(gridLayoutPrincipal);
		
		player1 = new JPanel();
		P1 = new FlowLayout(4);
		player1.setLayout(P1);
		Label1 = new JLabel ("Player 1 : ");
		PlayerOption[0]= new JCheckBox("RL player");
		PlayerOption[1]= new JCheckBox("IA player");
		PlayerOption[2]= new JCheckBox();
		player1.add(PlayerOption[0]);
		player1.add(PlayerOption[1]);
		player1.add(PlayerOption[2]);
		add(player1);
		
		player2 = new JPanel();
		P2 = new FlowLayout(4);
		player2.setLayout(P2);
		Label1 = new JLabel ("Player 2 : ");
		PlayerOption[3]= new JCheckBox("RL player");
		PlayerOption[4]= new JCheckBox("IA player");
		PlayerOption[5]= new JCheckBox("No player");
		player2.add(PlayerOption[3]);
		player2.add(PlayerOption[4]);
		player2.add(PlayerOption[5]);
		add(player2);
		
		player3 = new JPanel();
		P3 = new FlowLayout(4);
		player3.setLayout(P3);
		Label1 = new JLabel ("Player 3 : ");
		PlayerOption[6]= new JCheckBox("RL player");
		PlayerOption[7]= new JCheckBox("IA player");
		PlayerOption[8]= new JCheckBox("No player");
		player3.add(PlayerOption[6]);
		player3.add(PlayerOption[7]);
		player3.add(PlayerOption[8]);
		add(player3);
		
		player4 = new JPanel();
		P4 = new FlowLayout(4);
		player4.setLayout(P4);
		Label1 = new JLabel ("Player 4 : ");
		PlayerOption[9]= new JCheckBox("RL player");
		PlayerOption[10]= new JCheckBox("IA player");
		PlayerOption[11]= new JCheckBox("No player");
		player4.add(PlayerOption[9]);
		player4.add(PlayerOption[10]);
		player4.add(PlayerOption[11]);
		add(player4);
		
		pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	//test
	 public static void main (String args[]) {
         
         OptionWindow maFrame= new OptionWindow();
                 
	 }
	
}
