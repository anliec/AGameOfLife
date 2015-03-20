import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class MyPanel extends JPanel{

	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		int nOCol = Earth.world.length, nOLin = Earth.world[0].length;
		int tileXSize = this.getHeight()/nOCol;
		int tileYSize = this.getWidth()/nOLin;
		for (int i=0; i<nOLin; i++){
			for (int j=0; j<nOCol; j++){
				if(Earth.world[i][j] == 1)
					//x1, y1, width, height
				    g2d.fillRect(j*tileYSize, i*tileXSize, tileYSize, tileXSize);
				if(Earth.world[i][j] == 2)
					g2d.fillOval((int)((j+0.25)*tileYSize), (int)((i+0.25)*tileXSize), tileYSize/2, tileXSize/2);
			}
		}
	}
}
