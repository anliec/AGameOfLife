package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by nicolas on 21/03/15.
 */
public class GraphicBoard extends JPanel {

    public Board getBoard() {
        return board;
    }

    private Board board;
    private int squareSize;
    private BoardPoint selectedCell;

    /**
     * Default constructor: load the Board:"AGameOfLife/Boards/TestBoard1"
     */
    public GraphicBoard(){
        init(new Board("AGameOfLife/Boards/TestBoard3", ' '));
    }

    /**
     * Main constructor
     * @param sourceBoard the board that will be set as the board of the GraphicBoard
     */
    public GraphicBoard(Board sourceBoard){
        init(sourceBoard);
    }

    /**
     * Do all the general initialisations tasks
     * @param sourceBoard the board that will be set as the board of the GraphicBoard
     */
    private void init(Board sourceBoard){
        board = sourceBoard;
        addComponentListener(new ComponentAdapter() { //to get the resize event in order to keep squareSize updated
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setSquareSize();
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                switch (mouseEvent.getButton()){
                    case MouseEvent.BUTTON1:
                        setSelectedCell(mouseToBoard(mouseEvent.getPoint()));
                        break;
                    case MouseEvent.BUTTON3:
                        if(isACellSelected()){
                            BoardPoint pointOnBoard = mouseToBoard(mouseEvent.getPoint());
                            if(board.moveCell(selectedCell,pointOnBoard)){
                                setSelectedCell(pointOnBoard);
                            }
                        }
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
        selectedCell.setY(-1);
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
                ret = new Color(200,0,0);
                break;
            case 2:
                ret = new Color(0,0,200);
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
     * Compute the coordinate of the origin
     * @return the origin point
     */
    private Point getOrigin(){
        return new Point(getAbscissaOrigin(),getOrdinateOrigin());
    }


}
