/**
 * This file is part of A Game Of Life.
 *
 * Game Of Life is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Game Of Life is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Game Of Life.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

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

    private final int WIDGET_WIDTH = 600;
    private final int WIDGET_HEIGHT = 600;

    private final int WINDOW_MIN_WIDTH = 100;
    private final int WINDOW_MIN_HEIGHT = 100;

    protected Options gameOptions;
    protected OptionWindow optionWindow;
    protected GraphicBoard boardWidget;
    protected GraphicBoardIni iniBoard;
    protected Button btNextGeneration;
    protected JMenuBar menuBar;
    protected String title;
    protected JFileChooser fc;

    /**
     * default constructor
     */
    public MainWindows(){
        title=("A Game of Life");
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //set icone
        ImageIcon img = new ImageIcon("Images/AGOL.png");
        this.setIconImage(img.getImage());

        ///set UI
        gameOptions = new Options();
        menuBar = new JMenuBar();
        initMenuBar();
        setJMenuBar(menuBar);
        //load file selection window
        fc = new JFileChooser("Boards");
        //load options windows
        optionWindow = new OptionWindow();

        setPlayMode(new Board(gameOptions));
        btNextGeneration.setEnabled(false);

        this.setMinimumSize(new Dimension(WINDOW_MIN_WIDTH,WINDOW_MIN_HEIGHT));

        pack();
        setVisible(true);
    }

    /**
     * initialisation of the menu bar of the main window
     */
    private void initMenuBar() {
        JMenu fileMenu = new JMenu("File"),
                toolsMenu = new JMenu("Tools"),
                helpMenu = new JMenu("About");
        JMenuItem newMenuItem = new JMenuItem("New Game"),
                openMenuItem = new JMenuItem("Open..."),
                saveMenuItem = new JMenuItem("Save as..."),
                closeMenuItem = new JMenuItem("Exit"),
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
                optionWindow.setVisible(true);
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

    /**
     * open the file choosing windows and manage its return in order to open files
     */
    public void openFileMenu(){
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            setPlayMode(new Board(fc.getSelectedFile().getPath(), ' ',gameOptions));
            //boardWidget.setBoardFromFile(fc.getSelectedFile().getPath(), ' ');
        }
    }

    /**
     * open the file choosing windows and manage its return in order to save files
     */
    public void saveFileMenu(){
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            boardWidget.saveBoardToFile(fc.getSelectedFile().getPath(), ' ');
        }
    }

    /**
     * manage the initialisation of a new board
     */
    public void initialisationOfTheBoard(){
        //board initialisation:
        gameOptions = optionWindow.getOptions();
        Board board = new Board(gameOptions);
        Team[] teams = new Team[gameOptions.getTeamsIA().size()];
        teams[0] = board.getTeam(0);//get the dead cell team witch is correctly set
        for (int i = 1; i < teams.length; i++) {
            teams[i] = new Team(gameOptions.getTeamsIA().get(i),board);
        }
        board.setTeams(teams);

        //setup of the teams cells:
        boardIni(1, board);

    }

    /**
     * add the team given to the board if the team is not an IA the
     * method show an interface for the user to place is cells
     * @param team number of the team to init
     * @param board the board where the team will be added
     */
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
                boardWidget.setPreferredSize(new Dimension(WIDGET_WIDTH, WIDGET_HEIGHT));
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
                this.setVisible(true);
            }
        }
        else{
            setPlayMode(board);
        }

    }

    /**
     * end the placement of the currant human team
     */
    public void endOfHumanPlacement(){
        boardIni(iniBoard.getCurrantTeam()+1,iniBoard.getBoard());
    }

    /**
     * set the interface in playing mode
     * @param board the board which will be showed at the first turn
     */
    public void setPlayMode(final Board board){
        try{
            remove(boardWidget);
            remove(btNextGeneration);
        }
        catch (Exception e){

        }
        boardWidget = new GraphicBoard(board);
        boardWidget.setPreferredSize(new Dimension(WIDGET_WIDTH, WIDGET_HEIGHT));
        add(boardWidget, BorderLayout.CENTER);
        btNextGeneration = new Button("End Turn");
        add(btNextGeneration, BorderLayout.SOUTH);
        ///actionListener
        btNextGeneration.setActionCommand("EndOfHumanTurn");
        btNextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("EndOfHumanTurn".equals(actionEvent.getActionCommand())) {
                    btNextGeneration.setEnabled(false);
                    boardWidget.endHumanPlayerTurn();
                    btNextGeneration.setEnabled(true);
                    endOfGameManagement();
                }
                title = "A Game of Life";
                title += " - Player : " + boardWidget.getBoard().getCurrentPlayer();
                setTitle(title);
            }
        });
        boardWidget.getBoard().playCurrentTurn();
        setVisible(true);
    }

    public void endOfGameManagement(){
        if(!boardWidget.getBoard().isAHumanPlayerAlive()){ // /!\ bug on isAHumanPlayerAlive()
            gameOver();
        }
        else{
            int numberOfPlayerAlive = boardWidget.getBoard().numberOfPlayerAlive();
            if(numberOfPlayerAlive == 0){
                gameOver();
            }
            else if(numberOfPlayerAlive == 1){
                victory(boardWidget.getBoard().lastPlayerAlive());
            }
        }
    }

    public void victory(int teamNumber){
        boardWidget.addNotification("Player "+teamNumber+" win !!!", 10000);
        endOfGame();
    }

    public void gameOver(){
        boardWidget.addNotification("Game Over", 10000);
        endOfGame();
    }

    public void endOfGame(){
        btNextGeneration.setEnabled(false);
    }
}
