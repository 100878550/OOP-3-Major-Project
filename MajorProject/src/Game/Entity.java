package Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected int x, y;
    protected int width = 48, height = 48;
    protected int speed;

    protected BufferedImage image;

    public Entity(int x, int y, int speed, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.image = image;
    }
    
    
    public void update() {
    	
    }
    // Check if an entity hits any solid tile
    public boolean collides(Rectangle entity, Room room, TileManager tileManager, int tileSize) {

        for (int r = 0; r < room.getRows(); r++) { // rows
            for (int c = 0; c < room.getCols(); c++) { // columns

            	//  set the tile to the tile the player is on
                Tile tile = tileManager.getTile(room.getTileId(r, c));

                // check if the tile is walkable
                if (tile.isSolid()) {
                    Rectangle tileRect = new Rectangle(
                        c * tileSize,
                        r * tileSize,
                        tileSize,
                        tileSize
                    );
                    
                    if (entity.intersects(tileRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // Move entity while checking collisions
    public void move(int dx, int dy, Room level, TileManager tileManager, int tileSize, boolean collides) {

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

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getSpeed() {
        return speed;
    }

    public void setPosition(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }


	public void takeDamage() {
		// TODO Auto-generated method stub
		
	}


	public void move() {
		// TODO Auto-generated method stub
		
	}


	public void update(Entity entity, Room room, TileManager tileManager, int tileSize) {
		// TODO Auto-generated method stub
		
	}
}