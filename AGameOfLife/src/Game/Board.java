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
        generationNumber = 0;
        teams = new Team[2];
        teams[0] = new Team(); teams[1] = new Team();
        width = w;
        height = h;
        cellBoard = new Cell[height][width];
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if(Math.random()<0.3) { // probability to have an alive cell = 0.3
                    cellBoard[i][j]=new Cell((int)(Math.random()*2+1));
                    teams[cellBoard[i][j].getTeam()-1].getCells().add(cellBoard[i][j]);
                }
            }
        }
    }
    
    /**
     * 
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public Board(String path, char sep, int w, int h) {
        width = w;
        height = h;
        teams = new Team[2]; //number of teams : 2
        teams[0] = new Team(); teams[1] = new Team();
        cellBoard=new Cell[height][width];
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
                        int n = ((int)line.charAt(a))-48;
                        if(n != 0) {
                            cellBoard[i][j] = new Cell(n);
                            teams[cellBoard[i][j].getTeam()-1].getCells().add(cellBoard[i][j]);
                        }
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
    }
    
    /**
     * display the board on console
     * @param sep separator
     */
    public void printConsoleBoard(char sep) {
        for(int i=0; i<height; i++){
            System.out.print("\n"+sep);
            for(int j=0; j<width; j++) {
                if(cellBoard[i][j]==null)
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
