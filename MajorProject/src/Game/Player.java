package Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// Handles player movement and collision
public class Player {

    private int x, y;
    private int width = 48, height = 48;
    private int speed = 4;

    private BufferedImage image;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        // load player image
        try {
            image = ImageIO.read(getClass().getResource("/assets/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Move player while checking collisions
    public void move(int dx, int dy, Room level, TileManager tileManager, int tileSize) {

        // check X movement
        Rectangle nextX = new Rectangle(x + dx, y, width, height);
        if (!collides(nextX, level, tileManager, tileSize)) {
            x += dx;
        }

        // check Y movement
        Rectangle nextY = new Rectangle(x, y + dy, width, height);
        if (!collides(nextY, level, tileManager, tileSize)) {
            y += dy;
        }
    }

    // Check if player hits any solid tile
    private boolean collides(Rectangle player, Room level, TileManager tileManager, int tileSize) {

        for (int r = 0; r < level.getRows(); r++) {
            for (int c = 0; c < level.getCols(); c++) {

                Tile tile = tileManager.getTile(level.getTileId(r, c));

                if (tile.isSolid()) {
                    Rectangle tileRect = new Rectangle(
                        c * tileSize,
                        r * tileSize,
                        tileSize,
                        tileSize
                    );

                    if (player.intersects(tileRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Draw player
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public int getSpeed() {
        return speed;
    }
}