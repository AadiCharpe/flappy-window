import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    private int spawnTime = 110;
    private Timer tick;
    private gameOver gameOverScreen;
    private Bird bird;
    public void start() {
        bird = new Bird();
        pipes = new ArrayList<>();
        gameOverScreen = new gameOver(this);
        bird.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    yv = 9;
            }
        });

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int y = (int) size.getHeight() / 2 + 160;

        tick = new Timer(1000 / 60, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    bird.setLocation(100, bird.getY() - (int) Math.floor(yv));
                    if(yv > -15)
                        yv -= 0.4;
                    if(bird.getY() <= 50 || bird.getY() + bird.getHeight() >= size.getHeight())
                        stop();
                    for(int i = 0; i < pipes.size(); i++) {
                        Pipe current = pipes.get(i);
                        int birdX = bird.getX(), birdY = bird.getY(), birdW = bird.getWidth(), birdH = bird.getHeight(), pipeX = current.getX(), pipeY = current.getY(), pipeW = current.getWidth(), pipeH = current.getHeight();
                        if(birdX + birdW >= pipeX && birdX <= pipeX + pipeW && birdY + birdH >= pipeY + 30 && pipeY + pipeH >= birdY + 30)
                            stop();
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
        });
        tick.start();
        bird.show();
    }
    public void stop() {
        tick.stop();
        gameOverScreen.setVisible(true);
    }

    public void restart() {
        gameOverScreen.setVisible(false);
        bird.setLocation(100, 200);
        yv = -1;
        for(int i = 0; i < pipes.size(); i++)
            pipes.get(i).dispose();
        pipes.clear();
        spawnTime = 110;
        tick.start();
    }
}

class Bird extends JFrame {
    public Bird() {
        setDefaultCloseOperation(3);
        setBounds(100, 200, 80, 95);
        add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("bird.png").getScaledInstance(100, 68, Image.SCALE_SMOOTH))));
    }
}

class Pipe extends JFrame {
    public Pipe(int x, int y, int w, int h, boolean flipped) {
        setBounds(x, y, w, h);
        add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(flipped ? "pipe-top.png" : "pipe-bottom.png").getScaledInstance(w, h - 30, Image.SCALE_SMOOTH))));
    }
}

class gameOver extends JFrame {
    public gameOver(Manager manager) {
        setDefaultCloseOperation( 3);
        setSize(250, 150);
        setLocationRelativeTo(null);

        JLabel text = new JLabel("Game Over", SwingConstants.CENTER);
        text.setFont(new Font("Sanserif", Font.PLAIN, 36));
        text.setForeground(Color.RED);
        add(text);
        JPanel panel = new JPanel();

        JButton restart = new JButton("Restart");
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manager.restart();
            }
        });
        panel.add(restart);
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(close);

        add(panel, "South");
    }
}