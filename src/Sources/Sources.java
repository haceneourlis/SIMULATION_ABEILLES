package Sources;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sources extends Rectangle {


    public int source_xpos;
    public int source_ypos;
    public int source_id ;
    public BufferedImage image;
    public int quality; // rating from 1 to 10

    public double quantity;

    public Rectangle solidArea = new Rectangle(0,0,GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE);

    public Sources(int quality, int source_xpos, int source_ypos, int source_id, double quantity)
    {
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Sources/flower.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        this.quality = quality;
        this.source_id = source_id;
        this.source_xpos = source_xpos;
        this.source_ypos = source_ypos;
        this.quantity = quantity;
        solidArea.x += source_xpos;
        solidArea.y += source_ypos;
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
        g2.drawImage(image, source_xpos, source_ypos, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
    }



}
