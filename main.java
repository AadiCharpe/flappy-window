import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.Toolkit;

public class main {
    public static void main(String[] args) {
        Bird bird = new Bird();
        bird.show();
    }
}

class Bird extends JFrame {
    public Bird() {
        setDefaultCloseOperation(3);
        setBounds(100, 200, 150, 135);
        add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("bird.png").getScaledInstance(160, 108, Image.SCALE_SMOOTH))));
    }
}