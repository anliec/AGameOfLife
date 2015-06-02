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

import java.awt.*;

/**
 * extended from GraphicBoard, this class is an interface specially
 * designed for the placement of the cells by an human player at
 * the beginning of the game.
 *
 * Created by team AGOL on 11/05/15.
 */
public class GraphicBoardIni extends GraphicBoard {

    protected int xMin, xMax, yMin, yMax;
    protected int numberOfCellToPlace;
    protected int currantTeam;

    public GraphicBoardIni(int humanPlayerTeam, int numberOfCell, Options gameOptions){
        super(gameOptions);
        numberOfCellToPlace = numberOfCell;
        init(humanPlayerTeam);
    }

    public GraphicBoardIni(Board sourceBoard,int humanPlayerTeam, int numberOfCell){
        super(sourceBoard);
        numberOfCellToPlace = numberOfCell;
        init(humanPlayerTeam);
    }

    private void init(int humanPlayerTeam){
        currantTeam = humanPlayerTeam;
        int numberOfTeam = board.getTeams().length-1; // minus the dead cell team
        BoardInitializationBoundary boundary = new BoardInitializationBoundary(board,numberOfTeam,currantTeam);
        xMin = boundary.getxMin();
        xMax = boundary.getxMax();
        yMax = boundary.getyMax();
        yMin = boundary.getyMin();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        Point origin = getOrigin();

        //paint rect for the new cell area:
        g2d.setColor(getTeamColor(currantTeam));
        g2d.drawRect(origin.x + xMin*squareSize, origin.y + yMin*squareSize, (xMax-xMin)*squareSize, (yMax-yMin)*squareSize);
    }

    protected void onLeftClick(BoardPoint point){
        if(isOnZone(point)){
            if(board.getCell(point).getTeam() != currantTeam){
                if(numberOfCellToPlace != 0){
                    numberOfCellToPlace--;
                    board.setCell(point, new Cell(currantTeam, point));
                }
            }
        }
    }

    protected void onRightClick(BoardPoint point){
        if(isOnZone(point)){
            if(board.getCell(point).getTeam() == currantTeam){
                numberOfCellToPlace++;
                board.setCell(point, new Cell(0, point));
            }
        }
    }

    public boolean isOnZone(BoardPoint point){
        return point.getX()>=xMin && point.getX()<xMax && point.getY()>=yMin && point.getY()<yMax;
    }


    public int getCurrantTeam() {
        return currantTeam;
    }

}