package Game;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


// Handles player movement and collision
public class Player extends Entity {

	
	private int maxshotCD = 12; // default frames between shots
	int health = 5;
	private int invincibilityTimer = 0;
	private int flicker = 0;
	boolean rebound = false;

    public Player(int x, int y) {
        super(x,y,4,loadImage());
        
 
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

    	boolean rebound = this.rebound;
        int projectileSpeed = 10;
        int projectileTTL = 90;
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
    
    @Override
    public void draw(Graphics g) {
    	if (isInvincible()) {
    		flicker++;
    		// draw player every 4 frames after being damaged
    		if(flicker % 8 < 4) {
    			return;
    		}
    	}
    	g.drawImage(image, x, y, width, height, null);
    }
    
    public void takeDamage() {
    	if(invincibilityTimer > 0) return; // if hit recently
        health--;
        if (health < 0) health = 0;
        invincibilityTimer = 60;
    }

    public void gainHealth() {
        health++;
        if (health > 5) {
            health = 5;
        }
    }
	public int getHealth() {
		return this.health;
	}
	public void updateInvincibility() {
	    if (invincibilityTimer > 0) invincibilityTimer--;
	}
	// checks if the player is invincible
	public boolean isInvincible() {
		return invincibilityTimer > 0;
	}
	public int getFireRate() {
		return maxshotCD;
	}

	  
    public void upgradeFireRate(int amount) {
    	// decrease the frames between possible shots
    	maxshotCD -= amount;
    	if(maxshotCD <4) maxshotCD = 4;
    }
    
	public void increaseSpeed(int amount) {
	    this.speed += amount;
	}
	public void setRebound(Boolean rebound) {
		this.rebound = rebound;
	}
}