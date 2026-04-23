package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuideScreen extends JPanel implements KeyListener {

    private GameFrame frame;

    public GuideScreen(GameFrame frame) {
        this.frame = frame;

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
        g.drawString("GUIDE", (getWidth() - fm.stringWidth("GUIDE")) / 2, 100);

        //movement rules
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("MOVEMENT", (getWidth() - fm.stringWidth("MOVEMENT")) / 2, 180);

        g.setFont(new Font("Arial", Font.PLAIN, 22));
        fm = g.getFontMetrics();
        g.drawString("W / A / S / D - Move character", (getWidth() - fm.stringWidth("W / A / S / D - Move character")) / 2, 220);
        g.drawString("Shift - Sprint", (getWidth() - fm.stringWidth("Shift - Sprint")) / 2, 250);

        //combat
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("COMBAT", (getWidth() - fm.stringWidth("COMBAT")) / 2, 320);

        g.setFont(new Font("Arial", Font.PLAIN, 22));
        fm = g.getFontMetrics();
        g.drawString("Click - Attack", (getWidth() - fm.stringWidth("Click - Attack")) / 2, 360);
        g.drawString("Avoid enemies to survive", (getWidth() - fm.stringWidth("Avoid enemies to survive")) / 2, 390);

        //objective
        g.setFont(new Font("Arial", Font.BOLD, 28));
        fm = g.getFontMetrics();
        g.drawString("OBJECTIVE", (getWidth() - fm.stringWidth("OBJECTIVE")) / 2, 460);

        g.setFont(new Font("Arial", Font.PLAIN, 22));
        fm = g.getFontMetrics();
        g.drawString("Clear all enemies in each room", (getWidth() - fm.stringWidth("Clear all enemies in each room")) / 2, 500);
        g.drawString("Progress through rooms and get stronger", (getWidth() - fm.stringWidth("Progress through rooms and get stronger")) / 2, 530);

        //return to menu
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        fm = g.getFontMetrics();
        g.drawString("Press ESC to return to menu (any screen)", (getWidth() - fm.stringWidth("Press ESC to return to menu (any screen)")) / 2, 850);
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.showMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}