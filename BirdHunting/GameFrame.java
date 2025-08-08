
import javax.swing.*;

public class GameFrame {
      ImageIcon icon;
    public GameFrame() {
        JFrame f = new JFrame("Bird Hunting");

        icon = new ImageIcon(getClass().getResource("/logo_processed.png"));
        f.setIconImage(icon.getImage());
        Game game = new Game();
        f.add(game);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        game.requestFocusInWindow();
    }
}

