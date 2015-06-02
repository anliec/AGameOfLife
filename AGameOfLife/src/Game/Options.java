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
 * contains all the options needed for a new game.
 *
 * Created by team AGOL on 14/05/15.
 */
public class Options implements Cloneable{

    protected int boardHeight;
    protected int boardWidth;
    protected LinkedList<Boolean> teamsIA;
    protected int numberOfCellBeginning;

    /**
     * default constructor: set default value, it's recommended to
     * use setters to make the options suit your needs after it.
     */
    public Options(){
        boardHeight = 10;
        boardWidth = 10;
        teamsIA = new LinkedList<>();
        numberOfCellBeginning = 5;
        teamsIA.add(true);//dead cell
        teamsIA.add(true);
        teamsIA.add(false);
        teamsIA.add(true);
        teamsIA.add(true);
        //teamsIA.add(false);
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

    public void setTeamsIAFromOptions(LinkedList<Boolean> teamsIAFromOptions){
        setTeamsIA(teamsIAFromOptions);
        teamsIA.addFirst(false); //add the dead team cell as an human player
    }

    public int getNumberOfCellBeginning() {
        return numberOfCellBeginning;
    }

    public void setNumberOfCellBeginning(int numberOfCellBeginning) {
        this.numberOfCellBeginning = numberOfCellBeginning;
    }

    /**
     * 
     */
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
