package Game;

public class Move {
    public int score;
    public BoardPoint from;
    public BoardPoint to;
    
    public Move(BoardPoint from, BoardPoint to, int score) {
        this.from=from;
        this.to=to;
        this.score=score;
    }

    public String toString(){
        return from+" -> "+to+" score: "+score;
    }
}
