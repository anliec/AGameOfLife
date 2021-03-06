/**
 * This file is part of A Game Of Life.
 *
 * Game Of Life is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Game Of Life is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Game Of Life.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/

package Game;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Management of the teams including the IA for non human player
 */
public class Team implements Cloneable {

    protected ArrayList<Cell> cells;
    protected Board board;
    protected boolean IA;
    protected boolean played;
    protected int teamNumber;
    protected final int IAIterations = 7;
    protected final double IAIntolerance = 0.8;
    protected final int IADivider = 6;
    protected int IAFloor = 1;

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
    }
    
    /**
     * the IA make the best move it can
     */
    public void playIA() {
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
                        int iter = IAIterations-cells.size()/IADivider;
                        if(iter < IAFloor) iter = IAFloor;
                        currentMove.score = getScoreMove(currentMove, iter);
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
            if(simulationBoard.getTeam(getTeamNumber()).getCells().size() <= IAIntolerance*sourceBoard.getTeam(getTeamNumber()).getCells().size()){
                return 0;// if very bad move
            }
            else if(iteration == 0){
                int friendNumber, ennemyNumber = 0;
                friendNumber = simulationBoard.getTeam(getTeamNumber()).getCells().size();
                /**for (int i=1; i<simulationBoard.getTeams().length; i++) {
                    if(teamNumber !=i) {
                        ennemyNumber += simulationBoard.getTeam(i).getCells().size();
                    }
                }
                System.out.println(ennemyNumber);*/
                return friendNumber;/**-(ennemyNumber/3);*///classic score
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
