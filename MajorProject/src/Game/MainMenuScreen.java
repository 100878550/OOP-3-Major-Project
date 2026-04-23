package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// this entire class is just fuckin drag and drop coding.
public class MainMenuScreen extends JPanel implements MouseListener, MouseMotionListener {

    private GameFrame frame;

    private Rectangle playButton  = new Rectangle(554, 248, 300, 100);
    private Rectangle statsButton = new Rectangle(554, 398, 300, 100);
    private Rectangle quitButton  = new Rectangle(554, 548, 300, 100);

    private Point mousePoint = new Point();

    public MainMenuScreen(GameFrame frame) {
        this.frame = frame;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        drawButton(g, playButton, "PLAY");
        drawButton(g, statsButton, "STATS");
        drawButton(g, quitButton, "QUIT");
    }

    private void drawButton(Graphics g, Rectangle button, String text) {
        boolean hover = button.contains(mousePoint);

        g.setColor(hover ? Color.LIGHT_GRAY : Color.WHITE);
        g.fillRect(button.x, button.y, button.width, button.height);

        g.setColor(Color.BLACK);
        g.drawRect(button.x, button.y, button.width, button.height);

        g.setFont(new Font("Arial", Font.BOLD, 32));
        FontMetrics fm = g.getFontMetrics();

        int textX = button.x + (button.width - fm.stringWidth(text)) / 2;
        int textY = button.y + ((button.height - fm.getHeight()) / 2) + fm.getAscent();

        g.drawString(text, textX, textY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point click = e.getPoint();

        if (playButton.contains(click)) {
            frame.showGame();
        } else if (statsButton.contains(click)) {
            frame.showStats();
        } else if (quitButton.contains(click)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePoint = e.getPoint();
        repaint();
    }

    @Override public void mouseDragged(MouseEvent e) { mousePoint = e.getPoint(); repaint(); }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}