package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile extends Entity{
	
	int x;
	int y;
	int speed;
	int direction; // ?
	
	public Projectile(int x, int y,int speed) {
		super(x,y,speed,loadImage());
	}
	private static BufferedImage loadImage() {
        try {
            return ImageIO.read(Player.class.getResource("/assets/enemies/lilfuckinrat.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	// Updates projectiles.
	@Override
	public void update() {
		
	}
	
	
	
}
