package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nicolas on 21/03/15.
 */
public class MainWindows extends JFrame {

    GraphicBoard BoardWidget;
    Button BtNextGeneration;

    public MainWindows(){
        setTitle("A Game Of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ///set UI
        BoardWidget = new GraphicBoard();
        BoardWidget.setPreferredSize(new Dimension(300, 300));
        add(BoardWidget, BorderLayout.CENTER);
        BtNextGeneration = new Button("Next Generation");
        add(BtNextGeneration, BorderLayout.SOUTH);

        ///actionListener
        BtNextGeneration.setActionCommand("ComputeNextGeneration");
        BtNextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if("ComputeNextGeneration".equals(actionEvent.getActionCommand())){
                    BoardWidget.computeNextGeneration();
                }
            }
        });

        pack();
        setVisible(true);
    }
}
