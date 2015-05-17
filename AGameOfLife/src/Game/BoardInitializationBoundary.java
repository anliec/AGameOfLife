package Game;

/**
 * Created by team AGOL on 14/05/15.
 */
public class BoardInitializationBoundary {

    protected int xMin, xMax, yMin, yMax;

    public BoardInitializationBoundary(Board board, int numberOfTeam, int currantTeam){
        if(numberOfTeam ==2){
            xMin = 0;
            xMax = board.width;
            if(currantTeam == 1){
                yMin = 0;
                yMax = board.height/2;
            }
            else if(currantTeam == 2){
                yMin = board.height - board.height/2;
                yMax = board.height;
            }
        }
        else if(numberOfTeam == 4 || numberOfTeam == 3){
            if(currantTeam == 1 || currantTeam == 3){
                xMin = 0;
                xMax = board.width/2;
            }
            else{
                xMin = board.width - board.width/2;
                xMax = board.width;
            }
            if(currantTeam == 1 || currantTeam == 2){
                yMin = 0;
                yMax = board.height/2;
            }
            else{
                yMin = board.height - board.height/2;
                yMax = board.height;
            }
        }
        //System.out.println("x: "+xMax+" "+xMin+" y: "+yMax+" "+yMin);
    }

    public int getxMin() {
        return xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }
}