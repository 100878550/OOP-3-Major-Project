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
    
    //creates a bullet towards cursor
    public Projectile shootToward(int targetX, int targetY) {

        int projectileSpeed = 10;
        int projectileTTL = 90;
        boolean rebound = false;
        int projectileSize = 16;

        int startX = x + width / 2 - projectileSize / 2;
        int startY = y + height / 2 - projectileSize / 2;

        double dx = targetX - (x + width / 2.0);
        double dy = targetY - (y + height / 2.0);

        //distance to mouse click
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        //prevents dividing by 0 if click on self
        if (distance == 0) distance = 1;

        //get direction for bullet
        double vx = (dx / distance) * projectileSpeed;
        double vy = (dy / distance) * projectileSpeed;
        
        //creates projectile 
        return new Projectile(
            startX,
            startY,
            projectileSpeed,
            vx,
            vy,
            projectileTTL,
            rebound,
            projectileSize,
            projectileSize,
            this
        );
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