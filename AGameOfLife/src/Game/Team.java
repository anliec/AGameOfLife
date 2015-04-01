package Game;

import java.util.ArrayList;

public class Team {
    private ArrayList<Cell> cells;

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;
    private boolean IA;
    
    public Team() {
        cells = new ArrayList<Cell>();
        IA=false;
    }
    
    public Team(boolean IA) {
        cells = new ArrayList<Cell>();
        this.IA = IA;
    }

    public Team(boolean IA, Board board){
        cells = new ArrayList<Cell>();
        this.IA = IA;
        this.board = board;
    }
    
    public boolean isIA() {return IA;}

    public ArrayList<Cell> getCells() {
        return cells;
    }
    
    public void play() {
        if (IA){
            playIA();
        }
        else {
            
        }
    }
    
    /**
     * the IA make the best move she can
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
                        Cell current = board.getCell(x, y);
                        switch (board.cellNeighbour(x, y)-1/*itself*/) {
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
                        switch (board.cellExtendedNeighbour(x, y, 0, 2)-1/*itself*/) {
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


}
