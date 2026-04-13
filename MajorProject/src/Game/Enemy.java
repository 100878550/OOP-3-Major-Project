package Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Enemy {
	
    private int x, y;
    private int width = 48, height = 48;
    private int speed = 6;
    
    private BufferedImage image;
    
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;

        // load enemy image
        try {
            image = ImageIO.read(getClass().getResource("/assets/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // draw enemy
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public int getSpeed() {
        return speed;
    }
    

}
