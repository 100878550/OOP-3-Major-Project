package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class HeartDisplay {

    private BufferedImage fullHeart;
    private BufferedImage emptyHeart;

    private final int maxHearts = 3;
    private int currentHealth = 3;

    private int x = 20;
    private int y = 10;
    private int size = 48;
    private int spacing = 10;

    public HeartDisplay() {
        try {
            fullHeart = ImageIO.read(getClass().getResource("/Assets/Icons/HeartFull.png"));
            emptyHeart = ImageIO.read(getClass().getResource("/Assets/Icons/HeartEmpty.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checks if sets current health between 0-3
    public void setHealth(int health) {
        if (health < 0) {
            currentHealth = 0;
        } else if (health > 3) {
            currentHealth = 3;
        } else {
            currentHealth = health;
        }
    }
    
    //draws the hearts top left and empties right to left
    public void draw(Graphics g) {
    	
    	//start from right to left
    	for (int i = maxHearts - 1; i >= 0; i--) {

            //adds spacing between hearts
            int drawX = x + (i * (size + spacing));

            //will set as empty heart if heartcount is greater then current health
            //this way hearts empty right to left
            if (i < currentHealth) {

                g.drawImage(fullHeart, drawX, y, size, size, null);

            } else {

                g.drawImage(emptyHeart, drawX, y, size, size, null);
            }
        }
    }
}


