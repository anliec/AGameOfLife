package Game;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutWindow extends JFrame {
    protected JLabel label = new JLabel("<html>Bienvenue dans notre modeste"
            + " application<br>by AGOL Team</html>");
    
    public AboutWindow() {
        setTitle("A propos");
        this.add(label);
        pack();
        setVisible(true);
    }
}
