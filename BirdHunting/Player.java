
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Player implements KeyListener {

    private int x, y, health = 100;
    private int arrowsCount = 50;
    boolean left, right, fire;
    long lastShot = System.nanoTime();
    Image playerImage;
    int screenWidth;

    public void sound(String f) {
        try {
            InputStream inputAudio = getClass().getResourceAsStream(f);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputAudio);
            Clip c = AudioSystem.getClip();
            c.open(audioStream);
            c.start();
        } catch (Exception e) {
            System.out.println("Sound File not found");
        }
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            playerImage = new ImageIcon(getClass().getResource("/ast-archer-icon.png")).getImage();
        } catch (Exception e) {
            System.err.println("Error: Unable to load Player image");
        }
    }

    public void move() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        if (left && x > 0) {
            x -= 10;
        }
        if (right && x < screenWidth - 80) {
            x += 10;
        }

        if (fire && arrowsCount > 0 && (System.nanoTime() - lastShot) / 1_000_000 > 100) {
            Game.arrows.add(new Arrow(x + 22, y));
            arrowsCount--;
           // sound("/arrow-sound.wav");
            sound("/bird-hit.wav");

            lastShot = System.nanoTime();
        }
    }

    public void render(Graphics g) {
        g.drawImage(playerImage, x, y, 80, 70, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Arrows: " + arrowsCount, x, y - 10);
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 100;
        this.arrowsCount = 50;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fire = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        health --;
    }

    public void increaseHealth() {
        health += 15;
        if (health > 100) {
            health = 100;
        }
    }

    public void addArrow() {
        arrowsCount += 2;
    }

    public int getArrowsCount() {
        return arrowsCount;
    }
}
