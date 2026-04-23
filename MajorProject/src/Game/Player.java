package Game;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// Handles player movement and collision
public class Player extends Entity {

	
	
	int health = 3;

    public Player(int x, int y) {
        super(x,y,4,loadImage());
        this.health = health;
        
 
    }
    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(Player.class.getResource("/assets/Player Sprites/player.gif"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void update() {
    	
    }
    @Override
    /*
    public void move(int dx, int dy, Room level, TileManager tileManager, int tileSize) {

        // check X movement
        Rectangle nextX = new Rectangle(x + dx, y, width, height);
        if (!collides(nextX, level, tileManager, tileSize)) {
            x += dx;
        }

        // check Y movement
        Rectangle nextY = new Rectangle(x, y + dy, width, height);
        if (!collides(nextY, level, tileManager, tileSize)) {
            y += dy;
        }
    }
    */
    
    public void takeDamage() {
    	this.health--;
    }
    public void gainHealth() {
    	this.health++;
    }

    
   
}
