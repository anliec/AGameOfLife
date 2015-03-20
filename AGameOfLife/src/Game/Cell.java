package Game;

public class Cell {
    int x; // colonne
    int y; // ligne
    int team;//0 : sans équipe, 1 : équipe 1, 2 : équipe 2, etc...

    /**
     * standart generator with team assignement
     * @param t team
     */
    public Cell(int t) {
        team = t;
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

}

