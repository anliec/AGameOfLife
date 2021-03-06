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
 * keep together the beginning and end points of a move and its score
 * in order to simplify the management task on the AI.
 */
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
