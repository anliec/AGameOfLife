package Game;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by nicolas on 11/04/15.
 */

///////////////////////////////////////
/// created for IA testing purpose
///////////////////////////////////////

public class TeamBasicIA extends Team {

    private int iterationNumber;

    public TeamBasicIA(){
        super();
        iterationNumber = 2;
    }

    public TeamBasicIA(boolean isIA){
        super(isIA);
        iterationNumber = 2;
    }

    public TeamBasicIA(boolean isIA, Board board){
        super(isIA,board);
        iterationNumber = 2;
    }

    public TeamBasicIA(boolean isIA, Board board, int iterationIA){
        super(isIA,board);
        iterationNumber = iterationIA;
    }

    @Override
    public void playIA(){
        LinkedList<Move> moves = new LinkedList<Move>();
        Board simulationBoard = board.clone();
        for (int c = 0; c < cells.size() ; c++) {
            Cell cell = cells.get(c);
            for (int x = -1; x < 1; x++) {
                for (int y = -1; y < 1; y++) {
                    BoardPoint currantPoint = new BoardPoint(cell.getCoordinate().getX()+x,cell.getCoordinate().getY()+y);
                    if(simulationBoard.isOnBoard(currantPoint) && !simulationBoard.getCell(currantPoint).isAlive()) {
                        Move currantMove = new Move(cell.getCoordinate(),currantPoint,0);
                        currantMove.score = getScoreMove(currantMove, 3);
                        moves.add(currantMove);
                    }
                }
            }
        }
        Move finalMove = moves.getFirst();
        for(int i=1; i<moves.size(); i++) {
            if(moves.get(i).score>finalMove.score)
                finalMove = moves.get(i);
        }
        if(finalMove != null){
            if(finalMove.score>0) {
                board.moveCell(finalMove);
                System.out.println(finalMove);
            }
        }
    }

    public int getScoreMove(Move move){
        Board simulationBoard = board.clone();
        if(simulationBoard.moveCell(move)){
            simulationBoard.computeNextGeneration();
            int cellNumber;
            cellNumber = simulationBoard.getTeam(getTeamNumber()).getCells().size();
            return cellNumber;
        }
        else{
            return -1; //illegal move
        }
    }

    public int getScoreMove(Move move, int iteration){
        return getScoreMove(move,iteration,board);
    }

    public int getScoreMove(Move move,int iteration, Board sourceBoard){
        Board simulationBoard = sourceBoard.clone();
        if(simulationBoard.moveCell(move)){
            simulationBoard.computeNextGeneration();
            if(simulationBoard.getTeam(getTeamNumber()).getCells().size() <= 0.8*sourceBoard.getTeam(getTeamNumber()).getCells().size()){
                return 0;
            }
            else if(iteration == 0){
                int cellNumber;
                cellNumber = simulationBoard.getTeam(getTeamNumber()).getCells().size();
                return cellNumber;
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
                return -1; // if no finalMove or a negative score
            }
        }
        else{
            return -1; //illegal move
        }
    }

}