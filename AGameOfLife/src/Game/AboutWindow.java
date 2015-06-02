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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Classical about windows, with the name of the project, a link to
 * the sources, the name of the authors and licenses information.
 */
public class AboutWindow extends JFrame {
    protected JLabel labelTitle = new JLabel("A Game Of Life");
    protected JLabel labelAutors1 = new JLabel("team AGOL:");
    protected JLabel labelAutors2 = new JLabel("Quentin BAPAUME, Edern HAUMONT, Youssef MHAMEDI, Nicolas SIX");

    protected JLabel labelLink = new JLabel("www.github.com/anliec/AGameOfLife");
    protected JLabel iconeLabel;
    protected JLabel licenseLabel= new JLabel("Copyright © 2015 Team AGOL");
    protected JLabel licenseLabel2= new JLabel("This program is distributed in the hope that it will be useful, " +
            "but WITHOUT ANY WARRANTY");
    protected JLabel licenseLabel3 = new JLabel("See the GNU General Public License for more details.");
    
    public AboutWindow() {
        setTitle("About us");
        this.setPreferredSize(new Dimension(500,450));
        this.setMinimumSize(new Dimension(500, 450));
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
        labelAutors1.setFont(new Font(font.getName(),Font.BOLD,10));
        labelAutors2.setFont(new Font(font.getName(), Font.PLAIN, 10));
        licenseLabel.setFont(new Font(font.getName(),Font.ITALIC,10));
        licenseLabel2.setFont(new Font(font.getName(),Font.ITALIC,10));
        licenseLabel3.setFont(new Font(font.getName(),Font.ITALIC,10));

        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAutors1.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelAutors2.setAlignmentX(Component.CENTER_ALIGNMENT);
        licenseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        licenseLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        licenseLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(labelTitle);
        pane.add(iconeLabel);
        pane.add(labelLink);
        pane.add(Box.createRigidArea(new Dimension(20,20)));
        pane.add(labelAutors1);
        pane.add(labelAutors2);
        pane.add(Box.createRigidArea(new Dimension(20,20)));
        pane.add(licenseLabel);
        pane.add(licenseLabel2);
        pane.add(licenseLabel3);
        pack();
        setVisible(true);
    }
}
