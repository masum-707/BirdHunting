import java.awt.*;
import javax.swing.*;

public class Arrow {
    private int x, y;
    private final int speed = 10;
    Image arrow;

    public Arrow(int x, int y) {
        this.x = x;
        this.y = y;

        
        try {
            arrow = new ImageIcon(getClass().getResource("/arrow.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error: Unable to load Arrow image.");
        }
    }

    public Arrow() {
    }

    public void move() {
        y -= speed; 
    }

    public void render(Graphics g) {
        if (arrow != null) {
            g.drawImage(arrow, x, y, 36, 20, null); 
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 6, 10); 
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
