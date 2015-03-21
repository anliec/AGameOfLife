package Game;

import javax.swing.*;

/**
 * Created by nicolas on 21/03/15.
 */
public class MainWindows extends JFrame {

    GraphicBoard BoardWidget;

    public MainWindows(){
        BoardWidget = new GraphicBoard();
        add(BoardWidget);

        setSize(250, 250);
        setTitle("A Game Of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
