import java.awt.event.*;
import java.util.HashSet;

public class KeyMouseListener implements KeyListener, MouseListener {
    HashSet<Integer> pressedKeys;
    boolean isMousePressed = false;
    boolean entered = false;

    public boolean isPressed(Integer keyCode) {
        return  pressedKeys.contains(keyCode);
    }
    public HashSet<Integer> getPressedKeys() {
        return pressedKeys;
    }

    public boolean isEntered() {
        return entered;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        entered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        entered = false;
    }
}
