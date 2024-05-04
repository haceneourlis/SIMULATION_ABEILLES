package Sources;

import BEES_PACKAGE.Bees;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Sources extends Rectangle {

    public int source_xpos;
    public int source_ypos;
    public int source_id;
    public BufferedImage image;
    public int source_qualite;
    public double source_quantite;

    public ArrayList<Bees> surMoi;

    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE);

    public Sources(int source_id, int source_xpos, int source_ypos) {
        this.source_id = source_id;
        this.source_xpos = source_xpos;
        this.source_ypos = source_ypos;

        solidArea.x += source_xpos;
        solidArea.y += source_ypos;
    }

    public Sources(int quality, int source_xpos, int source_ypos, int source_id, double quantity)
            throws IllegalArgumentException {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sources/flower.png")));
        } catch (IOException e) {
            throw new IllegalArgumentException("Accés à l'image impossible pour la source : " + source_id, e);
        }
        this.source_qualite = quality;
        this.source_id = source_id;
        this.source_xpos = source_xpos;
        this.source_ypos = source_ypos;
        solidArea.x += source_xpos;
        solidArea.y += source_ypos;

        this.source_quantite = quantity;
        surMoi = new ArrayList<>();
    }
    
    public void reduireQuantite(double quantity) {
        // if(this.quantity>0)
        this.source_quantite -= quantity;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, this.source_xpos, this.source_ypos, GamePanel.TAILLE_CELLULE, GamePanel.TAILLE_CELLULE, null);
    }

    public String toString() {
        return "source id = " + source_id
                + "-- source xpos = " + source_xpos
                + "-- source ypos =" + source_ypos
                + "source quantity = " + source_quantite
                + "source quality = " + source_qualite;
    }
}
