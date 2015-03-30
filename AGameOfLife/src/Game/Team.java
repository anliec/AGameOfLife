package Game;

import java.util.ArrayList;

public class Team {
    private ArrayList<Cell> cells;
    private Board board;
    private boolean IA;
    
    public Team() {
        cells = new ArrayList<Cell>();
    }
    
    public boolean isIA() {return IA;}

    public ArrayList<Cell> getCells() {
        return cells;
    }
    
    public void play() {
        if (IA){
            playIA();
        }
    }
    
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
                        
                    }
                }
            }
        }
        for(int i=0; i<moves.size(); i++) {
            
        }
    }


}
