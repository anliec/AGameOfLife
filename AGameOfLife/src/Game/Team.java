package Game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Team implements Cloneable {

    protected ArrayList<Cell> cells;
    protected Board board;
    protected boolean IA;
    protected boolean played;
    protected int teamNumber;

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
        teamNumber = getTeamNumber();
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
        /*ArrayList<Move> moves = new ArrayList<Move>();
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
                        switch (tempBoard.cellNeighbour(x, y)) {
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
                        switch (tempBoard.cellExtendedNeighbour(x, y, 0, 2)) {
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
        }*/
        if(cells.size()==0){
            return;
        }
        LinkedList<Move> moves = new LinkedList<Move>();
        Board simulationBoard = board.clone();
        for (int c = 0; c < cells.size() ; c++) {
            Cell cell = cells.get(c);
            for (int x = -1; x < 1; x++) {
                for (int y = -1; y < 1; y++) {
                    BoardPoint currentPoint = new BoardPoint(cell.getCoordinate().getX()+x,cell.getCoordinate().getY()+y);
                    if(simulationBoard.isOnBoard(currentPoint) && !simulationBoard.getCell(currentPoint).isAlive()) {
                        Move currentMove = new Move(cell.getCoordinate(),currentPoint,0);
                        currentMove.score = getScoreMove(currentMove, 5);
                        moves.add(currentMove);
                    }
                }
            }
        }
        if(moves.size()>0){
            Move finalMove = moves.getFirst();
            for(int i=1; i<moves.size(); i++) {
                if(moves.get(i).score>finalMove.score)
                    finalMove = moves.get(i);
            }
            if(finalMove != null){
                if(finalMove.score>0) {
                    board.moveCell(finalMove);
                }
            }
        }
    }
    
    public int getScoreMove(Move move, int iteration){
        return getScoreMove(move,iteration,board);
    }

    public int getScoreMove(Move move,int iteration, Board sourceBoard){
        Board simulationBoard = sourceBoard.clone();
        if(simulationBoard.moveCell(move)){
            simulationBoard.computeNextGeneration();
            if(simulationBoard.getTeam(getTeamNumber()).getCells().size() <= 0.9*sourceBoard.getTeam(getTeamNumber()).getCells().size()){
                return 0;// if very bad move
            }
            else if(iteration == 0){
                int friendNumber, ennemyNumber = 0;
                friendNumber = simulationBoard.getTeam(getTeamNumber()).getCells().size();
                for (int i=1; i<simulationBoard.getTeams().length; i++) {
                    if(teamNumber !=i) {
                        ennemyNumber += simulationBoard.getTeam(i).getCells().size();
                        //System.out.println(ennemyNumber);
                    }
                }
                return friendNumber/(ennemyNumber+1);//classic score
            }
            else{
                LinkedList<Move> moves = new LinkedList<Move>();
                for (int c = 0; c < cells.size() ; c++) {
                    Cell cell = cells.get(c);
                    for (int x = -1; x < 1; x++) {
                        for (int y = -1; y < 1; y++) {
                            BoardPoint currantPoint = new BoardPoint(cell.getCoordinate().getX()+x,cell.getCoordinate().getY()+y);
                            if(simulationBoard.isOnBoard(currantPoint) && !simulationBoard.getCell(currantPoint).isAlive()) {
                                Move currantMove = new Move(cell.getCoordinate(),currantPoint,0);
                                currantMove.score = getScoreMove(currantMove, iteration-1, simulationBoard);
                                moves.add(currantMove);
                            }
                        }
                    }
                }
                if(moves.size()>0){
                    Move finalMove = moves.getFirst();
                    for(int i=1; i<moves.size(); i++) {
                        if(moves.get(i).score>finalMove.score)
                            finalMove = moves.get(i);
                    }
                    if(finalMove != null){
                        if(finalMove.score>0) {
                            return finalMove.score;
                        }
                    }
                }
                return -1; // if no finalMove or a negative score
            }
        }
        else{
            return -1; //illegal move
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
