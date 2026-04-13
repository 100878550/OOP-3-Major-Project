package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Main gameplay screen
public class GameScreen extends JPanel implements ActionListener, KeyListener {

	
    private GameFrame frame;
    private Timer timer;

    private final int TILE_SIZE = 64;

    private TileManager tileManager;
    private Room room;
    private Player player;
    private Enemy enemy;

    private boolean up, down, left, right;
    
	Random random = new Random();

    public GameScreen(GameFrame frame) {
        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);
        
        int randomX = random.nextInt(100,1100);
        int randomY = random.nextInt(100,900);

        tileManager = new TileManager();
        room = RoomData.getRoom(1);
        player = new Player(200, 200);
        enemy = new Enemy(randomX,randomY);
        
        System.out.println(randomX + " " + randomY);

        timer = new Timer(16, this); // game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMap(g);   // draw tiles
        player.draw(g); // draw player
        enemy.draw(g);
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