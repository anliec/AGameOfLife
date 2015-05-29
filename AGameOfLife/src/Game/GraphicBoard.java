package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Graphic Board contains the board plus some graphic and event elements
 * @author team AGOL
 */
public class GraphicBoard extends JPanel {

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
     * Default constructor: load the Board:"AGameOfLife/Boards/[theBoardName]"
     */
    public GraphicBoard(Options gameOptions){
        voidBoardInit(10,10);
        this.gameOptions = gameOptions;
    }

    public GraphicBoard(int width, int height, Options gameOptions){
        voidBoardInit(width, height);
        this.gameOptions = gameOptions;
    }

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

    protected void onLeftClick(BoardPoint point){
        setSelectedCell(point);
    }

    protected void onRightClick(BoardPoint point){
        if(isACellSelected()){
            if(board.playCurrentHumanTurn(new Move(selectedCell,point,0))){
                setSelectedCell(point);
            }
        }
    }

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
            System.out.println(notificationText);
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

    public void endHumanPlayerTurn(){
        addNotification("AI is thinking...", 1000);
        unselectCell();
        board.endHumanPlayerTurn();
        addNotification("It's Player " + board.getCurrentPlayer() + " turn", 700);
        repaint();
    }

    /**
     * Compute the coordinate of the origin
     * @return the origin point
     */
    protected Point getOrigin(){
        return new Point(getAbscissaOrigin(),getOrdinateOrigin());
    }

    public void setBoardFromFile(String path, char sep){
        init(new Board(path,sep,gameOptions));
        repaint();
    }

    public void saveBoardToFile(String path, char sep){
        board.writeBoardToFile(path, sep);
    }


}
