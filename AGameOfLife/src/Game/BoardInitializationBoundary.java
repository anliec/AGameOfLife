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
 * manage the size and position of the beginning zone according to
 * the size of the board, the number of teams and the currant team.
 *
 * Created by team AGOL on 14/05/15.
 */
public class BoardInitializationBoundary {

    protected int xMin, xMax, yMin, yMax;

    /**
     * generate the boundaries of the placement zone.
     * @param board the board on which the zone will be defined
     * @param numberOfTeam number of teams on the board
     * @param currantTeam number of the team corresponding to the boundaries
     */
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
