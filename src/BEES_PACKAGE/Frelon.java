package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

// classe singleton !
public class Frelon extends Bees implements KeyListener, Voleur {

    private static Frelon instance;
    public static int unPasY = 0;
    public static int unPasX = 0;

    public static int munition = 0;

    public static int vie_frelon = 5;

    public static BufferedImage coeur_image ;
    private Frelon() {
        this.bee_xpos = 25;
        this.bee_ypos = 75;
        try {
            image = ImageIO
                    .read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/f.png")));
            Frelon.coeur_image = ImageIO
                    .read(Objects.requireNonNull(getClass().getResourceAsStream("/Tuiles/coeur.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Frelon creerUnFrelon() {
        if (Frelon.instance == null) {
            Frelon.instance = new Frelon();
        }
        return Frelon.instance;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            unPasY = -1 * GamePanel.UNIT_SIZE;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            unPasY = GamePanel.UNIT_SIZE;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            unPasX = GamePanel.UNIT_SIZE;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            unPasX = -1 * GamePanel.UNIT_SIZE;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            unPasY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            unPasY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            unPasX = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            unPasX = 0;
        }
    }

    public void move() {
        this.bee_xpos += unPasX;
        this.bee_ypos += unPasY;
        if (this.bee_xpos <= 0) {
            this.bee_xpos = GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE;
        }

        if (this.bee_xpos > GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE) {
            this.bee_xpos = 0;
        }

        if (this.bee_ypos <= 0) {
            this.bee_ypos = GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE;
        }

        if (this.bee_ypos >= GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE) {
            this.bee_ypos = 0;
        }
        voler();
    }

    public static void supprimer_frelon() {
        Frelon.instance = null;
    }

    public void voler() {
        for (int i = 0; i < GamePanel.les_bananes.size(); i++) {
            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            GamePanel.les_bananes.get(i).solidArea.setLocation(GamePanel.les_bananes.get(i).source_xpos,
                    GamePanel.les_bananes.get(i).source_ypos);

            if (this.solidArea.intersects(GamePanel.les_bananes.get(i).solidArea)) {
                GamePanel.les_bananes.remove(i);
                i--;
                Frelon.munition++;

                provoquer();
            }
        }
    }

    private void provoquer() {
         GamePanel.les_fils_frelon.add(new FrelonVoleurDeFrelon());
    }

    @Override
    public void getSourceInformation(Sources[] src) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
