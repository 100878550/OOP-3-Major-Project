package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main gameplay screen
public class GameScreen extends JPanel implements ActionListener, KeyListener {

    private GameFrame frame;
    private Timer timer;

    private final int TILE_SIZE = 64;

    private TileManager tileManager;
    private Room room;
    private Player player;

    private boolean up, down, left, right;

    public GameScreen(GameFrame frame) {
        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);

        tileManager = new TileManager();
        room = RoomData.getRoom(1);
        player = new Player(100, 100);

        timer = new Timer(16, this); // game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMap(g);   // draw tiles
        player.draw(g); // draw player
    }

    // Draw all tiles
    private void drawMap(Graphics g) {
        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {

                Tile tile = tileManager.getTile(room.getTileId(r, c));

                g.drawImage(tile.getImage(),
                        c * TILE_SIZE,
                        r * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE,
                        null);
            }
        }
    }

    // Game loop update
    public void actionPerformed(ActionEvent e) {

        int dx = 0;
        int dy = 0;

        if (left) dx -= player.getSpeed();
        if (right) dx += player.getSpeed();
        if (up) dy -= player.getSpeed();
        if (down) dy += player.getSpeed();

        player.move(dx, dy, room, tileManager, TILE_SIZE);

        repaint(); // redraw screen
    }

    // Key pressed
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true;

        // ESC returns to menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            timer.stop();
            frame.showMenu();
        }
    }

    // Key released
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;
    }

    public void keyTyped(KeyEvent e) {}
}