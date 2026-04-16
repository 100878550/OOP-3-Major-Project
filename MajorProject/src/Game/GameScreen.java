package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

// Main gameplay screen
public class GameScreen extends JPanel implements ActionListener, KeyListener {

	
	private ArrayList<Enemy> enemies = new ArrayList<>();
    private GameFrame frame;
    private Timer timer;

    private final int TILE_SIZE = 64;

    private TileManager tileManager;
    private Room room;
    private Player player;
    
    private boolean canChangeRoom = true;
 

    private boolean up, down, left, right,sprint;
    
	private Random random = new Random();

    public GameScreen(GameFrame frame) {
        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);
        
        int randomRoom = random.nextInt(1,7);
        tileManager = new TileManager();
        room = RoomData.getRoom(randomRoom);
        
        spawnEnemiesFromRoom();
        
        
        
        int spawnRow = 0;
        int spawnCol = 0;

        // find the player spawn when room is generated
        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {
                if (room.getTileId(r, c) == 2) {
                    spawnRow = r;
                    spawnCol = c;
                }
            }
        }
        
        // place the player at the location
        player = new Player(
        	    spawnCol * TILE_SIZE,
        	    spawnRow * TILE_SIZE
        	);

        timer = new Timer(16, this); // game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawMap(g);   // draw tiles
        player.draw(g); // draw player
        for(Enemy e : enemies) {
        	e.draw(g);
        }
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
    @Override
    public void actionPerformed(ActionEvent e) {

        int dx = 0;
        int dy = 0;

        int currentSpeed = sprint ? player.getSpeed() * 2 : player.getSpeed();
        if (left) dx -= currentSpeed;
        if (right) dx += currentSpeed;
        if (up) dy -= currentSpeed;
        if (down) dy += currentSpeed;
        

        player.move(dx, dy, room, tileManager, TILE_SIZE);
        
        int tileX = player.getX() / TILE_SIZE;
        int tileY = player.getY() / TILE_SIZE;

        if (room.getTileId(tileY, tileX) != 4) {
            canChangeRoom = true;
        }    
        

        if (room.getTileId(tileY, tileX) == 4 && canChangeRoom) {
        	canChangeRoom = false;
            loadNewRoom();
        }
        
        repaint(); // redraw screen
    }

    // Key pressed
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) sprint = true;

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
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) sprint = false;
    }
    
    
    private void spawnEnemiesFromRoom() {
        int spawnChance = 100; // % chance per tile

        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {

                if (room.getTileId(r, c) == 3) { // tile 3 is possible spawns

                    if (random.nextInt(100) < spawnChance) {

                        int x = c * TILE_SIZE;
                        int y = r * TILE_SIZE;

                        enemies.add(new Enemy(x, y));
                    }
                }
            }
        }
    }

    private void loadNewRoom() {

        enemies.clear(); // remove old enemies

        int randomRoom = random.nextInt(1, 7);
        room = RoomData.getRoom(randomRoom);

        spawnEnemiesFromRoom();

        
        
        // reposition player to spawn tile
        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {

                if (room.getTileId(r, c) == 2) {
                    player.setPosition(
                        c * TILE_SIZE,
                        r * TILE_SIZE
                    );
                }
            }
        }
    }
    public void keyTyped(KeyEvent e) {}
}