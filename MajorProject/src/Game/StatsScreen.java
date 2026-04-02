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
        g.drawString("STATS SCREEN", 420, 200);

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