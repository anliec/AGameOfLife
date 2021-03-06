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


import java.io.*;

/**
 * A basic board which can perform basic moves
 * @author team AGOL
 */
public class BaseBoard implements Cloneable {

    protected int generationNumber;
    protected int width;
    protected int height;
    protected Cell[][] cellBoard;
    protected int numberOfTeams;
    protected Options boardOptions;

    /**
     * void board generator
     * @param w width
     * @param h height
     */
    public BaseBoard(int w, int h, Options boardOptions){
        Cell[][] cells = new Cell[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                cells[x][y] = new Cell(0,new BoardPoint(x,y));
            }
        }
        this.boardOptions = boardOptions;
        init(cells);
    }

    public BaseBoard(Options gameOptions){
        this.boardOptions = gameOptions;
        Cell[][] cells = new Cell[boardOptions.getBoardWidth()][boardOptions.getBoardHeight()];
        for (int x = 0; x < boardOptions.getBoardWidth(); x++) {
            for (int y = 0; y < boardOptions.getBoardHeight(); y++) {
                cells[x][y] = new Cell(0,new BoardPoint(x,y));
            }
        }
        init(cells);
    }

    public BaseBoard(Cell[][] cells,Options boardOptions){
        this.boardOptions = boardOptions;
        init(cells);
    }

    /**
     *
     * @param path file path
     * @param sep separator between numbers
     * @param w width
     * @param h height
     */
    public BaseBoard(String path, char sep, int w, int h,Options boardOptions) {
        this.boardOptions = boardOptions;
        loadBoardFromFile(path, sep, w, h);
    }

    /**
     * @param path file path
     * @param sep separator between numbers
     */
    public BaseBoard(String path, char sep, Options boardOptions){
        this.boardOptions = boardOptions;
        loadBoardFromFile(path, sep, getBoardWidthFromFile(path, sep), getBoardHeightFromFile(path));
    }

    /**
     * @param path file path
     * @param sep separator between numbers
     * @param boardWidth width size of the board to load
     * @param boardHeight height size of the board to load
     */
    public void loadBoardFromFile(String path, char sep, int boardWidth, int boardHeight){
        Cell[][] cells;
        try {
            InputStream ips=new FileInputStream(path);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            int i=0;
            //read the file:
            cells = new Cell[boardHeight][boardWidth];
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
            cells = randomBoard(10,10,0.5); //if there are an error when reading the file we get a random board...
        }catch (IOException e){
            e.printStackTrace();
            cells = randomBoard(10,10,0.5); //if there are an error when reading the file we get a random board...
        }
        init(cells);
    }

    public void writeBoardToFile(String path, char sep){
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            for (int x = 0; x < cellBoard.length; x++) {
                for (int y = 0; y < cellBoard[0].length; y++) {
                    writer.print(cellBoard[x][y]);
                    if(y!=cellBoard[0].length-1)writer.print(sep);
                }
                if (x!=cellBoard.length-1)writer.println();
            }
            writer.close();
        }
        catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * @param path file path
     * @param sep separator between numbers
     * @return number of value on the first line of the file
     */
    public int getBoardWidthFromFile(String path, char sep){
        int w=0;
        try {
            InputStream ips=new FileInputStream(path);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            boolean wasSeparator = true;
            int curChar;
            while((curChar=br.read())!='\n'){
                if(curChar!=sep && wasSeparator){
                    w++;
                    wasSeparator = false;
                }
                else if (curChar==sep){
                    wasSeparator = true;
                }
            }
            br.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return w;
    }

    /**
     * @param path file path
     * @return number of line on the file
     */
    public int getBoardHeightFromFile(String path){
        int h = 0;
        try {
            InputStream ips=new FileInputStream(path);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader br=new BufferedReader(ipsr);
            while((br.readLine())!=null){
                h++;
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return h;
    }

    /**
     * Do all the general initialisations tasks
     * @param cells cells array witch will be set as cellBoard
     */
    protected void init(Cell[][] cells){
        generationNumber = 0;
        setCellBoard(cells);
    }

    public void resetCellCoordinate(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellBoard[y][x].setCoordinate(new BoardPoint(x,y));
            }
        }
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
        System.out.println();
    }

    public int cellExtendedNeighbour(BoardPoint point, int countTeam, int radius){
        return cellExtendedNeighbour(point.getX(),point.getY(),countTeam,radius);
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
            neighbourCount = ((xMax-xMin+1)*(yMax-yMin+1))-1-neighbourCount;
            //if neighbourCount is the number of zero then the number of neighbour is the number of cells minus neighbourCount
        }
        return neighbourCount;
    }

    public int cellNaighbour(BoardPoint point, int countTeam){
        return cellNeighbour(point.getX(),point.getY(),countTeam);
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @param countTeam team number of th cell that will be counted
     * @return number of cell around the given position belonging to to team given on a radius of 1
     */
    public int cellNeighbour(int cellX, int cellY, int countTeam){
        return cellExtendedNeighbour(cellX, cellY, countTeam, 1);
    }

    public int cellNeighbour(BoardPoint point){
        return cellNeighbour(point.getX(),point.getY());
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @return number of cell around the given position on a radius of 1
     */
    public int cellNeighbour(int cellX, int cellY){
        return cellNeighbour(cellX, cellY, 0);
    }

    /**
     * @param A a point in board coordinates
     * @param B a point in board coordinates
     * @return the radius between this two point if the two of them are on the board else return -1
     */
    public int radiusBetween(BoardPoint A,BoardPoint B){
        if(isOnBoard(A) && isOnBoard(B)){
            return Math.max(Math.abs(A.getX()-B.getX()),Math.abs(A.getY()-B.getY()));
        }
        else{
            return -1;
        }
    }

    /**
     * @param A a point in board coordinates
     * @return true if the coordinates aren't out of the board
     */
    public boolean isOnBoard(BoardPoint A){
        return A.getX()>=0 && A.getY()>=0 && A.getX()<cellBoard[0].length && A.getY()<cellBoard.length;
    }
    public void setCellBoard(Cell[][] cellBoard) {
        this.cellBoard = cellBoard;
        height = cellBoard.length;
        width = cellBoard[0].length;
        for (int x = 0; x < cellBoard.length; x++) {
            for (int y = 0; y < cellBoard[0].length; y++) {
                if(numberOfTeams < cellBoard[x][y].getTeam()){
                    numberOfTeams = cellBoard[x][y].getTeam();
                }
            }
        }
    }

    /**
     * set a new cell board without changing anything else,
     * all the others settings MUST be set by an other way
     * (created for optimisation purpose)
     * @param cellBoard the new cell board
     */
    public void basicallySetCellBoard(Cell[][] cellBoard){
        this.cellBoard = cellBoard;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell[][] getCellBoard() {
        return cellBoard;
    }

    /**
     * @param cellCoordinates coordinates of the cell on the board
     * @return the cell at the given coordinates on the board
     */
    public Cell getCell(BoardPoint cellCoordinates){
        return getCell(cellCoordinates.getX(),cellCoordinates.getY());
    }

    /**
     * @param x abscissa position of the cell on the board
     * @param y ordinate position of the cell on the board
     * @return the cell at the given coordinates on the board
     */
    public Cell getCell(int x,int y){
        if(x>=0 && x<width && y>=0 && y<height){
            return cellBoard[y][x];
        }
        else{
            return new Cell(0);//return a dead cell if it's out of the board
        }
    }

    /**
     * put the given cell at the given coordinates
     * @param x abscissa position of the cell on the board
     * @param y ordinate position of the cell on the board
     * @param cell
     */
    public void setCell(int x, int y, Cell cell){
        if(x>=0 && x<width && y>=0 && y<height){
            cellBoard[y][x] = cell;//set the new cell
            cellBoard[y][x].setCoordinate(new BoardPoint(x,y));
        }
    }

    public void setCell(BoardPoint cellCoordinates, Cell cell){
        setCell(cellCoordinates.getX(), cellCoordinates.getY(), cell);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setBoardOptions(Options boardOptions) {
        this.boardOptions = boardOptions;
    }

    public Options getBoardOptions() {
        return boardOptions;
    }

    public BaseBoard clone(){
        BaseBoard baseBoard = null;
        try{
            baseBoard = (BaseBoard)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        Cell [][] cellBoardCopy = new Cell[height][width];
        for (int x = 0; x < cellBoardCopy.length; x++) {
            for (int y = 0; y < cellBoardCopy[0].length; y++) {
                cellBoardCopy[x][y] = cellBoard[x][y];
            }
        }
        //baseBoard.setCellBoard(cellBoardCopy);
        baseBoard.basicallySetCellBoard(cellBoardCopy);
        baseBoard.setHeight(height);
        baseBoard.setWidth(width);
        baseBoard.setBoardOptions(boardOptions.clone());//the options can't change, use less to clone
        return baseBoard;
    }

}
