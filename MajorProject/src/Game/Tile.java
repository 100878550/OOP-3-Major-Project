package Game;

import java.awt.image.BufferedImage;

// Represents one type of tile (floor, wall, etc.)
public class Tile {

    private BufferedImage image; // what the tile looks like
    private boolean solid;       // whether player can walk through it

    public Tile(BufferedImage image, boolean solid) {
        this.image = image;
        this.solid = solid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isSolid() {
        return solid;
    }
}