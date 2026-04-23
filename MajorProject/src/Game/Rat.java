package Game;
import java.awt.image.BufferedImage;
public class Rat extends Enemy{

	private static final int ROW_WALK_DOWN = 1;
	private static final int ROW_DEATH = 10;
	
	private BufferedImage[] currentAnimation;
	private int frameIndex = 0;
	private int animationSpeed = 120;
	private long lastTime;
	
	public Rat(BufferedImage[][] allSprites, int x, int y) {
	    super(x, y); // Fixes the super() error
	    
	    if (allSprites != null) {
	        // Row 1 is Walk Down in your sprite sheet
	        this.currentAnimation = allSprites[ROW_WALK_DOWN]; 
	    }
	    
	    this.lastTime = System.currentTimeMillis();
	}
	public void update() {
	    // 1. Handle Animation (the code we already fixed)
	    long now = System.currentTimeMillis();
	    if (now - lastTime > animationSpeed) {
	        frameIndex++;
	        if (frameIndex >= currentAnimation.length) {
	            frameIndex = 0;
	        }
	        lastTime = now;
	    }

	    // 2. Handle Movement (Simple test)
	    // Make the rat crawl slowly to the right
	    this.x += 1; 
	}

	public void draw(java.awt.Graphics g) {
	    if (currentAnimation != null && frameIndex < currentAnimation.length) {
	        // Draw the rat! Change 32 to your actual sprite size
	        g.drawImage(currentAnimation[frameIndex], (int)x, (int)y, 32, 32, null);
	    }
	}
}