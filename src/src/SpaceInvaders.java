import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class SpaceInvaders extends JFrame implements KeyListener, Runnable {
    int moveDist = 7;
    JLabel label;
    boolean wait = true;
    //ImageIcon icon;
    int minX = 0;
    int maxX = 9;
    int numInMaxX = 5;
    int numInMinX = 5;
    int movedX = 0;
    int movedY = 0;
    JLabel[][] aliens;
    ArrayList<JLabel> bullets;
    boolean ended = false;
    HashSet<Integer> pressed;
    SpaceInvaders() {
        System.out.println("SpaceInvaders");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1175, 1000);
        this.setLayout(null);
        this.addKeyListener(this);
        this.getContentPane().setBackground(Color.BLACK);

        label = new JLabel();
        label.setBounds(500, 850, 100, 50);
        label.setBackground(Color.GREEN);
        label.setOpaque(true);
        // icon = new ImageIcon("")
        //label.setIcon(icon);
        this.add(label);
        pressed = new HashSet<>();
        bullets = new ArrayList<>();

        aliens = new JLabel[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                aliens[i][j] = new JLabel();
                aliens[i][j].setBounds((j*100)+100, (i*80)+120, 60, 50);
                aliens[i][j].setBackground(Color.GREEN);
                aliens[i][j].setOpaque(true);
                this.add(aliens[i][j]);
            }
        }
        this.setVisible(true);
        run();
    }

    public void run() {
        while (!ended) {
            System.out.println("game loop");
            try {
                while (wait) {
                    Thread.sleep(100);
                }
                wait = true;
            }catch (InterruptedException ignored) {}
            moveBullets();
            moveAliens();
        }
        endGame();
    }

    public void moveBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            JLabel bullet = bullets.get(i);
            bullet.setLocation(bullet.getX(), bullet.getY()-10);
            if (bullet.getY() < -100) {
                bullets.remove(bullet);
                i--;
            }
            System.out.println(bullet.getX() + " " + bullet.getY() + " " + movedX + " " + movedY + " " + (bullet.getX()/100) + " " + (bullet.getY()/100));
            System.out.println((bullet.getX() % 100) + " >= " + movedX + "    " + (bullet.getX() % 100) + " <= " + (60 + movedX));
            System.out.println((bullet.getY() % 100) + ">=" + movedY + "     " + (bullet.getX() % 100) + " <= " + (50 + movedY));
            System.out.println();
            try {
                if (isHit(bullet)) {
                    bullets.remove(bullet);
                    this.remove(aliens[(bullet.getY() - movedY) / 100][(bullet.getX() - movedY) / 100]);
                    aliens[(bullet.getY() - movedY) / 100][(bullet.getX() - movedY) / 100] = null;
                    if (bullet.getX() / 100 == minX) numInMinX--;
                    if (bullet.getX() / 100 == maxX) numInMaxX--;
                    while (numInMinX == 0) minX++;
                    while (numInMaxX == 0) maxX--;
                }
            }catch (Exception ignored) {}
        }
    }
    public boolean isHit(JLabel bullet) {
        return (bullet.getX() % 100) >= movedX
                && (bullet.getX() % 100) <= 60 + movedX
                && (bullet.getY() % 100) >= movedY
                && (bullet.getY() % 100) < 50 + movedY
                && aliens[(bullet.getY() - movedY) / 100][(bullet.getX() - movedY) / 100] != null;
    }
    public void moveAliens() {
        movedX += moveDist;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (aliens[i][j] != null)
                aliens[i][j].setLocation(aliens[i][j].getX() + moveDist, aliens[i][j].getY());
            }
        }
        if (aliens[0][minX].getX() <= 30 || aliens[0][maxX].getX() >= 1085) {
            moveDist = -moveDist;
            advancingAliens();
        }
    }

    public void advancingAliens() {
        movedY += 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (aliens[i][j] != null)
                    aliens[i][j].setLocation(aliens[i][j].getX(), aliens[i][j].getY() + 5);
            }
        }
    }

    public void endGame() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
        System.out.println("Keylistener");
        if (e.getKeyCode() == KeyEvent.VK_0) wait = false;
        move();
    }

    public void move() {
        int x = label.getX();
        int y = label.getY();
        if (pressed.contains(32)) {
            System.out.println("Shoot");
            JLabel newBullet = new JLabel();
            newBullet.setBounds(x+47, 850, 6, 20);
            newBullet.setBackground(Color.GREEN);
            newBullet.setOpaque(true);
            this.add(newBullet);
            bullets.add(newBullet);
        }
        if ((pressed.contains(65) || pressed.contains(37)) && x >= 50) x -= 5;
        if ((pressed.contains(68) || pressed.contains(39)) && x <= 1025) x += 5;
        label.setLocation(x, y);
        System.out.println(x + " " + y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}
//fix bullet detection
//add icons
//fix spam bullets
// fix not moving after releasing shoot
//Add end game detection
//add title
//add losing screen
//add winning screen/fix everything else