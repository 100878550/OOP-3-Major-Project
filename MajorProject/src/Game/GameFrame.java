package Game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {


	
    public GameFrame() {
        setTitle("Game");
        setSize(1424, 935);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showMenu();

        setVisible(true);
    }

    public void showMenu() {
        setScreen(new MainMenuScreen(this));
    }

    public void showGame() {
        setScreen(new GameScreen(this));
    }

    public void showStats() {
        setScreen(new StatsScreen(this));
    }
    
    public void showGuide() {
        setScreen(new GuideScreen(this));
    }

    private void setScreen(JPanel screen) {
        setContentPane(screen);
        revalidate();
        repaint();

        screen.setFocusable(true);

        SwingUtilities.invokeLater(() -> {
            screen.requestFocusInWindow();
            screen.requestFocus();
        });
    }
}