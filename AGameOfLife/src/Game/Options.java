package Game;

import java.util.LinkedList;

/**
 * Created by team AGOL on 14/05/15.
 */
public class Options implements Cloneable{

    protected int boardHeight;
    protected int boardWidth;
    protected LinkedList<Boolean> teamsIA;
    protected int numberOfCellBeginning;

    public Options(){
        boardHeight = 10;
        boardWidth = 10;
        teamsIA = new LinkedList<>();
        numberOfCellBeginning = 5;
        teamsIA.add(true);//dead cell
        teamsIA.add(true);
        teamsIA.add(false);
        //teamsIA.add(true);
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public LinkedList<Boolean> getTeamsIA() {
        return teamsIA;
    }

    public void setTeamsIA(LinkedList<Boolean> teamsIA) {
        this.teamsIA = teamsIA;
    }

    public int getNumberOfCellBeginning() {
        return numberOfCellBeginning;
    }

    public void setNumberOfCellBeginning(int numberOfCellBeginning) {
        this.numberOfCellBeginning = numberOfCellBeginning;
    }

    public Options clone(){
        Options clonedOption = null;
        try{
            clonedOption = (Options)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        clonedOption.setBoardHeight(boardHeight);
        clonedOption.setBoardWidth(boardWidth);
        clonedOption.setNumberOfCellBeginning(numberOfCellBeginning);
        LinkedList<Boolean> clonedTeam = new LinkedList<>();
        for (int i = 0; i < teamsIA.size(); i++) {
            clonedTeam.add(teamsIA.get(i));
        }
        clonedOption.setTeamsIA(clonedTeam);
        return clonedOption;
    }
}
