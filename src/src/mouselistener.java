import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class mouselistener extends JFrame implements MouseListener {
    JLabel label;
    boolean pressed = false;
    boolean entered = false;
    Color[] colors;
    mouselistener() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(null);
        this.setVisible(true);

        label = new JLabel();
        label.setBounds(450, 450, 100, 100);
        label.setIcon(new ImageIcon("one.png"));
        label.setOpaque(true);
        label.addMouseListener(this);
        this.add(label);

        colors = new Color[] {Color.CYAN, Color.RED, Color.GREEN, Color.BLACK, Color.BLUE, Color.DARK_GRAY, Color.LIGHT_GRAY};
        while (true) {
            System.out.println("running...");
            try {
                Thread.sleep(5000);
            }catch (Exception ignored){}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked");
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.print(y + "jjjjjjjjj");
        System.out.print(x);
        Robot r = null;
        try {
            r = new Robot();
            r.mouseMove(x, y - 50);
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        System.out.println("pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        entered = true;
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        entered = false;
        System.out.println("exited");
    }
}
