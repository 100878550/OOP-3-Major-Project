package Game;


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
    
    public void takeDamage() {
    	this.health--;
    }
    public void gainHealth() {
    	this.health++;
    }

    
   
}
