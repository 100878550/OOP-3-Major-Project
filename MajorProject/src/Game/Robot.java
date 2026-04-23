package Game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;



// robots are an enemy class, they stand still and shoot you
public class Robot extends Enemy {
    private static BufferedImage robotSprite;
    private int shootCooldown = 0;
    private final int SHOOT_DELAY = 60; // Frames between shots (approx 1 sec)

    
    // forced to do this because java doesnt allow unhandled possible IO errors.
    static {
        try {
            robotSprite = ImageIO.read(Robot.class.getResource("/Assets/Enemies/Enemy.gif"));
        } catch (Exception e) {
            
        }
    }

    public Robot(int x, int y) {
        super(x, y, robotSprite,6);
        
    }

    public void update(Player player, ArrayList<Projectile> projectiles) {
        // cooldown
        if (shootCooldown > 0) {
            shootCooldown--;
        }


       // shoot if off cd
        if (shootCooldown == 0) {
            shoot(player, projectiles);
            shootCooldown = SHOOT_DELAY;
        }
    }

    
    private void shoot(Player player, ArrayList<Projectile> projectiles) {
        int projectileSpeed = 8;
        int projectileSize = 12;

        // Calculate center points
        int startX = x + width / 2 - projectileSize / 2;
        int startY = y + height / 2 - projectileSize / 2;

        double dx = (player.getX() + player.width / 2) - (x + width / 2);
        double dy = (player.getY() + player.height / 2) - (y + height / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize direction
        double vx = (dx / distance) * projectileSpeed;
        double vy = (dy / distance) * projectileSpeed;

        // Add to the same projectile list the GameScreen uses
        projectiles.add(new Projectile(
            startX, startY, projectileSpeed, vx, vy, 
            100, true, projectileSize, projectileSize, this
        ));
    }
}