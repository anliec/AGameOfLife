package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by nicolas on 21/03/15.
 */
public class GraphicBoard extends JPanel {

    private Board board;
    private int squareSize;

    /**
     * Default constructor: load the Board:"AGameOfLife/Boards/TestBoard1"
     */
    public GraphicBoard(){
        init(new Board("AGameOfLife/Boards/TestBoard1", ' ', 5, 5));
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
        setSquareSize();// to be sure that squareSize is not zero
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
