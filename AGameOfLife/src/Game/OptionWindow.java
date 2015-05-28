package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class OptionWindow extends JFrame {
    protected JPanel ComboPanel;
    protected JPanel player1;
    protected JPanel player2;
    protected JPanel player3;
    protected JPanel player4;

    protected JButton OK;

    private JLabel Label1;
    private JLabel Label2;
    private JLabel Label3;
    private JLabel Label4;
    private JLabel Combo;

    /*String[]PlayerNumber;*/
    protected JComboBox<String> ChoiceNumber;

    protected JTextField round;//number of rounds
    protected JTextField cell;//number of cells
    protected JButton Exit;

    private JCheckBox[] PlayerOption = new JCheckBox[8];

    protected FlowLayout C;//ComboBox
    protected FlowLayout P1;
    protected FlowLayout P2;
    protected FlowLayout P3;
    protected FlowLayout P4;

    protected GridLayout gridLayoutPrincipal;

    protected LinkedList<Boolean> Players;

    protected Options options;

    public OptionWindow() {
        setSize(new Dimension(1200, 900));
        setTitle("Options");
        gridLayoutPrincipal = new GridLayout(6, 1);
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
        PlayerOption[2] = new JCheckBox("RL player");
        PlayerOption[3] = new JCheckBox("IA player");
        player2.add(Label2);
        player2.add(PlayerOption[2]);
        player2.add(PlayerOption[3]);
        add(player2);
        player2.setVisible(false);

        player3 = new JPanel();
        P3 = new FlowLayout(4);
        player3.setLayout(P3);
        Label3 = new JLabel("Player 3 : ");
        PlayerOption[4] = new JCheckBox("RL player");
        PlayerOption[5] = new JCheckBox("IA player");
        player3.add(Label3);
        player3.add(PlayerOption[4]);
        player3.add(PlayerOption[5]);
        add(player3);
        player3.setVisible(false);

        player4 = new JPanel();
        P4 = new FlowLayout(4);
        player4.setLayout(P4);
        Label4 = new JLabel("Player 4 : ");
        PlayerOption[6] = new JCheckBox("RL player");
        PlayerOption[7] = new JCheckBox("IA player");
        player4.add(Label4);
        player4.add(PlayerOption[6]);
        player4.add(PlayerOption[7]);
        add(player4);
        player4.setVisible(false);

        for (int i = 0; i < PlayerOption.length; i++) {
            PlayerOption[i].addActionListener(new ItemAction3());
        }


        OK = new JButton("OK");
        add(OK);
        OK.addItemListener(new ItemState());
        OK.addActionListener(new ItemAction2());

        Players = new LinkedList<Boolean>();
        options = new Options();

        pack();
        setVisible(false);
    }

    public class ItemAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (ChoiceNumber.getSelectedItem().equals("1 player")) {
                player1.setVisible(true);
                player2.setVisible(false);
                player3.setVisible(false);
                player4.setVisible(false);
            }
            if (ChoiceNumber.getSelectedItem().equals("2 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(false);
                player4.setVisible(false);
            }
            if (ChoiceNumber.getSelectedItem().equals("3 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(true);
                player4.setVisible(false);
            }
            if (ChoiceNumber.getSelectedItem().equals("4 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(true);
                player4.setVisible(true);
            }
        }
    }//ActionListener

    public class ItemAction2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (PlayerOption[0].isSelected() == true) {
                Players.add(true);
                PlayerOption[1].setSelected(false);
                //System.out.println("true1");
            }
            if (PlayerOption[1].isSelected() == true) {
                Players.add(false);
                PlayerOption[0].setSelected(false);
                //System.out.println("false1");
            }
            if (PlayerOption[2].isSelected() == true) {
                Players.add(true);
                PlayerOption[3].setSelected(false);
                //System.out.println("true2");
            }
            if (PlayerOption[3].isSelected() == true) {
                Players.add(false);
                PlayerOption[2].setSelected(false);
                //System.out.println("false2");
            }
            if (PlayerOption[4].isSelected() == true) {
                Players.add(true);
                PlayerOption[5].setSelected(false);
                //System.out.println("true3");
            }
            if (PlayerOption[5].isSelected() == true) {
                Players.add(false);
                PlayerOption[4].setSelected(false);
                //System.out.println("false3");
            }
            if (PlayerOption[6].isSelected() == true) {
                Players.add(true);
                PlayerOption[7].setSelected(false);
                //System.out.println("true4");
            }
            if (PlayerOption[7].isSelected() == true) {
                Players.add(false);
                PlayerOption[6].setSelected(false);
                //System.out.println("false4");
            }
            setVisible(false);
        }
    }//ItemAction2

    public class ItemState implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            System.out.println(e.getItem());
        }
    }//ItemListener

    public class ItemAction3 implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //true = real player ; false = IA player
            if (PlayerOption[0].isSelected() == true) {
                PlayerOption[1].setSelected(false);
            }
            if (PlayerOption[1].isSelected() == true) {
                PlayerOption[0].setSelected(false);
            }
            if (PlayerOption[2].isSelected() == true) {
                PlayerOption[3].setSelected(false);
            }
            if (PlayerOption[3].isSelected() == true) {
                PlayerOption[2].setSelected(false);
            }
            if (PlayerOption[4].isSelected() == true) {
                PlayerOption[5].setSelected(false);
            }
            if (PlayerOption[5].isSelected() == true) {
                PlayerOption[4].setSelected(false);
            }
            if (PlayerOption[6].isSelected() == true) {
                PlayerOption[7].setSelected(false);
            }
            if (PlayerOption[7].isSelected() == true) {
                PlayerOption[6].setSelected(false);
            }
        }
    }

    public Options getOptions(){
        return options;
    }

    //test
    /*public static void main(String args[]) {

        OptionWindow maFrame = new OptionWindow();

    }*/

}