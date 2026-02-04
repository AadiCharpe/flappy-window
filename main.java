import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.start();
    }
}

class Manager {
    private double yv = -1;
    public void start() {
        Bird bird = new Bird();
        bird.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    yv = 10;
                }
            }
        });
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        bird.setLocation(bird.getX(), bird.getY() - (int) Math.floor(yv));
                        if(yv > -15)
                            yv -= 0.4;
                        Thread.sleep(1000 / 60);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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