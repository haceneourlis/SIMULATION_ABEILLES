package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


// classe singleton !
public class Frelon extends Bees implements KeyListener {

    private static Frelon instance ;
    public static int unPasY = 0;
    public static int unPasX = 0;

    public static int munition = 0;
    private Frelon()
    {
        this.bee_xpos = 25;
        this.bee_ypos = 0;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/Frelon.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Frelon creerUnFrelon()
    {
        if(Frelon.instance == null)
        {
            Frelon.instance = new Frelon();
            return Frelon.instance;
        }else
            return instance;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            unPasY = -1 * GamePanel.UNIT_SIZE;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN) {
            unPasY =  GamePanel.UNIT_SIZE;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
            unPasX = GamePanel.UNIT_SIZE;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            unPasX = -1 * GamePanel.UNIT_SIZE;
        }
    }


    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            unPasY = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN) {
            unPasY =  0;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
            unPasX = 0;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT) {
            unPasX = 0;
        }
    }
    public void move() {
        this.bee_xpos += unPasX;
        this.bee_ypos += unPasY;
        recolte();
    }


    public void recolte()
    {
        for(int i = 0 ; i < GamePanel.les_bananes.size();i++)
        {
            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            GamePanel.les_bananes.get(i).solidArea.setLocation( GamePanel.les_bananes.get(i).source_xpos,
                    GamePanel.les_bananes.get(i).source_ypos);

            if (this.solidArea.intersects( GamePanel.les_bananes.get(i).solidArea)) {
                GamePanel.les_bananes.remove(i);
                i--;
                Frelon.munition ++;
            }
        }
    }


    @Override
    public void getSourceInformation(Sources[] src) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
