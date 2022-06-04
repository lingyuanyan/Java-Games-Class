import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class minesweeper extends JFrame implements MouseListener {
    private final int tileSize = 50;
    private final int menuSize = 1200;
    private ImageIcon mine = new ImageIcon("mine.png");
    private ImageIcon empty = new ImageIcon("empty.png");
    private ImageIcon one = new ImageIcon("one.png");
    private ImageIcon two = new ImageIcon("two.png");
    private ImageIcon three = new ImageIcon("three.png");
    private ImageIcon four = new ImageIcon("four.png");
    private ImageIcon five = new ImageIcon("five.png");
    private ImageIcon six = new ImageIcon("six.png");
    private ImageIcon seven = new ImageIcon("seven.png");
    private ImageIcon eight = new ImageIcon("eight.png");
    private ImageIcon unopened = new ImageIcon("unopened.png");
    private ImageIcon start = new ImageIcon("start.png");
    private ImageIcon end = new ImageIcon("end.png");
    private int[][] mineMap;
    private boolean[][] revealed;
    private JLabel[][] tiles;
    private boolean ongoing = false;
    private JLabel screen;
    private final int h = 20;
    private final int w = 20;
    private final int k = 136;
    private boolean isMouseClicked = false;
    private final Object GridLayout;
    private int nOfRevealed = 0;

    public minesweeper(Object gridLayout) {
        GridLayout = gridLayout;
        mine = new ImageIcon(resizeImage(mine, tileSize, tileSize));
        empty = new ImageIcon(resizeImage(empty, tileSize, tileSize));
        one = new ImageIcon(resizeImage(one, tileSize, tileSize));
        two = new ImageIcon(resizeImage(two, tileSize, tileSize));
        three = new ImageIcon(resizeImage(three, tileSize, tileSize));
        four = new ImageIcon(resizeImage(four, tileSize, tileSize));
        five = new ImageIcon(resizeImage(five, tileSize, tileSize));
        six = new ImageIcon(resizeImage(six, tileSize, tileSize));
        seven = new ImageIcon(resizeImage(seven, tileSize, tileSize));
        eight = new ImageIcon(resizeImage(eight, tileSize, tileSize));
        unopened = new ImageIcon(resizeImage(unopened, tileSize, tileSize));
        start = new ImageIcon(resizeImage(start, menuSize, menuSize));
        end = new ImageIcon(resizeImage(end, menuSize, menuSize));
        this.setName("Minesweeper");
        start();
    }
    private BufferedImage resizeImage(ImageIcon icon, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(icon.getImage(), 0, 0, width, height, null);
        graphics2D.dispose();
        return resizedImage;
    }

    private void start() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(menuSize, menuSize));
        this.pack();
        this.setVisible(true);
        this.setLayout((GridLayout)GridLayout);

        screen = new JLabel();
        screen.setBounds(0, 0, 1200, 1200);
        screen.setIcon(start);
        screen.setOpaque(true);
        screen.addMouseListener(this);
        this.add(screen);
        while (!isMouseClicked)
        {
            try {
                Thread.sleep(100);
            }
            catch (Exception ignored) {}
        }
        isMouseClicked = false;
        startGame();
    }
    private void runGame() {
        while (ongoing) {
            try {
                if (isMouseClicked) {
                    Point pos = loc();
                    revealed[pos.x][pos.y] = true;
                    System.out.println(pos.x + " " + pos.y);
                    isMouseClicked = false;
                    revealImage(pos.x, pos.y);
                    if (mineMap[pos.x][pos.y] == 0) isZero(pos.x, pos.y);
                    if (mineMap[pos.x][pos.y] == -1) { break; }
                    if (nOfRevealed == (h*w)-k) break;
                }
                Thread.sleep(100);
            } catch (Exception ignored) {}
        }
        endGame();
    }

    private void endGame() {
        int count = 0;
        for (int i = 0; i < w && k > count; i++) {
            for (int j = 0; j < h && k > count; j++) {
                if (mineMap[i][j] == -1) {
                    tiles[i][j].setIcon(mine);
                    count++;
                    try {
                        Thread.sleep(20);
                    }catch (Exception ignored) {}
                }
            }
        }
        try {
            Thread.sleep(1000);
        }catch (Exception ignored) {}
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                tiles[i][j].setVisible(false);
            }
        }
        ongoing = false;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(menuSize, menuSize));
        this.pack();
        screen.setIcon(end);
        screen.setVisible(true);
        this.add(screen);
        this.setVisible(true);
        while (!isMouseClicked)
        {
            try {
                Thread.sleep(100);
            }
            catch (Exception ignored) {}
        }
        isMouseClicked = false;
        startGame();
    }

    private void startGame() {
        ongoing = true;
        screen.setVisible(false);
        this.getContentPane().setPreferredSize(new Dimension(w*tileSize, h*tileSize));
        this.pack();
        mineMap = new int[h][w];
        revealed = new boolean[h][w];
        tiles = new JLabel[h][w];
        for (int i = 0; i < k;) {
            int minex = (int) (Math.random() * h);
            int miney = (int) (Math.random() * w);
            if(mineMap[minex][miney] != -1) {
                mineMap[minex][miney] = -1;
                i++;
            }
        }
        int count;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (mineMap[i][j] == -1) continue;
                count = 0;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        try {
                            if (mineMap[i+x][j+y] == -1) count++;
                        }
                        catch (Exception ignored) {}
                    }
                }
                mineMap[i][j] = count;
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                tiles[i][j] = new JLabel();
                tiles[i][j].setBounds(i*tileSize, j*tileSize, tileSize, tileSize);
                tiles[i][j].addMouseListener(this);
                tiles[i][j].setOpaque(true);
                this.add(tiles[i][j]);
                tiles[i][j].setIcon(unopened);
                if (mineMap[i][j] == -1) System.out.print(" * ");
                else System.out.print(" " + mineMap[i][j] + " ");
            }
            System.out.println();
        }
        this.setVisible(true);
        runGame();
    }
    private Point loc() {
        Point mouse = getMousePosition();
        return new Point(mouse.x/tileSize, (mouse.y/tileSize)-1);
    }
    private void isZero(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                try {
                    if (revealed[i+x][j+y]) continue;
                    revealed[i+x][j+y] = true;
                    revealImage(i+x, j+y);
                    if (mineMap[i+x][j+y] == 0) {
                        isZero(i+x, j+y);
                    }
                }
                catch (Exception ignored) {}
            }
        }
    }
    private void revealImage(int x, int y) {
        nOfRevealed++;
        ImageIcon icon = null;
        switch (mineMap[x][y]) {
            case -1: icon = mine; break;
            case 0: icon = empty; break;
            case 1: icon = one; break;
            case 2: icon = two; break;
            case 3: icon = three; break;
            case 4: icon = four; break;
            case 5: icon = five; break;
            case 6: icon = six; break;
            case 7: icon = seven; break;
            case 8: icon = eight; break;
        }
        tiles[x][y].setIcon(icon);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isMouseClicked = true;

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
