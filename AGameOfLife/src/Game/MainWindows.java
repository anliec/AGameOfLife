package Game;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * class of the main window which contains the whole game
 * @author team AGOL
 */
public class MainWindows extends JFrame {

    protected GraphicBoard BoardWidget;
    protected Button BtNextGeneration;
    protected JMenuBar menuBar;
    protected String title;

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
        menuBar = new JMenuBar();
        initMenuBar();
        setJMenuBar(menuBar);
        

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
    
    private void initMenuBar() {
        JMenu fileMenu = new JMenu("Fichier"),
                toolsMenu = new JMenu("Outils"),
                helpMenu = new JMenu("A Propos");
        JMenuItem nouveauMenuItem = new JMenuItem("Nouvelle Simulation"),
               saveMenuItem = new JMenuItem("Sauvegarder"),
               closeMenuItem = new JMenuItem("Fermer"),
               optionsMenuItem = new JMenuItem("Options"),
               aboutMenuItem = new JMenuItem("?");
        closeMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        });
        aboutMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                AboutWindow about = new AboutWindow();
            }
        });
        fileMenu.add(nouveauMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(closeMenuItem);
        toolsMenu.add(optionsMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
    }
}
