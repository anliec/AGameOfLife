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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * class which represent the game board, with specific rules.
 * it commands the advancement of the turns
 * @author team AGOL
 */
public class Board extends BaseBoard implements Cloneable{

    private int currentPlayer;
	private Team[] teams;

    /**
     * random generator
     * @param w width
     * @param h height
     */
    public Board(int w, int h, Options boardOptions){
        super(w, h, boardOptions);
    }

    public Board( Options boardOptions){
        super(boardOptions);
    }

    public Board(Cell[][] cells, Options boardOptions){
        super(cells, boardOptions);
    }

    /**
     *
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public Board(String path, char sep, int w, int h, Options boardOptions) {
        super(path, sep, w, h, boardOptions);
    }

    /**
     * @param path file path
     * @param sep separator between numbers
     */
    public Board(String path, char sep, Options boardOptions){
        super(path, sep, boardOptions);
    }

    /**
     * Do all the general initialisations tasks
     * @param cells cells array witch will be set as cellBoard
     */
    protected void init(Cell[][] cells){
        currentPlayer = 1;
        super.init(cells);
        //generationNumber = 0;
        //setCellBoard(cells);
        writeBoardToFile("test.agols", ' ');
    }


    public void setCellBoard(Cell[][] cellBoard) {
        super.setCellBoard(cellBoard);
        //clean up the teams:
        resetCellCoordinate();
        reloadTeams();
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public Team getTeam(int teamNumber){
        if(teamNumber>=0 && teamNumber < teams.length){
            return teams[teamNumber];
        }
        else{
            return new Team();
        }
    }

    /**
     * do all the stuff to get clean teams
     */
    public void reloadTeams(){
        numberOfTeams = boardOptions.getTeamsIA().size()-1;
        teams = new Team[numberOfTeams+1]; //number of teams + 1 (dead cell team)
        for (int i = 0; i < teams.length; i++) {
            teams[i]= new Team(boardOptions.getTeamsIA().get(i),this);
        }
        for (int i=0; i<height; i++) {//put the team as they must be
            for (int j=0; j<width; j++) {
                Cell cell = cellBoard[i][j];
                try {
                    teams[cell.getTeam()].getCells().add(cell);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCellNumber(int team){
        if(team>=0 && team<teams.length){
            int cellNumber = teams[team].getCells().size();
            if(team == 0){
                cellNumber = width*height-cellNumber;
            }
            return cellNumber;
        }
        else
            return 0;
    }

    /**
     * put the given cell at the given coordinates
     * @param cellCoordinates coordinates of the cell on the board
     * @param cell
     */
    @Override
    public void setCell(BoardPoint cellCoordinates, Cell cell){
        setCell(cellCoordinates.getX(), cellCoordinates.getY(), cell);
    }

    /**
     * put the given cell at the given coordinates
     * @param x abscissa position of the cell on the board
     * @param y ordinate position of the cell on the board
     * @param cell
     */
    @Override
    public void setCell(int x, int y, Cell cell){
        if(x>=0 && x<width && y>=0 && y<height){
            Team team = teams[cellBoard[y][x].getTeam()];
            Cell cell1 = cellBoard[y][x];
            team.getCells().remove(cell1);//remove the cell from it currant team
            cellBoard[y][x] = cell;//set the new cell
            teams[cellBoard[y][x].getTeam()].getCells().add(cellBoard[y][x]);//add the new cell in is new team
            cellBoard[y][x].setCoordinate(new BoardPoint(x,y));
        }
    }


    /**
     * Compute the next generation of the board
     */
    public void computeNextGeneration(){
        Cell[][] newBoard = new Cell[cellBoard.length][cellBoard[0].length];
        for (int y = 0; y < cellBoard.length; y++) {
            for (int x = 0; x < cellBoard[0].length; x++) {
                int neighbour = cellNeighbour(x,y);
                newBoard[y][x] = new Cell(0);
                if(getCell(x,y).isAlive()){
                    if(neighbour<=1){
                        //under-population: the cell died
                    }
                    else if(neighbour<=3){
                        newBoard[y][x] = cellBoard[y][x];//nothing change
                    }
                    else{
                        //overcrowding: the cell died
                    }
                }
                else{
                    if(neighbour==3){
                        //a new cell come, by reproduction
                        int newCellTeam = 0;
                        int r = 1;
                        boolean unSetCase = true;
                        while(unSetCase && r<10){
                            int max = 0;
                            for (int i = 1; i < teams.length; i++) {
                                int curNeighbour = cellExtendedNeighbour(x, y, i, r);
                                if(max == curNeighbour){
                                    unSetCase = true;
                                }
                                else if(max < curNeighbour){
                                    max = curNeighbour;
                                    unSetCase = false;
                                    newCellTeam = i;
                                }
                            }
                            r++;
                        }
                        if(unSetCase){
                            newCellTeam = 0;//(int)(Math.random()*teams.length+1.0);
                        }
                        newBoard[y][x] = new Cell(newCellTeam);
                    }
                    else{
                        newBoard[y][x] = cellBoard[y][x];//nothing change, the cell is still dead
                    }
                }

            }
        }
        generationNumber++;
        setCellBoard(newBoard);
    }

    /**
     * moves a cell from one point to another
     * @param from
     * @param to
     * @return if the move have been performed (if the rules authorized it)
     */
    public boolean moveCell(BoardPoint from, BoardPoint to){
        if(radiusBetween(from,to) == 1 && getCell(from).getTeam() == currentPlayer && !getCell(to).isAlive() && !teams[currentPlayer].getPlayed()){
            setCell(to,getCell(from));
            setCell(from, new Cell(0));
            return true;
        }
        else
            return false;
    }

    /**
     * moves a cell from one point to another
     * @param move which contains the origin and the destination
     * @return if the move have been performed (if the rules authorized it)
     */
    public boolean moveCell(Move move){
        return moveCell(move.from, move.to);
    }

    /**
     * moves a cell from one point to another according to player actions
     * @param move
     * @return if the move have been performed (if the rules authorized it)
     */
    public boolean playCurrentHumanTurn(Move move){
        if(moveCell(move)){
            teams[currentPlayer].setPlayed(true);
            return true;
        }
        else
            return false;
    }

    /**
     * all in the title
     */
    public void endHumanPlayerTurn(){
        if(isHumanPlayerPlaying()){
            endCurrentTurn();
        }
    }

    /**
     * all in the title
     */
    public void endCurrentTurn(){
        currentPlayer++;
        if(currentPlayer<teams.length){
            playCurrentTurn();
        }
        else if(boardOptions.getTeamsIA().contains(false)){
            nextTurn();
        }
        //if there are no human player the game doesn't go to the next turn to prevent infinity loop
    }

    public void playCurrentTurn(){
        if(teams[currentPlayer].isIA()){
            teams[currentPlayer].play();
            endCurrentTurn();
        }
    }

    public void nextTurn(){
        computeNextGeneration();
        for (int i = 1; i < teams.length; i++) {
            teams[i].setPlayed(false);
        }
        currentPlayer = 1;
        playCurrentTurn();
    }

    public boolean isHumanPlayerPlaying(){
        try{
            return !boardOptions.getTeamsIA().get(currentPlayer);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPlayerAlive(int playerNumber){
        if(playerNumber > 0 && playerNumber < teams.length){
            reloadTeams();
            return teams[playerNumber].getCells().size() != 0;
        }
        else{
            return false;
        }
    }

    public boolean isAHumanPlayerAlive(){
        for (int i = 1; i < teams.length; i++) {
            if(!teams[i].isIA()){
                if(isPlayerAlive(i)){
                    return true;
                }
            }
        }
        return false;
    }

    public int numberOfPlayerAlive(){
        int ret=0;
        for (int i = 1; i < teams.length; i++) {
            if(isPlayerAlive(i))
                ret++;
        }
        return ret;
    }

    public int lastPlayerAlive(){
        if(numberOfPlayerAlive()!=1){
            return 0; // if there are more than one player alive
        }
        for (int i = 1; i < teams.length; i++) {
            if(isPlayerAlive(i))
                return i;
        }
        return 0; //if there are no player alive (can't append do to the first if)
    }

    public static void main(String args[]) {
        Board board = new Board("Boards/TestBoard1", ' ', 5, 5,new Options());
        board.printConsoleBoard(' ');
        System.out.println("\n");
        board.computeNextGeneration();
        board.printConsoleBoard(' ');
    }

    public Board clone(){
        Board board = null;
        board = (Board)super.clone();
        /*Team[] copiedTeam = new Team[teams.length];
        for (int i = 0; i < copiedTeam.length; i++) {
            copiedTeam[i] = teams[i].clone();
        }
        board.setTeams(teams.clone());*/
        board.reloadTeams();
        board.setCurrentPlayer(currentPlayer);
        return board;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }


}
