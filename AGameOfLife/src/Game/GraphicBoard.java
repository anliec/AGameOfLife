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

    public GraphicBoard(){
        init(new Board("AGameOfLife/Boards/TestBoard1", ' ', 5, 5));
    }

    public GraphicBoard(Board sourceBoard){
        init(sourceBoard);
    }

    private void init(Board sourceBoard){
        board = sourceBoard;
        setMinimumSize(new Dimension(100,100));
        addComponentListener(new ComponentAdapter() { //to get the resize event in order to keep squareSize updated
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                setSquareSize();
            }
        });
        setSquareSize();// to be sure that squareSize is not zero
    }

    public void setSquareSize(){
        double a = getSize().getHeight() / board.getHeight();
        double b = getSize().getWidth() / board.getWidth();
        squareSize = (int)(Math.min(a,b));
    }

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

    private int getAbscissaOrigin(){
        int cellWidth = squareSize * board.getWidth();
        return (getWidth()-cellWidth)/2;
    }

    private int getOrdinateOrigin(){
        int cellHeight= squareSize * board.getHeight();
        return (getHeight()-cellHeight)/2;
    }

    private Point getOrigin(){
        return new Point(getAbscissaOrigin(),getOrdinateOrigin());
    }

}
