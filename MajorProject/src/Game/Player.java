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
    
    public void takeDamage() {
        health--;
        if (health < 0) {
            health = 0;
        }
    }

    public void gainHealth() {
        health++;
        if (health > 3) {
            health = 3;
        }
    }
}