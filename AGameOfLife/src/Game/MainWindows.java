package Game;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * class of the main window which contains the whole game
 * @author team AGOL
 */
public class MainWindows extends JFrame {

    protected Options gameOptions;
    protected GraphicBoard boardWidget;
    protected GraphicBoardIni iniBoard;
    protected Button btNextGeneration;
    protected JMenuBar menuBar;
    protected String title;

    protected JFileChooser fc;

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
        gameOptions = new Options();
        menuBar = new JMenuBar();
        initMenuBar();
        setJMenuBar(menuBar);
        //load file selection window
        fc = new JFileChooser("Boards");

        setPlayMode(new Board(gameOptions));
        btNextGeneration.setEnabled(false);

        pack();
        setVisible(true);
    }
    
    private void initMenuBar() {
        JMenu fileMenu = new JMenu("Fichier"),
                toolsMenu = new JMenu("Outils"),
                helpMenu = new JMenu("A Propos");
        JMenuItem newMenuItem = new JMenuItem("Nouvelle Simulation"),
                openMenuItem = new JMenuItem("Ouvrir"),
                saveMenuItem = new JMenuItem("Sauvegarder"),
                closeMenuItem = new JMenuItem("Fermer"),
                optionsMenuItem = new JMenuItem("Options"),
                aboutMenuItem = new JMenuItem("?");
        closeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                initialisationOfTheBoard();
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openFileMenu();
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveFileMenu();
            }
        });
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AboutWindow about = new AboutWindow();
            }
        });
        optionsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                OptionWindow options = new OptionWindow();
            }
        });
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(closeMenuItem);
        toolsMenu.add(optionsMenuItem);
        helpMenu.add(aboutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
    }

    public void openFileMenu(){
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            setPlayMode(new Board(fc.getSelectedFile().getPath(), ' ',gameOptions));
            //boardWidget.setBoardFromFile(fc.getSelectedFile().getPath(), ' ');
        }
    }

    public void saveFileMenu(){
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            boardWidget.saveBoardToFile(fc.getSelectedFile().getPath(), ' ');
        }
    }

    public void initialisationOfTheBoard(){
        System.out.println("init board");
        //board initialisation:
        Board board = new Board(gameOptions);
        Team[] teams = new Team[gameOptions.getTeamsIA().size()];
        teams[0] = board.getTeam(0);//get the dead cell team witch is correctly set
        for (int i = 1; i < teams.length; i++) {
            teams[i] = new Team(gameOptions.getTeamsIA().get(i),board);
        }
        board.setTeams(teams);

        //setup of the teams cells:
        boardIni(1,board);

    }

    private void boardIni(int team, Board board){
        if(team < gameOptions.getTeamsIA().size()){
            if(board.getTeam(team).isIA()){
                BoardIniIA IAini = new BoardIniIA(board,team,gameOptions.getNumberOfCellBeginning());
                IAini.iniBoard();
                board = IAini.getBoard();
                boardIni(team + 1, board);
            }
            else{
                try{
                    remove(boardWidget);
                    remove(btNextGeneration);
                }
                catch (Exception e){ // if boardWidget or btNextGeneration doesn't exist...
                }
                iniBoard = new GraphicBoardIni(board,team,gameOptions.getNumberOfCellBeginning());
                boardWidget = iniBoard;
                boardWidget.setPreferredSize(new Dimension(300, 300));
                add(boardWidget, BorderLayout.CENTER);
                btNextGeneration = new Button("OK");
                ///actionListener
                btNextGeneration.setActionCommand("EndOfHumanPlacement");
                btNextGeneration.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if ("EndOfHumanPlacement".equals(actionEvent.getActionCommand())) {
                            endOfHumanPlacement();
                        }
                    }
                });
                add(btNextGeneration, BorderLayout.SOUTH);
                pack();
            }
        }
        else{
            setPlayMode(board);
        }

    }

    public void endOfHumanPlacement(){
        boardIni(iniBoard.getCurrantTeam()+1,iniBoard.getBoard());
    }

    public void setPlayMode(Board board){
        System.out.println("play mode");
        try{
            remove(boardWidget);
            remove(btNextGeneration);
        }
        catch (Exception e){

        }
        boardWidget = new GraphicBoard(board);
        boardWidget.setPreferredSize(new Dimension(300, 300));
        add(boardWidget, BorderLayout.CENTER);
        btNextGeneration = new Button("End Turn");
        add(btNextGeneration, BorderLayout.SOUTH);
        ///actionListener
        btNextGeneration.setActionCommand("EndOfHumanTurn");
        btNextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("EndOfHumanTurn".equals(actionEvent.getActionCommand())) {
                    boardWidget.unselectCell();
                    boardWidget.getBoard().endHumanPlayerTurn();
                    boardWidget.repaint();
                }
                title = "A Game of Life";
                title += " - Player : " + boardWidget.getBoard().getCurrentPlayer();
                setTitle(title);
            }
        });
        boardWidget.getBoard().playCurrentTurn();
        pack();
    }
}
