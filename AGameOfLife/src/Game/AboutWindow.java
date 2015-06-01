/**This file is part of A Game Of Life.

 Game Of Life is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Game Of Life is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Game Of Life.  If not, see <http://www.gnu.org/licenses/>.
 **/


package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AboutWindow extends JFrame {
    protected JLabel labelTitle = new JLabel("A Game Of Life");
    protected JLabel labelAutors = new JLabel("<html><h3>team AGOL:</h3>"+
            "<p><br>Quentin BAPAUME"+
            "<br>Edern HAUMONT"+
            "<br>Youssef MHAMEDI"+
            "<br>Nicolas SIX</p></html>");
    protected JLabel labelLink = new JLabel("www.github.com/anliec/AGameOfLife");
    protected JLabel iconeLabel;
    
    public AboutWindow() {
        setTitle("About us");
        this.setPreferredSize(new Dimension(500,480));
        this.setMinimumSize(new Dimension(500, 480));
        this.setResizable(false);
        Container pane = this.getContentPane();
        BoxLayout layout = new BoxLayout(pane, BoxLayout.PAGE_AXIS);
        pane.setLayout(layout);

        try {
            BufferedImage myPicture = ImageIO.read(new File("Images/AGOL_big.png"));
            iconeLabel = new JLabel(new ImageIcon(myPicture));
        } catch (IOException e) {
            e.printStackTrace();
            iconeLabel = new JLabel("");
        }
        Font font = labelTitle.getFont();
        labelTitle.setFont(new Font(font.getName(),Font.BOLD,30));
        labelLink.setFont(new Font(font.getName(),Font.ITALIC,20));

        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAutors.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelAutors.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        pane.add(labelTitle);
        pane.add(iconeLabel);
        pane.add(Box.createHorizontalGlue());
        pane.add(labelLink);
        pane.add(labelAutors);
        pack();
        setVisible(true);
    }
}
