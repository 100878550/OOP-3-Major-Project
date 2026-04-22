package Game;


import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Enemy extends Entity{
	
    public Enemy(int x, int y) {
        super(x,y,6,loadImage());

    }
    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(Player.class.getResource("/assets/enemies/lilfuckinrat.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update() {
    	
    }
}
