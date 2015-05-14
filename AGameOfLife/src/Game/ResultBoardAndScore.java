package Game;

/**
 * Created by team AGOL on 14/05/15.
 */
public class ResultBoardAndScore {

    private Board board;
    private int score;

    public ResultBoardAndScore(Board board,int score){
        this.board=board;
        this.score=score;
    }

    public Board getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

}
