import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Game extends JPanel implements Runnable {
    public static int score = 0;
    public  int level = 1;
    public static ArrayList<Arrow> arrows;
    ArrayList<Bird> birds;
     boolean running;
     Player player;
     Thread gameThread;
     int birdSpeed = 4;
     String levelUpMessage = "";
     long messageDisplayTime = 0;
     Cursor cr;
     JButton r, e;
     int screenWidth;
     int screenHeight;
     ImageIcon restart, exit;

    public Game() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        player = new Player(screenWidth / 2, screenHeight - 150);
        birds = new ArrayList<>();
        arrows = new ArrayList<>();
        addKeyListener(player);
        setFocusable(true);
        buttons();
        start();
    }
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

     void buttons() {
        cr = new Cursor(Cursor.HAND_CURSOR);

        try {
            restart = new ImageIcon(getClass().getResource("/restart.png"));
            r = new JButton(restart);
            add(r);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'restart.png'.");
        }
        r.setCursor(cr);
        r.setBounds(screenWidth / 2 - 155, screenHeight / 2, 150, 50);

        r.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sound("/play_sound.wav");
                restartGame();
            }
        });

        try {
            exit = new ImageIcon(getClass().getResource("/Exit.png"));
            e = new JButton(exit);
            add(e);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'Exit.png'.");
        }
        e.setCursor(cr);
        e.setBounds(screenWidth / 2 + 5, screenHeight / 2, 150, 50);

        e.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sound("/exit_sound.wav");
                System.exit(0);
            }
        });

        setLayout(null);
        r.setVisible(false);
        e.setVisible(false);
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

     void spawnBird() {
        birds.add(new Bird(-50, (int) (Math.random() * 400), birdSpeed));
        sound("/bird-sound.wav");
    }


 void update() {
    player.move();
    if (Math.random() < 0.02) spawnBird();
    for (Bird bird : birds) bird.move();
    for (Arrow arrow : arrows) arrow.move();

    Iterator<Bird> birdIterator = birds.iterator();
    while (birdIterator.hasNext()) {
        Bird bird = birdIterator.next();
        if (bird.getX() > screenWidth) {
            birdIterator.remove();
            player.decreaseHealth(); //pakhi safe cole gele health kombe
            continue;
        }
        Iterator<Arrow> arrowIterator = arrows.iterator();
        while (arrowIterator.hasNext()) {
            Arrow arrow = arrowIterator.next();
            if (arrow.getY() < 0) {
                arrowIterator.remove(); //arrow pakhite hit na korle arrow kombe
              //  player.decreaseHealth();  //arrow diye pakhi marte na parle health kobme
                continue;
            } //checking arrow and bird ek e position e ache ki na
            if (arrow.getX() < bird.getX() + 55 && arrow.getX() + 6 > bird.getX() &&
                arrow.getY() < bird.getY() + 60 && arrow.getY() + 10 > bird.getY()) {
                arrowIterator.remove(); 
                birdIterator.remove(); //ht korle pakhi and arrow remove hobe
                score += 10;
                checkLevelUp();
                player.addArrow();
                sound("/hitsoundbird.wav");
                break;
            }
        }
    }
}
    

     void checkLevelUp() {
        if (score >= 100 * level) {
            level++;
            player.increaseHealth();
            birdSpeed += 2;
            sound("/levelup.wav");
            levelUpMessage = "Congratulations! Level " + level;
            messageDisplayTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            player.render(g);
            for (Bird bird : birds) bird.render(g);
            for (Arrow arrow : arrows) arrow.render(g);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + score, screenWidth - 150, 30);
            g.drawString("Health: " + player.getHealth(), screenWidth - 150, 60);
            g.drawString("Level: " + level, screenWidth - 150, 90);

            if (!levelUpMessage.isEmpty()) {
                g.setColor(Color.MAGENTA);
                g.drawString(levelUpMessage,screenWidth/2-100, 50);
                if (System.currentTimeMillis() - messageDisplayTime > 2000) {
                    levelUpMessage = "";
                }
            }
        } else {
            sound("/gameover.wav");
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", screenWidth / 2 - 100, screenHeight / 2 - 100);

            g.setFont(new Font("Arial", Font.BOLD, 28));
            g.drawString("Final Score: " + score, screenWidth / 2 - 100, screenHeight / 2 - 50);
        }
    }
    public void run() {
        final double msPerUpdate = 1000.0 / 60;
        long lastTime = System.currentTimeMillis();
    
        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;
            if(level>=0 && level<=3){
                this.setBackground(Color.WHITE); 
            }
            else{
                this.setBackground(Color.CYAN); //noton background add hobe
            }
    
            if (elapsed >= msPerUpdate) {
                lastTime = now;
                update(); 
                repaint();
    
                if (isGameOver()) {
                    handleGameOver();
                    break; 
                }
            }
    
            try {
                
                Thread.sleep(1); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    

     boolean isGameOver() {
        return player.getHealth() <= 0 || player.getArrowsCount() <= 0;
    }
     void handleGameOver() {
        running = false;
        r.setBounds(screenWidth / 2 - 150, screenHeight / 2, 150, 50);
        e.setBounds(screenWidth / 2 + 5, screenHeight / 2, 150, 50);
        r.setVisible(true);
        e.setVisible(true);
        repaint();
    }

     void restartGame() {
        score = 0;
        level = 1;
        birdSpeed = 4;
        player.reset(screenWidth / 2, screenHeight - 150);
        birds.clear();
        arrows.clear();
        r.setVisible(false);
        e.setVisible(false);
        start();
    }
}
