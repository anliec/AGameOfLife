package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class of the main window which contains the whole game
 * @author team AGOL
 */
public class MainWindows extends JFrame {

    GraphicBoard BoardWidget;
    Button BtNextGeneration;
    String title;

    /**
     * default constructor
     * @author team AGOL
     */
    public MainWindows(){
        title=("A Game of Life");
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ///set UI
        BoardWidget = new GraphicBoard();
        BoardWidget.setPreferredSize(new Dimension(300, 300));
        add(BoardWidget, BorderLayout.CENTER);
        BtNextGeneration = new Button("End Turn");
        add(BtNextGeneration, BorderLayout.SOUTH);

        ///actionListener
        BtNextGeneration.setActionCommand("EndOfHumanTurn");
        BtNextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if("EndOfHumanTurn".equals(actionEvent.getActionCommand())){
                    BoardWidget.unselectCell();
                    BoardWidget.getBoard().endHumanPlayerTurn();
                    BoardWidget.repaint();
                }
                title="A Game of Life";
                title+=" - Player : "+BoardWidget.getBoard().getCurrentPlayer();
                setTitle(title);
            }
        });

        pack();
        setVisible(true);
    }
}
