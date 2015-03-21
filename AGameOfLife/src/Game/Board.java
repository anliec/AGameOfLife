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
        init();
        Cell[][] cells = new Cell[h][w];
        for (int i=0; i<h; i++) {
            for (int j=0; j<w; j++) {
                if(Math.random()<0.3) { // probability to have an alive cell = 0.3
                    cells[i][j]=new Cell((int)(Math.random()*2+1));
                    teams[cells[i][j].getTeam()-1].getCells().add(cells[i][j]);
                }
            }
        }
        setCellBoard(cells);
    }
    
    /**
     * 
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public Board(String path, char sep, int w, int h) {
        init();
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
                        teams[cells[i][j].getTeam()].getCells().add(cells[i][j]);
                    }
                }
                i++;
            }
            br.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        setCellBoard(cells);
    }

    /**
     * Do all the general initialisations tasks
     */
    private void init(){
        generationNumber = 0;
        teams = new Team[3]; //number of teams : 2 + 1 (dead cell team)
        teams[0] = new Team(); teams[1] = new Team(); teams[2] = new Team();
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
        Board board = new Board("AGameOfLife/Boards/TestBoard1", ' ', 5, 5);
        board.printConsoleBoard(' ');
    }
}
