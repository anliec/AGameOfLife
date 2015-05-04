package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionWindow extends JFrame{
        JPanel ComboPanel;
        JPanel player1;
    JPanel player2;
    JPanel player3;
    JPanel player4;
    
    JButton OK;
    
    private JLabel Label1;
    private JLabel Label2;
    private JLabel Label3;
    private JLabel Label4;
    private JLabel Combo;
    
    /*String[]PlayerNumber;*/
    JComboBox<String> ChoiceNumber;
    
    JTextField round;//number of rounds
    JTextField cell;//number of cells
    JButton Exit;
    
    private JCheckBox[] PlayerOption = new JCheckBox[9];
    
    FlowLayout C;//ComboBox
    FlowLayout P1;
    FlowLayout P2;
    FlowLayout P3;
    FlowLayout P4;
    
    GridLayout gridLayoutPrincipal;
    
    /*
     * JButton jButton_AC; JButton jButton_C; JButton jButton_Egal; JButton
     * jButton_Div; JButton jButton_Mult; JButton jButton_signe; JButton
     * jButton_add; JButton jButton_moins; JButton boutonp; FlowLayout
     * flowLayoutResultat; GridLayout gridLayoutPrincipal; GridLayout
     * GridLayoutAc_C_Egal; GridLayout GridLayout7_8_9; GridLayout
     * GridLayout4_5_6; GridLayout GridLayout1_2_3; GridLayout
     * GridLayout0_signe; JPanel jPanelAC_C_Egal; JPanel jPanel1_2_3; JPanel
     * jPanel4_5_6; JPanel jPanel7_8_9; JPanel jPanel0_signe; JPanel
     * jPanel_Resultat;
     */

    public OptionWindow() {
        setSize(new Dimension(1200, 900));
        setTitle("Options");
        gridLayoutPrincipal = new GridLayout(8, 1);
        getContentPane().setLayout(gridLayoutPrincipal);

        C = new FlowLayout(2);
        ComboPanel = new JPanel();
        ComboPanel.setLayout(C);
        /*
         * String[]PlayerNumber = {"1 player", "2 players", "3 players",
         * "4 players"};
         */
        ChoiceNumber = new JComboBox<>();
        Combo = new JLabel("Number of players : ");
        ChoiceNumber.addItem("1 player");
        ChoiceNumber.addItem("2 players");
        ChoiceNumber.addItem("3 players");
        ChoiceNumber.addItem("4 players");
        ComboPanel.add(Combo);
        ComboPanel.add(ChoiceNumber);
        add(ComboPanel);
        ChoiceNumber.addItemListener(new ItemState());
        ChoiceNumber.addActionListener(new ItemAction());

        player1 = new JPanel();
        P1 = new FlowLayout(4);
        player1.setLayout(P1);
        Label1 = new JLabel("Player 1 : ");
        PlayerOption[0] = new JCheckBox("RL player");
        PlayerOption[1] = new JCheckBox("IA player");
        player1.add(Label1);
        player1.add(PlayerOption[0]);
        player1.add(PlayerOption[1]);
        /* player1.add(PlayerOption[2]); */
        add(player1);
        player1.setVisible(true);

        player2 = new JPanel();
        P2 = new FlowLayout(4);
        player2.setLayout(P2);
        Label2 = new JLabel("Player 2 : ");
        PlayerOption[3] = new JCheckBox("RL player");
        PlayerOption[4] = new JCheckBox("IA player");
        player2.add(Label2);
        player2.add(PlayerOption[3]);
        player2.add(PlayerOption[4]);
        add(player2);
        player2.setVisible(false);

        player3 = new JPanel();
        P3 = new FlowLayout(4);
        player3.setLayout(P3);
        Label3 = new JLabel("Player 3 : ");
        PlayerOption[5] = new JCheckBox("RL player");
        PlayerOption[6] = new JCheckBox("IA player");
        player3.add(Label3);
        player3.add(PlayerOption[5]);
        player3.add(PlayerOption[6]);
        add(player3);
        player3.setVisible(false);

        player4 = new JPanel();
        P4 = new FlowLayout(4);
        player4.setLayout(P4);
        Label4 = new JLabel("Player 4 : ");
        PlayerOption[7] = new JCheckBox("RL player");
        PlayerOption[8] = new JCheckBox("IA player");
        player4.add(Label4);
        player4.add(PlayerOption[7]);
        player4.add(PlayerOption[8]);
        add(player4);
        player4.setVisible(false);

        OK = new JButton("OK");
        add(OK);
        OK.addItemListener(new ItemState());
        OK.addActionListener(new ItemAction2());

        pack();
        setVisible(true);
    }
        
        public class ItemAction implements ActionListener {
                public void actionPerformed(ActionEvent e){
                        if (ChoiceNumber.getSelectedItem().equals("1 player")){
                                player1.setVisible(true);
                                player2.setVisible(false);
                                player3.setVisible(false);
                                player4.setVisible(false);
                        }
                        if (ChoiceNumber.getSelectedItem().equals("2 players")){
                                player1.setVisible(true);
                                player2.setVisible(true);
                                player3.setVisible(false);
                                player4.setVisible(false);
                        }
                        if (ChoiceNumber.getSelectedItem().equals("3 players")){
                                player1.setVisible(true);
                                player2.setVisible(true);
                                player3.setVisible(true);
                                player4.setVisible(false);
                        }
                        if (ChoiceNumber.getSelectedItem().equals("4 players")){
                                player1.setVisible(true);
                                player2.setVisible(true);
                                player3.setVisible(true);
                                player4.setVisible(true);
                        }
                }
        }//ActionListener
        
        public class ItemAction2 implements ActionListener {
                public void actionPerformed(ActionEvent e){
                        
                System.exit(0);
                        
                }
        }//ItemAction2
        
        public class ItemState implements ItemListener{
                public void itemStateChanged(ItemEvent e){
                        System.out.println(e.getItem());
                }
        }//ItemListener
                
        
        //test
         public static void main (String args[]) {
         
         OptionWindow maFrame= new OptionWindow();
                 
         }
        
}