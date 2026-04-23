package Game;


import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Enemy extends Entity{
	protected int health; // enemy health
	
    public Enemy(int x, int y,BufferedImage image, int health) {
        super(x,y,6,image);
        this.health = health;
    }

    @Override
    public void move() {
    	
    }

    @Override
	public void update(Entity enemy, Room room, TileManager tileManager, int tileSize) {
		// TODO Auto-generated method stub
		
	}
    
    public void takeDamage(int damage) {
    	health -= damage;
    }
    public boolean isDead() {
    	return health <=0;
    }
}
