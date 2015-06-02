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

/**
 * A basic cell with its team number and its coordinates.
 */
public class Cell implements Cloneable{

    BoardPoint coordinate;
    int team;//0 : sans �quipe, 1 : �quipe 1, 2 : �quipe 2, etc...

    /**
     * standard generator with team assignment
     * @param t team
     */
    public Cell(int t) {
        team = t;
    }

    public Cell(int team, BoardPoint coordinate){
        this.team = team;
        this.coordinate = coordinate;
    }
    
    public BoardPoint getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(BoardPoint coordinate) {
        this.coordinate = coordinate;
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

    public Cell clone(){
        Cell cellCopy = null;
        try{
            cellCopy = (Cell)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }
        cellCopy.setTeam(team);
        cellCopy.setCoordinate(coordinate);
        return cellCopy;
    }

}