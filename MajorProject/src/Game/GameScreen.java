package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

// Main gameplay file.
public class GameScreen extends JPanel implements ActionListener, KeyListener {

	
	private BufferedImage[][] ratSpriteSheet; // Store the sliced images here
	
	// Variables 
	private ArrayList<Rat> rats = new ArrayList<>(); // List of enemies on screen
    private GameFrame frame; // window
    private Timer timer; //??????

    
    private final int TILE_SIZE = 64; 

    private TileManager tileManager;
    private Room room; // room on screen
    private Player player; // player entity on screen
    private HeartDisplay hearts = new HeartDisplay(); // heart containers on screen
    
    
    private boolean canChangeRoom = true; // used to check whether the player is on a door tile in a new room
 
    // player key movements
    private boolean up, down, left, right,sprint;
    
	private Random random = new Random();

    public GameScreen(GameFrame frame) {
        this.frame = frame;

        setFocusable(true);
        addKeyListener(this);
        
        
        
        // load sprites
        SpriteLoader loader = new SpriteLoader();
        
        ratSpriteSheet = loader.loadSprites("/Assets/Enemies/lilfuckinrat.png",32,32);
        
        int randomRoom = random.nextInt(1, RoomData.getRoomCount() -1);
        tileManager = new TileManager();
        room = RoomData.getRoom(randomRoom);
        
        player = new Player(
        	    0 * TILE_SIZE,
        	    0 * TILE_SIZE
        	);
        
        spawnEnemiesFromRoom();
        
        

        movePlayer();
        hearts.setHealth(player.health);
        // place the player at the location
        
        timer = new Timer(16, this); // game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // I'd like to make an array of entities and then just draw entities in a loop here.
        
        
        drawMap(g);   // draw tiles
        player.draw(g); // draw player
        hearts.draw(g); //draw hearts
        
        // Draw the enemies on the screen
        for(Rat r : rats) {
        	r.draw(g);
        }
    }

    // Draw all tiles
    private void drawMap(Graphics g) {
        for (int r = 0; r < room.getRows(); r++) { // rows 
            for (int c = 0; c < room.getCols(); c++) { // columns

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

        
        // create player speed and check whether sprint is true. if true multiply by 2 if not, keep it chill
        int currentSpeed = sprint ? player.getSpeed() * 2 : player.getSpeed();
        
        if (left) dx -= currentSpeed; // add speed going left
        if (right) dx += currentSpeed; // add speed going right
        if (up) dy -= currentSpeed; // add speed going up
        if (down) dy += currentSpeed;// add speed going down
        
        
        boolean collision = player.collides(getBounds(), room, tileManager, currentSpeed);
        
        // move the player 
        player.move(dx, dy, room, tileManager, TILE_SIZE,collision);
        
        
        // check where the player is
        int tileX = player.getX() / TILE_SIZE;
        int tileY = player.getY() / TILE_SIZE;

        
        
        // if the player's coordinates are NOT found on a door tile, set canChangeRoom to true
        if (room.getTileId(tileY, tileX) != 4) {
            canChangeRoom = true;
        }    
        

        // If a player is found on a door tile, change canChangeRoom to false and load a new room
        if (room.getTileId(tileY, tileX) == 4 && canChangeRoom) {
        	canChangeRoom = false;
            loadNewRoom(); // method to fetch a new room
        }
        
        for (Enemy e_rat : rats) {
        	if (e_rat instanceof Rat) {
        		((Rat) e_rat).update();
        	}
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
    
    
    
    // Method that handles enemy spawns
    private void spawnEnemiesFromRoom() {
        int spawnChance = 100; // % chance per tile, set at 100% for testing purposes.

        for (int r = 0; r < room.getRows(); r++) { // rows
            for (int c = 0; c < room.getCols(); c++) { // columns

                if (room.getTileId(r, c) == 3) { // tiles that are numbered 3, are enemy spawns

                	
                	// generate a random number between 0 and 100 and if lower
                	// spawn an enemy on the spawn tile currently targeted.
                    if (random.nextInt(100) < spawnChance) {

                        int x = c * TILE_SIZE;
                        int y = r * TILE_SIZE;

                        rats.add(new Rat(ratSpriteSheet, x, y));
                    }
                }
            }
        }
    }

    // Method to load a new room
    private void loadNewRoom() {

        rats.clear(); // remove old enemies

        // randomize the next room to be picked
        int randomRoom = random.nextInt(1, RoomData.getRoomCount() -1);
        // set the randomized room to the current active one
        room = RoomData.getRoom(randomRoom);

        // call the enemy spawn method
        spawnEnemiesFromRoom();

        // reposition player to spawn tile
        movePlayer();
    }
    
    public void movePlayer() {
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