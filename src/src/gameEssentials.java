import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class gameEssentials extends Canvas implements KeyListener
{
    private Rectangle rect;
    private ArrayList<Integer> pressed;
    public void init() {
        this.addKeyListener(this); // use itself
        rect = new Rectangle(250, 250, 50, 50);
        pressed = new ArrayList<>();
    }
    public void paint(Graphics g)
    {
        setSize(500, 500); // set size
        Graphics2D g2 = (Graphics2D) g; // convert graphic (g) to newer/ better graphics library (Graphics2D)
        g2.fill(rect);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (pressed.contains(e.getKeyCode()) && e.getKeyCode() != KeyEvent.VK_SHIFT) pressed.add(e.getKeyCode());
        move();
        repaint();
    }
    public void move()
    {
        int x = rect.x, y = rect.y;
        if (pressed.contains(KeyEvent.VK_A) || pressed.contains(KeyEvent.VK_LEFT)) x -= 5;
        if (pressed.contains(KeyEvent.VK_W) || pressed.contains(KeyEvent.VK_UP)) y -= 5;
        if (pressed.contains(KeyEvent.VK_S) || pressed.contains(KeyEvent.VK_DOWN)) y += 5;
        if (pressed.contains(KeyEvent.VK_D) || pressed.contains(KeyEvent.VK_RIGHT)) x += 5;
        rect.setLocation(x, y);
    }
    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}