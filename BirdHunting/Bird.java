import java.awt.*;
import javax.swing.*;

public class Bird {
    private int x, y, speed;
     Image birdImage;

    public Bird(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        birdImage = new ImageIcon(getClass().getResource("/bird.gif")).getImage();
    }

    public void move() {
        x += speed; 
    }

    public void render(Graphics g) {
        g.drawImage(birdImage, x, y, 50, 60, null);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
