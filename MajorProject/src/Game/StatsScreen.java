package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatsScreen extends JPanel implements KeyListener {

    private GameFrame frame;
    private Stats stats;
    

    public StatsScreen(GameFrame frame)  {
        this.frame = frame;
        //Need to add json helper that works with Stats
        if (stats == null) {
            this.stats = new Stats();
        } else {
            this.stats = stats;
        }


        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);

        FontMetrics fm;

        //title
        g.setFont(new Font("Arial", Font.BOLD, 48));
        fm = g.getFontMetrics();
        g.drawString("STATS", (getWidth() - fm.stringWidth("STATS")) / 2, 100);

        //enemies killed
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("ENEMIES KILLED", (getWidth() - fm.stringWidth("ENEMIES KILLED")) / 2, 220);

        g.setFont(new Font("Arial", Font.PLAIN, 26));
        String kills = String.valueOf(stats.getEnemiesKilled());
        fm = g.getFontMetrics();
        g.drawString(kills, (getWidth() - fm.stringWidth(kills)) / 2, 260);

        //deaths
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("DEATHS", (getWidth() - fm.stringWidth("DEATHS")) / 2, 340);

        g.setFont(new Font("Arial", Font.PLAIN, 26));
        String deaths = String.valueOf(stats.getDeaths());
        fm = g.getFontMetrics();
        g.drawString(deaths, (getWidth() - fm.stringWidth(deaths)) / 2, 380);

        //rooms cleared
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("ROOMS CLEARED", (getWidth() - fm.stringWidth("ROOMS CLEARED")) / 2, 460);

        g.setFont(new Font("Arial", Font.PLAIN, 26));
        String rooms = String.valueOf(stats.getRoomsCleared());
        fm = g.getFontMetrics();
        g.drawString(rooms, (getWidth() - fm.stringWidth(rooms)) / 2, 500);

        
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        fm = g.getFontMetrics();
        g.drawString("Press ESC to return to menu", (getWidth() - fm.stringWidth("Press ESC to return to menu")) / 2, 850);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.showMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed
    }
}