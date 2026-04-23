package Game;

import java.awt.image.BufferedImage;

public class Powerup extends Entity {
    private PowerupData.Type type;

    public Powerup(int x, int y, BufferedImage image, PowerupData.Type type) {
        super(x, y, 0, image); 
        this.type = type;
    }

    public PowerupData.Type getType() { 
        return type; 
    }
}