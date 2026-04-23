package Game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Projectile extends Entity {

    private double vx;
    private double vy;

    private int ttl;
    private boolean alive;
    private boolean rebound;

    private Object owner;

    public Projectile(int x, int y, int speed, double vx, double vy,
                      int ttl, boolean rebound, int width, int height,
                      Object owner) {
    	
        super(x, y, speed, loadImage());

        this.vx = vx;
        this.vy = vy;
        this.ttl = ttl;
        this.rebound = rebound;
        this.alive = true;
        this.owner = owner;

        this.width = width;
        this.height = height;
    }

    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(Player.class.getResource("/assets/projectiles/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    //checks colision and rebounds if enabled
    public void update(Room room, TileManager tileManager, int tileSize) {
        if (!alive) return;


        int nextX = x + (int) vx;
        Rectangle nextBoundsX = new Rectangle(nextX, y, width, height);

        if (collides(nextBoundsX, room, tileManager, tileSize)) {
            if (rebound) {
                vx = -vx;
            } else {
                alive = false;
                return;
            }
        } else {
            x = nextX;
        }


        int nextY = y + (int) vy;
        Rectangle nextBoundsY = new Rectangle(x, nextY, width, height);

        if (collides(nextBoundsY, room, tileManager, tileSize)) {
            if (rebound) {
                vy = -vy;
            } else {
                alive = false;
                return;
            }
        } else {
            y = nextY;
        }


        ttl--;
        if (ttl <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isRebound() {
        return rebound;
    }

    public void kill() {
        alive = false;
    }

    public void bounceX() {
        vx = -vx;
    }

    public void bounceY() {
        vy = -vy;
    }

    public Object getOwner() {
        return owner;
    }

    public int getTTL() {
        return ttl;
    }

    public double getVX() {
        return vx;
    }

    public double getVY() {
        return vy;
    }
}