package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatsScreen extends JPanel implements KeyListener {

    private GameFrame frame;

    public StatsScreen(GameFrame frame) {
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
        
        //Highscore Stats
        g.drawString("HIGHSCORE:", 420, 200);
        g.drawString("99999999", 500, 200); //Placeholder Number
        
        //Kills Stats
        g.drawString("KILLS:", 420, 220);
        g.drawString("9999", 500, 220); //Placeholder Number
        

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