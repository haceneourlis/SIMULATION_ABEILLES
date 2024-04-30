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
    public int source_id ;
    public BufferedImage image;
    public int quality;
    public double quantity;

    public ArrayList<Bees> surMoi;

    public Rectangle solidArea = new Rectangle(0,0,GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);

    public Sources(int source_id,int source_xpos, int source_ypos)
    {
        this.source_id = source_id;
        this.source_xpos = source_xpos;
        this.source_ypos = source_ypos;

        solidArea.x += source_xpos;
        solidArea.y += source_ypos;
    }
    public Sources(int quality, int source_xpos, int source_ypos, int source_id, double quantity) throws IllegalArgumentException {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sources/flower.png")));
        } catch (IOException e) {
            throw new IllegalArgumentException("Accés à l'image impossible pour la source : " + source_id, e);
        }
        this.quality = quality;
        this.source_id = source_id;
        this.source_xpos = source_xpos;
        this.source_ypos = source_ypos;
        solidArea.x += source_xpos;
        solidArea.y += source_ypos;

        this.quantity = quantity;
        surMoi = new ArrayList<>();
    }


    public void reduceQualityby(int quality)
    {
        this.quantity = this.quality - quality;
    }
    public void reduceQuantityBy(double quantity)
    {
//        if(this.quantity>0)
            this.quantity -= quantity;
    }



    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, this.source_xpos, this.source_ypos, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
    }

    public String toString()
    {
        return "source id = "+ source_id
                +"-- source xpos = "+ source_xpos
                +"-- source ypos ="+ source_ypos
                +"source quantity = "+ quantity
                +"source quality = "+quality;
    }
}
