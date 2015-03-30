package Game;

public class Cell {
    BoardPoint coordinate;
    int team;//0 : sans �quipe, 1 : �quipe 1, 2 : �quipe 2, etc...

    /**
     * standard generator with team assignment
     * @param t team
     */
    public Cell(int t) {
        team = t;
    }
    
    public BoardPoint getCoordinate() {
        return coordinate;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
    
    public String toString() {
        return Integer.toString(team);
    }

    public boolean isAlive(){
        return team!=0;
    }

}