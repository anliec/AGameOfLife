import java.awt.Color;

import javax.swing.JFrame;


public class MyWindow extends JFrame{

	public MyWindow() {
		this.setTitle("Xaxetrov's Life Game");
	    this.setSize(1018, 1047);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setAlwaysOnTop(true);
	    this.setBackground(Color.lightGray);
	    this.setContentPane(new MyPanel());
	    this.setVisible(true);
	}
	
}
