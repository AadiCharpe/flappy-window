import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.start();
    }
}

class Manager {
    private double yv = -1;
    private ArrayList<Pipe> pipes;
    private int spawnTime = 0;
    public void start() {
        Bird bird = new Bird();
        pipes = new ArrayList<>();
        bird.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    yv = 9;
                }
            }
        });

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) size.getHeight() / 2 + 160;

        new Timer(1000 / 60, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    bird.setLocation(bird.getX(), bird.getY() - (int) Math.floor(yv));
                    if(yv > -15)
                        yv -= 0.4;
                    if(bird.getY() <= 50 || bird.getY() + bird.getHeight() >= size.getHeight())
                        System.exit(0);
                    for(int i = 0; i < pipes.size(); i++) {
                        Pipe current = pipes.get(i);
                        current.setLocation(current.getX() - 4, current.getY());
                        if(current.getX() <= 0) {
                            current.dispose();
                            pipes.remove(current);
                        }
                    }
                    spawnTime++;
                    if(spawnTime >= 110) {
                        int offset = (int) (Math.random() * (size.getHeight() / 2)) - (int) (size.getHeight() / 4     );

                        Pipe pipe = new Pipe((int) size.getWidth() - 100, y + offset, 100, (int) size.getHeight() - (y + offset), false);
                        pipes.add(pipe);
                        pipe.setFocusableWindowState(false);
                        pipe.setVisible(true);

                        Pipe pipe2 = new Pipe((int) size.getWidth() - 100, 0, 100, (int) size.getHeight() - (y - offset), true);
                        pipes.add(pipe2);
                        pipe2.setFocusableWindowState(false);
                        pipe2.setVisible(true);
                        
                        spawnTime = 0;
                    }
                    Thread.sleep(1000 / 120);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        bird.show();
    }
}

class Bird extends JFrame {
    public Bird() {
        setDefaultCloseOperation(3);
        setBounds(100, 200, 90, 100);
        add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("bird.png").getScaledInstance(106, 72, Image.SCALE_SMOOTH))));
    }
}

class Pipe extends JFrame {
    public Pipe(int x, int y, int w, int h, boolean flipped) {
        setBounds(x, y, w, h);
        add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(flipped ? "pipe-top.png" : "pipe-bottom.png").getScaledInstance(w, h - 30, Image.SCALE_SMOOTH))));
    }
}