package Game;

import java.util.ArrayList;

public class Team implements Cloneable {

    protected ArrayList<Cell> cells;
    protected Board board;
    protected boolean IA;
    protected boolean played;

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public Team() {
        cells = new ArrayList<Cell>();
        IA=false;
        played = false;
    }
    
    public Team(boolean IA) {
        cells = new ArrayList<Cell>();
        this.IA = IA;
        played = false;
    }

    public Team(boolean IA, Board board){
        cells = new ArrayList<Cell>();
        this.IA = IA;
        this.board = board;
        played = false;
    }
    
    public boolean isIA() {return IA;}

    public void setIA(boolean IA){
        this.IA = IA;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells){
        this.cells = cells;
    }
    
    public void play() {
        if (IA){
            playIA();
            played = true;
        }
        else {
            
        }
    }
    
    /**
     * the IA make the best move it can
     * @author Edern Haumont
     */
    public void playIA() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for(int i=0; i<cells.size(); i++) {
            for(int r=-1; r<2; r++) {
                for(int c=-1; c<2; c++) {
                    int x = cells.get(i).getCoordinate().getX()+c;
                    int y = cells.get(i).getCoordinate().getY()+r;
                    if(x >=0 && y >=0 && x < board.getWidth() && y < board.getHeight()
                            && !board.getCell(x, y).isAlive()) {
                        int score = 0;
                        //Cell current = board.getCell(x, y); //never used
                        // move the cell on a temp board to be sure of the score result:
                        Board tempBoard = new Board(board.getCellBoard().clone());
                        tempBoard.moveCell(cells.get(i).getCoordinate(), new BoardPoint(x,y));
                        switch (tempBoard.cellNeighbour(x, y)/*-1/*itself*/) { //useless, already counted on cellNeighbour
                        case 2:
                            score = 5;
                            break;
                        case 3:
                            score = 10;
                            break;
                        default:
                            score = 0;
                            break;
                        }
                        switch (tempBoard.cellExtendedNeighbour(x, y, 0, 2)/*-1/*itself*/) { //useless, already counted on cellNeighbour
                        case 6:
                            score += 3;
                            break;
                        case 7:
                            score += 4;
                            break;
                        case 8:
                            score += 5;
                            break;
                        case 9:
                            score += 4;
                            break;
                        case 10:
                            score += 3;
                            break;
                        default:
                            break;
                        }
                        moves.add(new Move(cells.get(i).getCoordinate(), new BoardPoint(x,y), score));
                    }
                }
            }
        }
        Move finalMove = moves.get(0);
        for(int i=1; i<moves.size(); i++) {
            if(moves.get(i).score>finalMove.score)
                finalMove = moves.get(i);
        }
        if(finalMove.score!=0) {
            board.moveCell(finalMove.from, finalMove.to);
            System.out.println(finalMove.score);
        }
    }

    public int getTeamNumber(){
        if(!cells.isEmpty()){
            return cells.get(0).getTeam();
        }
        else{
            return -1; //no cells no team...
        }
    }

    public void setPlayed(boolean played){
        this.played = played;
    }

    public  boolean getPlayed(){
        return played;
    }

    public Team clone(){
        Team team = null;
        try{
            team = (Team)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        team.setBoard(board); //if board.clone() -> infinity loop
        ArrayList<Cell> copiedCell = new ArrayList<Cell>();
        for (int i = 0; i < cells.size(); i++) {
            copiedCell.add(cells.get(i).clone());
        }
        team.setCells(copiedCell);
        team.setIA(IA);
        team.setPlayed(played);
        return team;
    }
}
