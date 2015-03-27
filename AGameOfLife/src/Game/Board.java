package Game;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Board {

	private int generationNumber;
	private int width;
	private int height;
	private Cell[][] cellBoard;
	private Team[] teams;

	
	public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell[][] getCellBoard() {
        return cellBoard;
    }

    public void setCellBoard(Cell[][] cellBoard) {
        this.cellBoard = cellBoard;
        height = cellBoard.length;
        width = cellBoard[0].length;
        //clean up the teams:
        teams = new Team[3]; //number of teams : 2 + 1 (dead cell team)
        teams[0] = new Team(); teams[1] = new Team(); teams[2] = new Team();
        for (int i=0; i<height; i++) {//put the team as they must be
            for (int j=0; j<width; j++) {
                teams[cellBoard[i][j].getTeam()].getCells().add(cellBoard[i][j]);
            }
        }
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public Cell getCell(int x,int y){
        if(x>=0 && x<width && y>=0 && y<height){
            return cellBoard[y][x];
        }
        else{
            return new Cell(0);//return a dead cell if it's out of the board
        }
    }

    public void setCell(int x, int y, Cell cell){
        if(x>=0 && x<width && y>=0 && y<height){
            teams[cellBoard[y][x].getTeam()].getCells().remove(cellBoard[y][x]);//remove the cell from it currant team
            cellBoard[y][x] = cell;//set the new cell
            teams[cellBoard[y][x].getTeam()].getCells().add(cellBoard[y][x]);//add the new cell in is new team
        }
    }

    /**
	 * random generator
	 * @param w width
	 * @param h height
	 */
    public Board(int w, int h){
        init(randomBoard(w,h,0.3));
    }
    
    /**
     * 
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public Board(String path, char sep, int w, int h) {
        Cell[][] cells = new Cell[h][w];
        try {
            InputStream ips=new FileInputStream(path); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            int i=0;
            String line;
            while ((line=br.readLine())!=null){
                int j=0;
                for (int a=0; a<line.length(); a++){
                    if (line.charAt(a) == sep)
                        j++;
                    else {
                        int n;
                        try {
                            n = Integer.parseInt(line.substring(a, a + 1) );
                        }
                        catch (NumberFormatException e){
                            n = 0; //if the value is not numeric, as an error we set it to zero
                        }
                        cells[i][j] = new Cell(n);
                    }
                }
                i++;
            }
            br.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            cells = randomBoard(w,h,0.3); //if there are an error when reading the file we get a random board...
        }catch (IOException e){
            e.printStackTrace();
            cells = randomBoard(w,h,0.3); //if there are an error when reading the file we get a random board...
        }
        init(cells);
    }

    /**
     * Do all the general initialisations tasks
     * @param cells cells array witch will be set as cellBoard
     */
    private void init(Cell[][] cells){
        generationNumber = 0;
        setCellBoard(cells);
    }

    /**
     * Generate a random board from the given arguments
     * @param width width of the board
     * @param height height of the board
     * @param probability probability of a cell to be alive
     * @return a board randomly generated
     */
    public Cell[][] randomBoard(int width, int height, double probability){
        Cell[][] cells = new Cell[height][width];
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if(Math.random()<probability) { // probability to have an alive cell
                    cells[i][j]=new Cell((int)(Math.random()*2+1));
                }
                else{
                    cells[i][j]=new Cell(0);
                }
            }
        }
        return cells;
    }
    
    /**
     * display the board on console
     * @param sep separator
     */
    public void printConsoleBoard(char sep) {
        for(int i=0; i<height; i++){
            System.out.print("\n"+sep);
            for(int j=0; j<width; j++) {
                if(cellBoard[i][j].getTeam()==0)
                    System.out.print("-"+sep);
                else {
                    System.out.print(cellBoard[i][j]);
                    System.out.print(sep);
                }
            }
        }
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @param countTeam team number of th cell that will be counted
     * @param radius radius around the cell witch will be scanned
     * @return number of cell around the given position belonging to to team given on a given radius
     */
    public  int cellExtendedNeighbour(int cellX, int cellY, int countTeam, int radius){
        if(radius<1){ //a little check...
            return 0;
        }
        int xMax = cellX+radius, xMin = cellX-radius;
        int yMax = cellY+radius, yMin = cellY-radius;
        int neighbourCount = 0;
        if(xMax>=cellBoard[0].length){
            xMax = cellBoard[0].length-1;
        }
        if(xMin<0){
            xMin = 0;
        }
        if(yMax>=cellBoard.length){
            yMax = cellBoard.length-1;
        }
        if(yMin<0){
            yMin = 0;
        }
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                if(!(x==cellX && y==cellY) && cellBoard[y][x].getTeam()==countTeam){
                    neighbourCount++;
                }
            }
        }
        if(countTeam==0){
            neighbourCount = ((xMax-xMin+1)*(yMax-yMin+1))-1-neighbourCount;//if neighbourCount is the number of zero then the number of neighbour is the number of cells minus neighbourCount
        }
        return neighbourCount;
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @param countTeam team number of th cell that will be counted
     * @return number of cell around the given position belonging to to team given on a radius of 1
     */
    public int cellNeighbour(int cellX, int cellY, int countTeam){
        return cellExtendedNeighbour(cellX,cellY,countTeam,1);
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @return number of cell around the given position on a radius of 1
     */
    public int cellNeighbour(int cellX, int cellY){
        return cellNeighbour(cellX,cellY,0);
    }

    /**
     * Compute the next generation of the board
     */
    public void computeNextGeneration(){
        Cell[][] newBoard = new Cell[cellBoard.length][cellBoard[0].length];
        for (int y = 0; y < cellBoard[0].length; y++) {
            for (int x = 0; x < cellBoard.length; x++) {
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

    public static void main(String args[]) {
        Board board = new Board("Boards/TestBoard1", ' ', 5, 5);
        board.printConsoleBoard(' ');
        System.out.println("\n");
        board.computeNextGeneration();
        board.printConsoleBoard(' ');
    }
}
