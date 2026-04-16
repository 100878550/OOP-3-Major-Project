package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// Stores all tile types and loads their images
public class TileManager {

    private Tile[] tiles;

    public TileManager() {
        tiles = new Tile[10]; // array of tile types
        loadTiles();
    }

    // Loads tile images from files
    private void loadTiles() {
        try {
            BufferedImage floor = ImageIO.read(getClass().getResource("/assets/tiles/floor.png"));
            BufferedImage wall = ImageIO.read(getClass().getResource("/assets/tiles/wall.png"));
            BufferedImage door = ImageIO.read(getClass().getResource("/assets/tiles/1.png"));

            tiles[0] = new Tile(floor, false); // floor = walkable
            tiles[1] = new Tile(wall, true);   // wall = solid
            tiles[2] = new Tile(floor,false); // Player spawn, is just a tile
            tiles[3] = new Tile(floor,false); // enemy spawn, same as player spawn
            tiles[4] = new Tile(door,false); // Door, self explanatory
            	

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Returns tile by ID
    public Tile getTile(int id) {
        return tiles[id];
    }
}