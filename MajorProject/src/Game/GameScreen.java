package Game;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Graphics;
import java.util.Random;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Main gameplay file.
public class GameScreen extends JPanel implements ActionListener, KeyListener, MouseListener {

	
	// Properties of the gamescreen
	
	public int kills = 0;
	public int rooms = 0;
	public int deaths = 0;
	
	// for powerup images
	private BufferedImage healthImg, speedImg, fireRateImg,bulletBounce;
	
	
	// set default powerups, could be null, wouldnt matter
	private PowerupData.Type selectedPowerup = PowerupData.Type.HEALTH;
	private PowerupData.Type door1Offer = PowerupData.Type.SPEED;
	private PowerupData.Type door2Offer = PowerupData.Type.HEALTH;
	
	private Boolean roomBuffer = false;
	
	
	
	// create an array list to contain powerups.
	private ArrayList<Powerup> powerups = new ArrayList<>();
	
	// tried to make a rat. It now phases through reality
	private BufferedImage[][] ratSpriteSheet; 
	
	
	private ArrayList<Enemy> enemies = new ArrayList<>(); // List of enemies on screen
    private GameFrame frame; // window
    private Timer timer; //??????

    
    // Projectiles on screen
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    private int shootCooldown = 0;
    
    private final int TILE_SIZE = 64; 

    private TileManager tileManager;
    private Room room; // room on screen
    private int lastRoom = -1; //make sure same room doesnt repeat
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
        addMouseListener(this);
        
        
        Stats.load();
        
        // load powerup images 
        try {
            healthImg = ImageIO.read(getClass().getResource("/Assets/powerups/HeartFull.png"));
            speedImg = ImageIO.read(getClass().getResource("/Assets/powerups/Boots.png"));
            fireRateImg = ImageIO.read(getClass().getResource("/Assets/powerups/bullet.png"));
            bulletBounce = ImageIO.read(getClass().getResource("/Assets/powerups/bulletBounce.png"));
        } catch (IOException e) { e.printStackTrace(); }
        // load sprites
        SpriteLoader loader = new SpriteLoader();
        ratSpriteSheet = loader.loadSprites("/Assets/Enemies/lilfuckinrat.png",32,32);
        
        
        
        int randomRoom = random.nextInt(RoomData.getRoomCount());
        tileManager = new TileManager();
        room = RoomData.getRoom(randomRoom);
        
        player = new Player(
        	    0 * TILE_SIZE,
        	    0 * TILE_SIZE
        	);
        
        spawnEnemiesFromRoom();
        
        

        movePlayer();
        hearts.setHealth(player.getHealth());
        // place the player at the location
        selectedPowerup = PowerupData.Type.Ricochet;
        
        timer = new Timer(16, this); // game loop
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // I'd like to make an array of entities and then just draw entities in a loop here.
        
        
        drawMap(g);   // draw tiles
        drawPowerupOffers(g);
        player.draw(g); // draw player
        hearts.draw(g); //draw hearts
        
        //draws all projectiles
        
        for (Projectile p : projectiles) {
            p.draw(g);
        }
        
        // Draw the enemies on the screen
        for(Enemy e : enemies) {
        	e.draw(g);
        }
     // Draw powerups
        for (Powerup p : powerups) {
            p.draw(g);
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
    	
    	if(player.getHealth() <= 0) {
    		gameOver();
    		return;
    	}
    	player.updateInvincibility();

        int dx = 0;
        int dy = 0;

        if (shootCooldown > 0) {
            shootCooldown--;
        }
        
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
        
        
        // this should stop an out of bounds error
        tileX = Math.max(0, Math.min(tileX, room.getCols() - 1));
        tileY = Math.max(0, Math.min(tileY, room.getRows() - 1));
        
        
        // if the player's coordinates are NOT found on a door tile, set canChangeRoom to true
        if (room.getTileId(tileY, tileX) != 4) {
            canChangeRoom = true;
        }    
        
      
        
        // If a player is found on a door tile, change canChangeRoom to false and load a new room
        if (room.getTileId(tileY, tileX) == 4) {
        	if (enemies.isEmpty()) {
        		// defined offsets because the powerup display 
        		// wont always be above
        		
        		int[][] directions = {
        			{-1,0}, //up
        			{1,0}, // down
        			{0,-1}, // left
        			{0,1} // right
        		};
        		
        		for(int[] dir : directions) {
        			int checkY = tileY + dir[0];
        			int checkX = tileX + dir[1];
        			
        			if(checkY >= 0 && checkY < room.getRows() && checkX >=0 && checkX < room.getCols()) {
        				int adjacent = room.getTileId(checkY,checkX);
        				
        				if(adjacent == 7) {
        					selectedPowerup = door1Offer;
        					break;
        				}
        				else if (adjacent == 8) {
        					selectedPowerup = door2Offer;
        					break;
        				}
        			}
        		}
        		
        		canChangeRoom = false;
        		loadNewRoom();
            }
        }
        
        
        
        // handles powerup pickups.
        Iterator<Powerup> itemIter = powerups.iterator();
        while (itemIter.hasNext()) {
            Powerup p = itemIter.next();
            
            if (player.getBounds().intersects(p.getBounds())) {
                applyPowerup(p.getType());
                itemIter.remove(); // Remove item from screen
            }
        }
        
     
        
        // updates enemies
        for (Enemy en : enemies) {
            if (en instanceof Robot) {
                ((Robot) en).update(player, projectiles);
            } else if (en instanceof Rat) {
            	((Rat) en).update(player, room, tileManager, TILE_SIZE);
            	// damages the player if a rat makes contact.
            	if (en.getBounds().intersects(player.getBounds())) {
                    player.takeDamage();
                    hearts.setHealth(player.getHealth());
                }
            }
          
        }
        
        //allows for removing while looping
        Iterator<Projectile> it = projectiles.iterator();

        //loops through and updates projectiles
        while (it.hasNext()) {
            Projectile p = it.next();
            p.update(room, tileManager, TILE_SIZE);

           
            if (!p.isAlive()) {
                it.remove();
                continue; // Move to the next projectile
            }

            //Check if the projectile hits the player
            if (p.getBounds().intersects(player.getBounds())) {
                
                // check the owner of the projectile
            	// if shot by a robot, it can dmg the player
                if (p.getOwner() instanceof Robot) {
                    player.takeDamage();           // decrease the health of the player
                    hearts.setHealth(player.getHealth()); // Update the visual heart icons
                    
                    p.kill();    // Set projectile alive = false
                    it.remove(); // Remove from the list
                    continue;
                }
            }
            if(p.getOwner() instanceof Player) {
            	Iterator<Enemy> enemyIt = enemies.iterator();
            	while(enemyIt.hasNext()) {
            		Enemy en = enemyIt.next();
            		if(p.getBounds().intersects(en.getBounds())) {
            			en.takeDamage(1); // damage the enemy
            			p.kill(); // get rid of the projectile
            			it.remove();
            			
            			if(en.isDead()) {
            				enemyIt.remove(); // remove the enemy if health is 0 or lower.
            				Stats.addEnemyKilled();
            				Stats.save();
            			}
            			break; // stop checking other enemies for this bullet.
            		}
            	}
            }
        }
        
        checkRoomClear();
        
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
        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {
                if (room.getTileId(r, c) == 3) {
                    
                    int x = c * TILE_SIZE;
                    int y = r * TILE_SIZE;

                    int enemyRoll = random.nextInt(10); 

                
                    if (enemyRoll < 5) {
                        // 50% chance for a Rat
                        enemies.add(new Rat(ratSpriteSheet, x, y));
                    } else {
                        enemies.add(new Robot(x,y));
                    }
                }
            }
        }
    }

    // Method to load a new room
    private void loadNewRoom() {
    	int randomRoom;

        enemies.clear(); // remove old enemies
        projectiles.clear();
        if(!roomBuffer) player.setRebound(false);
        
        roomBuffer = false;
        
        // randomize the next room to be picked
        do {
            randomRoom = random.nextInt(RoomData.getRoomCount());
        } while (randomRoom == lastRoom);
        // set the randomized room to the current active one
        lastRoom = randomRoom;
        room = RoomData.getRoom(randomRoom);

        // call the enemy spawn method
        spawnEnemiesFromRoom();

        // reposition player to spawn tile
        movePlayer();
        Stats.addRoomCleared();
        Stats.save();
    }
    
    // move the player to the new spawn location.
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
    


    @Override
    public void mousePressed(MouseEvent e) {
    	if (e.getButton() == MouseEvent.BUTTON1 && shootCooldown == 0) {
            projectiles.add(player.shootToward(e.getX(), e.getY()));
            shootCooldown = player.getFireRate();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    
    
    // prompts the user with a yes/no dialogue.
    private void gameOver() {
        timer.stop(); // Freeze the game
        player = new Player(
        	    0 * TILE_SIZE,
        	    0 * TILE_SIZE
        	);
        
        Stats.addDeath();
        Stats.save();
        
        int choice = JOptionPane.showConfirmDialog(this, 
            "You died! Restart?", "Game Over", 
            JOptionPane.YES_NO_OPTION);
            
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            frame.showMenu(); // Go back to main menu
        }
    }
    
    // quick reset if they want to reset when the dialogue option "yes" 
    // is selected.
    private void resetGame() {
        player.health = 5; // Reset health
        hearts.setHealth(player.getHealth()); // reset the hearts.
        loadNewRoom(); // load a new room
        timer.start(); // Start the loop again
    }
    
    
    // to apply a powerup we call this method
    // there's 3 powerups
    // Heal
    // a speed boost
    // and fire rate.
    // directly modifies player attributes.
    private void applyPowerup(PowerupData.Type type) {
        switch (type) {
            case HEALTH:
                if (player.health < 5) player.health++;
                hearts.setHealth(player.health);
                break;
            case SPEED:
                player.speed += 1;
                break;
            case FIRE_RATE:
                player.upgradeFireRate(2);
                break;
            case Ricochet:
            	player.setRebound(true);
            	roomBuffer = true;
        }
    }
    
    // checks if everything is dead and spawns the powerup.
    private void checkRoomClear() {
        // Only proceed if enemies are gone AND offers arent made yet.
        if (enemies.isEmpty() && canChangeRoom) {
            
            // 1. Handle the "Spawn at center" logic once
            if (selectedPowerup != null) {
                spawnPowerupAtCenter(selectedPowerup);
                selectedPowerup = null; 
            }

            // randomize the door powerup types if they're not null.
            if (door1Offer == null && door2Offer == null) {
                door1Offer = PowerupData.getRandomPowerupType();
                door2Offer = PowerupData.getRandomPowerupType();
            }
            
        } else {
            // If enemies exist, ensure offers are cleared
            door1Offer = null;
            door2Offer = null;
        }
    }
    
    // looks for the '6' tile
    private void spawnPowerupAtCenter(PowerupData.Type type) {
    	powerups.clear();
        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {
                
                if (room.getTileId(r, c) == 6) {
                    int x = c * TILE_SIZE;
                    int y = r * TILE_SIZE;
                    
                    BufferedImage img = getPowerupImage(type);
                    powerups.add(new Powerup(x, y, img, type));
                    return; // Stop searching once found
                }
            }
        }
    }
    
    
    // Helper method to get powerup images in dynamically.
    private BufferedImage getPowerupImage(PowerupData.Type type) {
        switch (type) {
            case HEALTH: 
            	return healthImg;
            case SPEED:  
            	return speedImg;
            case FIRE_RATE:    
            	return fireRateImg;
            case Ricochet:
            	return bulletBounce;
        }
		return healthImg;
    }
    
    private void drawPowerupOffers(Graphics g) {
        // Only draw if there are offers available (room is clear)
        if (door1Offer == null && door2Offer == null) return;

        for (int r = 0; r < room.getRows(); r++) {
            for (int c = 0; c < room.getCols(); c++) {
                int tileId = room.getTileId(r, c);
                
                // Tile 7 is Door 1's indicator, Tile 8 is Door 2's indicator
                if (tileId == 7 && door1Offer != null) {
                    g.drawImage(getPowerupImage(door1Offer), c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                } 
                else if (tileId == 8 && door2Offer != null) {
                    g.drawImage(getPowerupImage(door2Offer), c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }
    }
    
}

	