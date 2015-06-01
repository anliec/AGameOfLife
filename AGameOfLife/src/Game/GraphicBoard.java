/**This file is part of A Game Of Life.

 Game Of Life is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Game Of Life is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Game Of Life.  If not, see <http://www.gnu.org/licenses/>.
 **/

package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Graphic Board contains the board plus some graphic and event elements
 * @author team AGOL
 */
public class GraphicBoard extends JPanel {

    /**
     * @return the Board used by GraphicBoard
     */
    public Board getBoard() {
        return board;
    }

    protected Board board;
    protected int squareSize;
    protected BoardPoint selectedCell;
    protected Options gameOptions;
    protected String notificationText;
    protected Timer notificationTimer;

    /**
     * default constructor, generate a board thanks to default options
     */
    public  GraphicBoard(){
        gameOptions = new Options();
        voidBoardInit(gameOptions.getBoardWidth(),gameOptions.getBoardHeight());
    }

    /**
     * generate a void board of the given size (thanks to option)
     * @param gameOptions options to use
     */
    public GraphicBoard(Options gameOptions){
        voidBoardInit(gameOptions.getBoardWidth(),gameOptions.getBoardHeight());
        this.gameOptions = gameOptions;
    }

    /**
     * @param path path of the file to load
     * @param gameOptions options to use for that file
     */
    public GraphicBoard(String path, Options gameOptions){
        init(new Board(path, ' ', gameOptions));
        this.gameOptions = gameOptions;
    }

    /**
     * Main constructor
     * @param sourceBoard the board that will be set as the board of the GraphicBoard
     */
    public GraphicBoard(Board sourceBoard){
        gameOptions = sourceBoard.getBoardOptions();
        init(sourceBoard);
    }

    /**
     * Do all the general initializations tasks
     * @param sourceBoard the board that will be set as the board of the GraphicBoard
     */
    private void init(Board sourceBoard){
        board = sourceBoard;
        addComponentListener(new ComponentAdapter() { //to get the resize event in order to keep squareSize updated
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setSquareSize();
                repaint();
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                switch (mouseEvent.getButton()) {
                    case MouseEvent.BUTTON1:
                        onLeftClick(mouseToBoard(mouseEvent.getPoint()));
                        break;
                    case MouseEvent.BUTTON3:
                        onRightClick(mouseToBoard(mouseEvent.getPoint()));
                        break;
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        selectedCell = new BoardPoint(-1,-1);
        setSquareSize();// to be sure that squareSize is not zero
        notificationText = "";
        notificationTimer = new Timer(100, null);
    }

    /**
     * action done when a left click is detected on the board
     * @param point point were the click was (in board coordinate)
     */
    protected void onLeftClick(BoardPoint point){
        setSelectedCell(point);
    }

    /**
     * action done when a right click is detected on the board
     * @param point point were the click was (in board coordinate)
     */
    protected void onRightClick(BoardPoint point){
        if(isACellSelected()){
            if(board.playCurrentHumanTurn(new Move(selectedCell,point,0))){
                setSelectedCell(point);
            }
        }
    }

    /**
     * init the GraphicBoard with a empty board
     * @param width width of the board
     * @param height height of the board
     */
    private void voidBoardInit(int width, int height){
        Cell[][] cellTab = new Cell[width][height];
        for (int x = 0; x < cellTab.length; x++) {
            for (int y = 0; y < cellTab[0].length; y++) {
                cellTab[x][y] = new Cell(0,new BoardPoint(x,y));
            }
        }
        init(new Board(cellTab,gameOptions));
    }

    /**
     * @param cellCoordinates coordinates of the cell
     * @return if the cell is set, return true and false if the cell is out of the board
     */
    public boolean setSelectedCell(BoardPoint cellCoordinates){
        return setSelectedCell(cellCoordinates.getX(),cellCoordinates.getY());
    }

    /**
     * @param cellX abscissa position of the cell
     * @param cellY ordinate position of the cell
     * @return if the cell is set, return true and false if the cell is out of the board
     */
    public boolean setSelectedCell(int cellX, int cellY){
        if(cellX>=0&&cellY>=0&&cellX<board.getWidth()&&cellY<board.getHeight()){
            selectedCell.setY(cellY);
            selectedCell.setX(cellX);
            return true;
        }
        else{
            unselectCell();
            return false; //if the cell is not on the board
        }
    }

    /**
     * unselect the currently selected cell
     */
    public void unselectCell(){
        selectedCell.setY(-1);
        selectedCell.setX(-1);
        repaint();
    }

    /**
     * @return true if a cell is selected
     */
    public boolean isACellSelected(){
        return selectedCell.getX()!=-1 && selectedCell.getY()!=-1;
    }

    /**
     * @param mousePositionOnJPanel coordinate of a point on the JPanel
     * @return coordinate of a cell on the board or (-1,-1) if there are no cell there
     */
    public BoardPoint mouseToBoard(Point mousePositionOnJPanel){
        Point origin = getOrigin();
        int x = mousePositionOnJPanel.x - origin.x;
        int y = mousePositionOnJPanel.y - origin.y;
        if(x>=0&&y>=0&&x<=board.getWidth()*squareSize&&y<=board.getHeight()*squareSize){
            return new BoardPoint(x/squareSize , y/squareSize );
        }
        else{
            return new BoardPoint(-1,-1);
        }

    }


    /**
     * Set the size of the square used to represent the cells
     */
    public void setSquareSize(){
        double a = getSize().getHeight() / board.getHeight();
        double b = getSize().getWidth() / board.getWidth();
        squareSize = (int)(Math.min(a,b));
    }

    /**
     * Paint the actual representation of the board
     * @param g Graphics witch will be used to paint the cells array
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        Point origin = getOrigin();
        Cell[][] cellBoard = board.getCellBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = cellBoard[y][x];
                int team = cell.getTeam();
                Color cellColor = getTeamColor(team);
                g2d.setColor( cellColor );
                g2d.fillRect(origin.x+x*squareSize,origin.y+y*squareSize,squareSize,squareSize);
            }
        }
        if(isACellSelected()){
            g2d.setColor(new Color(0, 200, 0));
            g2d.drawRect(origin.x + selectedCell.getX() * squareSize, origin.y + selectedCell.getY() * squareSize, squareSize, squareSize);
        }
        if(!notificationText.equals("")){
            //some calculation
            Font font = g.getFont();
            font = new Font(font.getName(),font.getStyle(),30);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(notificationText);
            int textHeight = fm.getHeight();
            int textX = (int)(getSize().getWidth()/2 - textWidth/2);
            int textY = (int)(getSize().getHeight()/2-textHeight/2);
            //draw background
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g2d.setColor(Color.black);
            g2d.fillRect(textX-20, textY-textHeight-10, textWidth + 40, textHeight + 40);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            //draw notification text
            g2d.setColor(Color.white);
            g2d.drawString(notificationText, textX, textY);
        }
    }

    /**
     * Give the color witch is corresponding to a team
     * @param cellTeam team number
     * @return Color of the team
     */
    public Color getTeamColor(int cellTeam){
        Color ret;
        switch (cellTeam){
            case 1:
                ret = new Color(255,0,0);
                break;
            case 2:
                ret = new Color(0,0,255);
                break;
            case 3:
                ret = new Color(255, 255, 0);
                break;
            case 4:
                ret = new Color(0, 234, 255);
                break;
            case 5:
                ret = new Color(255, 167, 0);
                break;
            case 6:
                ret = new Color(0,150,0);
                break;
            default:
                ret = new Color(0,0,0);
        }
        return ret;
    }

    /**
     * Compute the abscissa of the origin
     * @return the abscissa of the origin
     */
    private int getAbscissaOrigin(){
        int cellWidth = squareSize * board.getWidth();
        return (getWidth()-cellWidth)/2;
    }

    /**
     * Compute the ordinate of the origin
     * @return the ordinate of the origin
     */
    private int getOrdinateOrigin(){
        int cellHeight= squareSize * board.getHeight();
        return (getHeight()-cellHeight)/2;
    }

    /**
     * show the given text in the center of the board
     * @param text the text that will be shown on the board
     * @param msTime time before the message disappear (micro second)
     */
    public void addNotification(String text, int msTime){
        notificationTimer.stop();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                notificationText="";
                repaint();
            }
        };
        notificationTimer = new Timer(msTime ,taskPerformer);
        notificationTimer.setRepeats(false);
        notificationTimer.start();
        notificationText = text;
        repaint();
        /*try{// try to sleep 10ms in order to allow a repaint of the board
            Thread.sleep(10);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }

    /**
     * do all the stuff needed to go to the next turn
     */
    public void endHumanPlayerTurn(){
        addNotification("AI is thinking...", 1000);
        unselectCell();
        board.endHumanPlayerTurn();
        if(!board.isPlayerAlive(board.getCurrentPlayer()) && board.isAHumanPlayerAlive()){
            endHumanPlayerTurn();// if an human player is still alive but the current one is not: end currant turn
        }
        else{
            addNotification("It's Player " + board.getCurrentPlayer() + " turn", 700);
            repaint();
        }
    }

    /**
     * Compute the coordinate of the origin
     * @return the origin point
     */
    protected Point getOrigin(){
        return new Point(getAbscissaOrigin(),getOrdinateOrigin());
    }

    /**
     * load a board form a file
     * @param path path of the file
     * @param sep separator between the cells (default is ' ')
     */
    public void setBoardFromFile(String path, char sep){
        init(new Board(path,sep,gameOptions));
        repaint();
    }

    /**
     * save the board to the given path
     * @param path path where the file will be written
     * @param sep separator between the cells (default is ' ')
     */
    public void saveBoardToFile(String path, char sep){
        board.writeBoardToFile(path, sep);
    }


}
