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

import java.util.LinkedList;

/**
 * BoardIniIA is an AI that trie to found the best beginning in the given
 * board according to the placement rules.
 *
 * Created by team AGOL on 14/05/15.
 */
public class BoardIniIA {
    private final int DEEP = 5;
    protected Board board;
    protected int xMax, xMin, yMax, yMin;
    protected int teamNumber;
    protected int cellToPlace;

    public BoardIniIA(Board board,int teamNumber,int cellToPlace){
        this.board = board;
        BoardInitializationBoundary boundary = new BoardInitializationBoundary(board,board.getTeams().length-1,teamNumber);
        this.xMax = boundary.getxMax();
        this.xMin = boundary.getxMin();
        this.yMax = boundary.getyMax();
        this.yMin = boundary.getyMin();
        this.teamNumber = teamNumber;
        this.cellToPlace = cellToPlace;
    }

    public void iniBoard(){
        BoardPoint firstCellPoint = new BoardPoint(0,0);
        firstCellPoint.setX((xMax + xMin) / 2);
        firstCellPoint.setY((yMax + yMin) / 2);
        board.setCell(firstCellPoint, new Cell(teamNumber, firstCellPoint));
        cellToPlace--;

        if (cellToPlace>0) { //if they are still cells to place (ok it's not a good game with only one cell)
            LinkedList<ResultBoardAndScore> scoreBoard = new LinkedList<>();
            BoardPoint currantPoint;
            Board simulationBoard;
            for (int x = xMin; x < xMax; x++) {
                for (int y = yMin; y < yMax; y++) {
                    currantPoint = new BoardPoint(x,y);
                    if(board.getCell(currantPoint).getTeam()==0){
                        int neighbour = board.cellNeighbour(x,y);
                        if(neighbour>0 && neighbour<4){//don't look for cell in the midle of nothing or where it will die.
                            simulationBoard = board.clone();
                            simulationBoard.setCell(currantPoint, new Cell(teamNumber, currantPoint));
                            scoreBoard.add(getScorePosition(simulationBoard, cellToPlace-1));
                        }
                    }
                }
            }
            if (scoreBoard.size()>0) {
                ResultBoardAndScore bestScore = scoreBoard.getFirst();
                for (int i = 1; i < scoreBoard.size(); i++) {
                    if(scoreBoard.get(i).getScore()>bestScore.getScore()){
                        bestScore = scoreBoard.get(i);
                    }
                }
                board = bestScore.getBoard();
            }
        }
    }

    public ResultBoardAndScore getScorePosition(Board currantBoard, int cellToPlace){
        if(cellToPlace <= 0){
            Board simulationBoard = currantBoard.clone();
            for (int i = 0; i < DEEP ; i++) {
                simulationBoard.computeNextGeneration();
            }
            return new ResultBoardAndScore(currantBoard, simulationBoard.getTeam(teamNumber).cells.size());
        }
        else{
            LinkedList<ResultBoardAndScore> scoreBoard = new LinkedList<>();
            BoardPoint currantPoint;
            Board simulationBoard;
            int neighbour;
            for (int x = xMin; x < xMax; x++) {
                for (int y = yMin; y < yMax; y++) {
                    currantPoint = new BoardPoint(x,y);
                    if(currantBoard.getCell(currantPoint).getTeam()==0){
                        neighbour = board.cellNeighbour(currantPoint);
                        if(neighbour>0 && neighbour<4){//don't look for cell in the middle of nothing or where it will die.
                            currantPoint = new BoardPoint(x,y);
                            simulationBoard = currantBoard.clone();
                            simulationBoard.setCell(currantPoint, new Cell(teamNumber, currantPoint));
                            scoreBoard.add(getScorePosition(simulationBoard, cellToPlace - 1));
                            //System.out.println("score: " + scoreBoard.getLast().getScore()+" cell left: "+cellToPlace);
                        }
                    }

                }
            }
            if (scoreBoard.size()>0) {
                ResultBoardAndScore bestScore = scoreBoard.getFirst();
                for (int i = 1; i < scoreBoard.size(); i++) {
                    if(scoreBoard.get(i).getScore()>bestScore.getScore()){
                        bestScore = scoreBoard.get(i);
                    }
                }
                return bestScore;
            } else {
                return new ResultBoardAndScore(currantBoard,0);//if there are no best score, return a score equal to 0
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}
