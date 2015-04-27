package Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Board extends BaseBoard implements Cloneable{

    private int currentPlayer;
    private int teamHumanPlayer;
	private Team[] teams;

    @Override
    public void setCellBoard(Cell[][] cellBoard) {
        this.cellBoard = cellBoard;
        height = cellBoard.length;
        width = cellBoard[0].length;
        //clean up the teams:
        reloadTeams();
        resetCellCoordinate();
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
        teams = new Team[3]; //number of teams : 2 + 1 (dead cell team)
        for (int i = 0; i < teams.length; i++) {
            teams[i]= new TeamBasicIA(i!=teamHumanPlayer,this);
        }
        for (int i=0; i<height; i++) {//put the team as they must be
            for (int j=0; j<width; j++) {
                Cell cell = cellBoard[i][j];
                teams[cell.getTeam()].getCells().add(cell);
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
        setCell(cellCoordinates.getX(),cellCoordinates.getY(),cell);
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
            teams[cellBoard[y][x].getTeam()].getCells().remove(cellBoard[y][x]);//remove the cell from it currant team
            cellBoard[y][x] = cell;//set the new cell
            teams[cellBoard[y][x].getTeam()].getCells().add(cellBoard[y][x]);//add the new cell in is new team
            cellBoard[y][x].setCoordinate(new BoardPoint(x,y));
        }
    }

    /**
	 * random generator
	 * @param w width
	 * @param h height
	 */
    public Board(int w, int h){
        super(w, h);
    }

    public Board(Cell[][] cells){
        super(cells);
    }
    
    /**
     * 
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public Board(String path, char sep, int w, int h) {
        super(path,sep,w,h);
    }

    /**
     * @param path file path
     * @param sep separator between numbers
     */
    public Board(String path, char sep){
        super(path, sep);
    }

    /**
     * Do all the general initialisations tasks
     * @param cells cells array witch will be set as cellBoard
     */
    protected void init(Cell[][] cells){
        currentPlayer = 1;
        teamHumanPlayer = 1;
        super.init(cells);
        //generationNumber = 0;
        //setCellBoard(cells);
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
                            newCellTeam = (int)(Math.random()*teams.length+1.0);
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

    public boolean moveCell(BoardPoint from, BoardPoint to){
        if(radiusBetween(from,to) == 1 && getCell(from).getTeam() == currentPlayer && !getCell(to).isAlive() ){
            setCell(to,getCell(from));
            setCell(from,new Cell(0));
            return true;
        }
        else
            return false;
    }

    public boolean moveCell(Move move){
        return moveCell(move.from, move.to);
    }

    public void endHumanPlayerTurn(){
        if(isHumanPlayerPlaying()){
            endCurrentTurn();
        }
    }

    public void endCurrentTurn(){
        currentPlayer++;
        if(currentPlayer<teams.length){
            playCurrentTurn();
        }
        else{
            nextTurn();
        }
    }

    public void playCurrentTurn(){
        if(teams[currentPlayer].isIA()){
            teams[currentPlayer].play();
            endCurrentTurn();
        }
    }

    public void nextTurn(){
        computeNextGeneration();
        currentPlayer = 1;
        playCurrentTurn();
    }

    public boolean isHumanPlayerPlaying(){
        return currentPlayer==teamHumanPlayer;
    }

    public static void main(String args[]) {
        Board board = new Board("Boards/TestBoard1", ' ', 5, 5);
        board.printConsoleBoard(' ');
        System.out.println("\n");
        board.computeNextGeneration();
        board.printConsoleBoard(' ');
    }

    public Board clone(){
        Board board = null;
        board = (Board)super.clone();
        board.setTeams(teams.clone());
        board.setCurrentPlayer(currentPlayer);
        board.setTeamHumanPlayer(teamHumanPlayer);
        return board;
    }

    public void setTeamHumanPlayer(int teamHumanPlayer) {
        this.teamHumanPlayer = teamHumanPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getTeamHumanPlayer() {
        return teamHumanPlayer;
    }

}
