package Game;

/**
 * Created by nicolas on 28/03/15.
 */
public class BoardPoint {
    private int x;
    private int y;

    public BoardPoint(){
        setX(0);
        setY(0);
    }

    public BoardPoint(int x, int y){
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
