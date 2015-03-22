package Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    
    public static void main(String args[]) {
        Board board = new Board("Boards/TestBoard1", ' ', 5, 5);
        board.printConsoleBoard(' ');
    }
}
