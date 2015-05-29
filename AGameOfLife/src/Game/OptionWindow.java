package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class OptionWindow extends JFrame {
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

    JSpinner CellNumber;//number of cells
    JSpinner BoardHeight;//number of cells
    JSpinner BoardWidth;//number of cells
    JButton Exit;

    private JRadioButton[] PlayerOption = new JRadioButton[8];
    private ButtonGroup[] PlayerOptionGroup = new ButtonGroup[4];

    FlowLayout C;//ComboBox

    BoxLayout boxLayoutPrincipal;

    LinkedList<Boolean> Players;
    int numberOfPlayers;

    Options Op;
    Integer numberofcell;

    public OptionWindow() {
        setMinimumSize(new Dimension(275, 300));
        setMaximumSize(new Dimension(350, 400));
        setTitle("Options");
        boxLayoutPrincipal = new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS);
        getContentPane().setLayout(boxLayoutPrincipal);

        C = new FlowLayout(2);
        ComboPanel = new JPanel();
        ComboPanel.setLayout(C);
        /*
         * String[]PlayerNumber = {"1 player", "2 players", "3 players",
         * "4 players"};
         */
        ChoiceNumber = new JComboBox<>();
        Combo = new JLabel("Number of players : ");
        //ChoiceNumber.addItem("1 player");
        ChoiceNumber.addItem("2 players");
        ChoiceNumber.addItem("3 players");
        ChoiceNumber.addItem("4 players");
        ComboPanel.add(Combo);
        ComboPanel.add(ChoiceNumber);
        add(ComboPanel);
        ChoiceNumber.addActionListener(new ItemAction());

        for (int i = 0; i < PlayerOptionGroup.length; i++) {
            PlayerOptionGroup[i] = new ButtonGroup();
        }

        player1 = new JPanel();
        player1.setLayout(new BoxLayout(player1,BoxLayout.LINE_AXIS));
        Label1 = new JLabel("Player 1 : ");
        PlayerOption[0] = new JRadioButton("RL player");
        PlayerOption[1] = new JRadioButton("AI player");
        player1.add(Box.createHorizontalGlue());
        player1.add(Label1);
        player1.add(PlayerOption[0]);
        player1.add(PlayerOption[1]);
        player1.add(Box.createHorizontalGlue());
        add(player1);
        PlayerOptionGroup[0].add(PlayerOption[0]);
        PlayerOptionGroup[0].add(PlayerOption[1]);
        PlayerOption[0].setSelected(true);
        player1.setVisible(true);

        player2 = new JPanel();
        player2.setLayout(new BoxLayout(player2,BoxLayout.LINE_AXIS));
        Label2 = new JLabel("Player 2 : ");
        PlayerOption[2] = new JRadioButton("RL player");
        PlayerOption[3] = new JRadioButton("AI player");
        player2.add(Box.createHorizontalGlue());
        player2.add(Label2);
        player2.add(PlayerOption[2]);
        player2.add(PlayerOption[3]);
        player2.add(Box.createHorizontalGlue());
        add(player2);
        PlayerOptionGroup[1].add(PlayerOption[2]);
        PlayerOptionGroup[1].add(PlayerOption[3]);
        PlayerOption[3].setSelected(true);
        player2.setVisible(true);

        player3 = new JPanel();
        player3.setLayout(new BoxLayout(player3,BoxLayout.LINE_AXIS));
        Label3 = new JLabel("Player 3 : ");
        PlayerOption[4] = new JRadioButton("RL player");
        PlayerOption[5] = new JRadioButton("AI player");
        player3.add(Box.createHorizontalGlue());
        player3.add(Label3);
        player3.add(PlayerOption[4]);
        player3.add(PlayerOption[5]);
        player3.add(Box.createHorizontalGlue());
        add(player3);
        PlayerOptionGroup[2].add(PlayerOption[4]);
        PlayerOptionGroup[2].add(PlayerOption[5]);
        PlayerOption[5].setSelected(true);
        player3.setVisible(false);

        player4 = new JPanel();
        player4.setLayout(new BoxLayout(player4,BoxLayout.LINE_AXIS));
        Label4 = new JLabel("Player 4 : ");
        PlayerOption[6] = new JRadioButton("RL player");
        PlayerOption[7] = new JRadioButton("AI player");
        player4.add(Box.createHorizontalGlue());
        player4.add(Label4);
        player4.add(PlayerOption[6]);
        player4.add(PlayerOption[7]);
        player4.add(Box.createHorizontalGlue());
        add(player4);
        PlayerOptionGroup[3].add(PlayerOption[6]);
        PlayerOptionGroup[3].add(PlayerOption[7]);
        PlayerOption[7].setSelected(true);
        player4.setVisible(false);

        add(Box.createVerticalGlue());

        numberofcell = 0;
        SpinnerModel model1 = new SpinnerNumberModel(5, 5, 10, 1);
        CellNumber = new JSpinner(model1);
        JLabel label1 = new JLabel("Number of cells : ");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.LINE_AXIS));
        panel1.add(Box.createHorizontalGlue());
        panel1.add(label1);
        panel1.add(CellNumber);
        panel1.add(Box.createHorizontalGlue());
        add(panel1);

        SpinnerModel model2 = new SpinnerNumberModel(10, 10, 20, 1);
        SpinnerModel model3 = new SpinnerNumberModel(10, 10, 20, 1);
        BoardHeight = new JSpinner(model2);
        BoardWidth = new JSpinner(model3);
        JLabel labelBoardWdith = new JLabel("board width : ");
        JLabel labelBoardHeight = new JLabel("board height : ");
        JPanel panelBoardWdith = new JPanel();
        JPanel panelBoardHeight = new JPanel();
        panelBoardHeight.setLayout(new BoxLayout(panelBoardHeight,BoxLayout.LINE_AXIS));
        panelBoardWdith.setLayout(new BoxLayout(panelBoardWdith,BoxLayout.LINE_AXIS));
        panelBoardWdith.add(Box.createHorizontalGlue());
        panelBoardWdith.add(labelBoardWdith);
        panelBoardWdith.add(BoardWidth);
        panelBoardWdith.add(Box.createHorizontalGlue());
        add(panelBoardWdith);

        panelBoardHeight.add(Box.createHorizontalGlue());
        panelBoardHeight.add(labelBoardHeight);
        panelBoardHeight.add(BoardHeight);
        panelBoardHeight.add(Box.createHorizontalGlue());
        add(panelBoardHeight);

        add(Box.createVerticalGlue());

        OK = new JButton("OK");
        add(OK);
        OK.addActionListener(new ItemAction2());

        Op = new Options();
        numberOfPlayers = 2;

        pack();
        setVisible(false);
    }

    public class ItemAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (ChoiceNumber.getSelectedItem().equals("2 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(false);
                player4.setVisible(false);
                numberOfPlayers = 2;
            }
            if (ChoiceNumber.getSelectedItem().equals("3 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(true);
                player4.setVisible(false);
                numberOfPlayers = 3;
            }
            if (ChoiceNumber.getSelectedItem().equals("4 players")) {
                player1.setVisible(true);
                player2.setVisible(true);
                player3.setVisible(true);
                player4.setVisible(true);
                numberOfPlayers = 4;
            }
        }
    }//ActionListener

    public void BoutonOK() {

        Players = new LinkedList<>();
        Players.add(false); //dead team cell

        for (int i = 0; i < PlayerOption.length; i+=2) {
            if(numberOfPlayers > i/2){
                Players.add(!PlayerOption[i].isSelected());
            }
        }

        Op.setTeamsIA(Players);
        Op.setNumberOfCellBeginning((int) CellNumber.getValue());
        Op.setBoardWidth((int) BoardWidth.getValue());
        Op.setBoardHeight((int) BoardHeight.getValue());
        System.out.println("number of teams:"+(Players.size()-1));
        setVisible(false);
    }//boutonOK

    public class ItemAction2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            BoutonOK();
        }
    }//ItemAction2


    public Options getOptions(){
        return Op;
    }

}