import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BirdHunting extends JFrame {
     ImageIcon icon, play, exit,rules,about,bgL;
     Container c;
     JButton playBtn, exitBtn,rulesBtn,aboutBtn;
     Cursor cr;
     JLabel bg;
   
    
    // sound add korte hobe
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

    BirdHunting() {
        components();
    }

    public void components() {
        c = getContentPane();
        c.setLayout(null);

        try {
            icon = new ImageIcon(getClass().getResource("/logo_processed.png"));
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'logo_processed.png'.");
        }

        try {
            bgL = new ImageIcon(getClass().getResource("/Background800x600.png"));
            bg = new JLabel(bgL);
            c.add(bg);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'Background800x600.png'.");
        }

        bg.setBounds(0, 0, 800, 600);

        cr = new Cursor(Cursor.HAND_CURSOR);

        try {
            play = new ImageIcon(getClass().getResource("/Play.png"));
            playBtn = new JButton(play);
            c.add(playBtn);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'Play.png'.");
        }
       playBtn .setCursor(cr);
        playBtn.setBounds(325, 275, 150, 50);
       

        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound("/play_sound.wav");
                dispose();
                new GameFrame();
            }
        });

        try {
            exit = new ImageIcon(getClass().getResource("/Exit.png"));
            exitBtn = new JButton(exit);
            c.add(exitBtn);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'Exit.png'.");
        }
        exitBtn.setCursor(cr);
        exitBtn.setBounds(325, 335, 150, 50);

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    sound("/exit_sound.wav");
                    dispose();
                    
                }
            }
        });


        try {
            rules = new ImageIcon(getClass().getResource("/Rules.jpg"));
            rulesBtn = new JButton(rules);
            c.add( rulesBtn);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'Rules.jpg'.");
        }
        rulesBtn.setCursor(cr);
        rulesBtn.setBounds(235, 395, 150, 50);
    

        rulesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sound("/click.wav");
                JOptionPane.showMessageDialog(null, 
                "Games Rules\n" +
                "1.Player need to kill bird to add score\n" +
                "2.Press RIGHT ARROW key to move player right\n" +
                "3.Press LEFT ARROW key to move player left\n" +
                "2.Press SPACE key to fire arrow\n" +
                "Note:Player can play game unill health Zero or remaining arrow Zero\n" +
                "Enjoy the Bird Hunting game!",
                "Game Rules",
            JOptionPane.QUESTION_MESSAGE
            );
            }
        });
       
        
        try {
            about = new ImageIcon(getClass().getResource("about.png"));
            aboutBtn = new JButton(about);
            c.add(aboutBtn);
        } catch (Exception e) {
            System.err.println("Error: Unable to load image 'about.png'.");
        }

        aboutBtn.setCursor(cr);
        aboutBtn.setBounds(390, 395, 150, 50);

        aboutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sound("/click.wav"); 
        
                JOptionPane.showMessageDialog(null, 
                    "About Us\n" +
                    "Bird Hunting\n" +
                    "Developed by: Md. Masum Bhuiyan(Student,IIT,NSTU)\n" +
                    "Email:masum2517@student.nstu.edu.bd\n" +
                    "Supervised by: Shudeb Babu Sen Omit (Lecturer, IIT, NSTU)\n" +
                    "Email:omit.iit@nstu.edu.bd\n" +
                    "Version: 1.0\n" +
                    "Enjoy the Bird Hunting game!",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });  

        c.setComponentZOrder(bg, c.getComponentCount() - 1);
    }

    public static void main(String[] args) {
        BirdHunting b = new BirdHunting();
        b.setVisible(true);
        b.setTitle("Bird Hunting");
        b.setBounds(0, 0, 800, 600);
        b.setLocationRelativeTo(null);
        b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b.setResizable(false);
    }
}
