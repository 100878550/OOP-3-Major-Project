package Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
public class Rat extends Enemy{

	private static final int ROW_WALK_DOWN = 1;
	private static final int ROW_DEATH = 10;
	
	private BufferedImage[] currentAnimation;
	private int frameIndex = 0;
	private int animationSpeed = 120;
	private long lastTime;
	
	// rats chase you down and inflict contact or bite damage.
	public Rat(BufferedImage[][] allSprites, int x, int y) {
	    // Pass the first frame of the walk animation as the default 'image'
	    super(x, y, allSprites[1][0], 3); 
	    this.speed = 3;
	    
	    if (allSprites != null) {
	        this.currentAnimation = allSprites[1]; 
	    }
	    this.lastTime = System.currentTimeMillis();
	}
	@Override
	public void update(Entity player, Room room, TileManager tileManager, int tileSize) {
	   
	    long now = System.currentTimeMillis();
	    if (now - lastTime > animationSpeed) {
	        frameIndex++;
	        if (frameIndex >= (currentAnimation.length -4)) {
	            frameIndex = 0;
	        }
	        lastTime = now;
	    }

	    
	    // directional variables.
	    int dx = 0;
	    int dy = 0;

	    // Determine horizontal direction
	    if (player.getX() > this.x) dx = speed;
	    else if (player.getX() < this.x) dx = -speed;

	    // Determine vertical direction
	    if (player.getY() > this.y) dy = speed;
	    else if (player.getY() < this.y) dy = -speed;

	  
	    // move the rat.
	    move(dx, dy, room, tileManager, tileSize, true);
	}

	public void draw(Graphics g) {
	    if (currentAnimation != null && frameIndex < currentAnimation.length) {

	        g.drawImage(currentAnimation[frameIndex], (int)x, (int)y, 48, 48, null);
	    }
	}
}