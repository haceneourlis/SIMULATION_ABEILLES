package objects;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SuperObject extends Rectangle {

    Random rand = new Random();

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int xpos , ypos;
    public Rectangle solidArea = new Rectangle(0,0,GamePanel.UNIT_SIZE,GamePanel.UNIT_SIZE);
    public int dx=1 ,dy=1;
    public int vitesse = 3;

    public void draw(Graphics2D g2, GamePanel gp)
    {
        g2.drawImage(image,xpos,ypos,GamePanel.UNIT_SIZE,GamePanel.UNIT_SIZE,null);
    }

    public void move(int temps)
    {
        xpos += (int) ((dx * vitesse * temps)
                / Math.sqrt((dx * dx + dy * dy)));
        ypos += (int) ((dy * vitesse * temps)
                / Math.sqrt((dx * dx + dy * dy)));

        if(xpos >= GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE || xpos <= 0)
        {
            dx = dx * -1;
            dy = 0;
        }
        if(ypos >= GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE || ypos <= 0)
        {
            dx = 0;
            dy = dy * -1;
        }
        else
        {
            dx = dx + rand.nextInt(-50,50);
            dy = dy + rand.nextInt(-50,50);
        }
    }
}
