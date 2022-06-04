import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.awt.Graphics2D;

public class KeyListenerDemo extends JFrame implements KeyListener {
    JLabel label;
    //ImageIcon icon;

    HashSet<Integer> pressed;
    KeyListenerDemo() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(214, 237);
        this.setLayout(null);
        this.addKeyListener(this);

        label = new JLabel();
        label.setBounds(0, 0, 100, 100);
        //label.setBackground(Color.RED);
        label.setOpaque(true);
        this.setContentPane(new JLabel(new ImageIcon("start")));
        // icon = new ImageIcon("")
        //label.setIcon(icon);
        this.getContentPane().setBackground(Color.CYAN);
        this.add(label);
        this.setVisible(true);

        pressed = new HashSet<>();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
        move();
    }

    public void move() {
        int x = label.getX();
        int y = label.getY();
        if ((pressed.contains(65) || pressed.contains(37)) && x != 0) x -= 1;
        if ((pressed.contains(87) || pressed.contains(38)) && y != 0) y -= 1;
        if ((pressed.contains(83) || pressed.contains(40)) && y != 100) y += 1;
        if ((pressed.contains(68) || pressed.contains(39)) && x != 100) x += 1;
        label.setLocation(x, y);
        System.out.println(x + " " + y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }

}
