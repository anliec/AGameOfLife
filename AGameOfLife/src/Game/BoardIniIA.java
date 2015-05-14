package Game;

import java.util.LinkedList;

/**
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

        LinkedList<ResultBoardAndScore> scoreBoard = new LinkedList<>();
        BoardPoint currantPoint;
        Board simulationBoard;
        for (int x = xMin; x < xMax; x++) {
            for (int y = yMin; y < yMax; y++) {
                int neighbour = board.cellNeighbour(x,y);
                if(neighbour>0 && neighbour<4){//don't look for cell in the midle of nothing or where it will die.
                    currantPoint = new BoardPoint(x,y);
                    simulationBoard = board.clone();
                    simulationBoard.setCell(currantPoint, new Cell(teamNumber, currantPoint));
                    cellToPlace--;
                    scoreBoard.add(getScorePosition(simulationBoard, cellToPlace));
                }
            }
        }
        ResultBoardAndScore bestScore = scoreBoard.getFirst();
        for (int i = 1; i < scoreBoard.size(); i++) {
            if(scoreBoard.get(i).getScore()>bestScore.getScore()){
                bestScore = scoreBoard.get(i);
            }
        }
        board = bestScore.getBoard();
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
                    neighbour = board.cellNeighbour(x,y);
                    if(neighbour>0 && neighbour<4){//don't look for cell in the midle of nothing or where it will die.
                        currantPoint = new BoardPoint(x,y);
                        simulationBoard = currantBoard.clone();
                        simulationBoard.setCell(currantPoint, new Cell(teamNumber, currantPoint));
                        scoreBoard.add(getScorePosition(simulationBoard, cellToPlace - 1));
                        //System.out.println("score: " + scoreBoard.getLast().getScore()+" cell left: "+cellToPlace);
                    }
                }
            }
            ResultBoardAndScore bestScore = scoreBoard.getFirst();
            for (int i = 1; i < scoreBoard.size(); i++) {
                if(scoreBoard.get(i).getScore()>bestScore.getScore()){
                    bestScore = scoreBoard.get(i);
                }
            }
            return bestScore;
        }
    }

    public Board getBoard() {
        return board;
    }
}
