package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

public class Bees {

    public BufferedImage image;
    public String type;
    public int bee_xpos, bee_ypos;
    public Sources source_to_explore;
    public int dx = 1, dy = 1;
    public int vitesse = 3;
    public boolean isInHome = false;
    public int bee_id;
    public boolean information_gotten = false;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE); // pour m'aider à détecter les collisions .

    static public Sources[] infoBoxOfSources = new Sources[GamePanel.NUMBER_OF_SOURCES];
    public boolean letMove = true;
    public int stop_Labeille = 0;
    public boolean gotInfoNowWait = false;
    public boolean goingHome = false;

    Random rand = new Random();

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, bee_xpos, bee_ypos, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
    }

    public static void display() {
        for (int i = 0; i < infoBoxOfSources.length; i++) {
            if (infoBoxOfSources[i] != null) {
                System.out.println("Bee number: " + i + " returned with source " + infoBoxOfSources[i].source_id
                        + " at position x = " + infoBoxOfSources[i].source_xpos / GamePanel.UNIT_SIZE +
                        " and position y = " + infoBoxOfSources[i].source_ypos / GamePanel.UNIT_SIZE
                        + " with a score equal to source.quality = " + infoBoxOfSources[i].quality);
                System.out.println("-------------------------------------------------------");
            }
        }
    }

    public void getSourceInformation(Sources[] src) {
        this.solidArea.x += this.bee_xpos;
        this.solidArea.y += this.bee_ypos;

        for (Sources source_decouverte : src) {
            if (source_decouverte != null) {
                source_decouverte.solidArea.x += source_decouverte.source_xpos;
                source_decouverte.solidArea.y += source_decouverte.source_ypos;

                if (this.solidArea.intersects(source_decouverte.solidArea)) {
                    if (Bees.infoBoxOfSources[source_decouverte.source_id] == null || gotInfoNowWait) {
                        if (Bees.infoBoxOfSources[source_decouverte.source_id] == null) {
                            Bees.infoBoxOfSources[source_decouverte.source_id] = source_decouverte;
                        }
                        abeille_suce_et_attend(300);
                    }
                }
                source_decouverte.solidArea.x = 0;
                source_decouverte.solidArea.y = 0;
            }
        }
        this.solidArea.x = 0;
        this.solidArea.y = 0;
    }

    public void bee_move(int temps, int vitesse) {
        if (letMove) {
            int target_x_position = this.source_to_explore.source_xpos;
            int target_y_position = this.source_to_explore.source_ypos;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse * temps) / distance);
            bee_ypos += (int) ((dy * vitesse * temps) / distance);

            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            source_to_explore.solidArea.setLocation(target_x_position, target_y_position);

            if (this.solidArea.intersects(source_to_explore.solidArea)) {
                this.information_gotten = true;
                this.letMove = false;
            }
        }
    }

    public void abeille_suce_et_attend(int tempsDattente) {
        this.information_gotten = false;
        this.gotInfoNowWait = true;

        this.stop_Labeille++;
        this.letMove = false;
//        if (this instanceof Employee_bee)
//            System.out.println("I am an employee bee , my number is :  " + this.bee_id + " is still waiting , its stop_Labeille is at "+this.stop_Labeille);
//        else
//            System.out.println("I am an ECLAIREUSE bee , my number is :  " + this.bee_id + " is still waiting , its stop_Labeille is at "+this.stop_Labeille);

        if (this.stop_Labeille > tempsDattente) {
//            System.out.println("The bee:  " + this.bee_id + " is still waiting , its stop_Labeille is at "+this.stop_Labeille);
            this.information_gotten = true;
            if (this instanceof Employee_bee)
                System.out.println("The bee: " + this.bee_id + " was waiting at position: x = "
                        + this.bee_xpos / GamePanel.UNIT_SIZE + " y = " + this.bee_ypos / GamePanel.UNIT_SIZE);
        }
    }

    public int getBackHome(int vitesse) {
        if (!isInHome && information_gotten) {
            this.goingHome = true;
            this.stop_Labeille = 0;
            this.gotInfoNowWait = false;

            int target_x_position = GamePanel.POSITION_X_DE_LA_RUCHE + GamePanel.UNIT_SIZE * 2;
            int target_y_position = GamePanel.POSITION_Y_DE_LA_RUCHE - GamePanel.UNIT_SIZE * 3;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse) / distance);
            bee_ypos += (int) ((dy * vitesse) / distance);

            if (this.bee_ypos <= GamePanel.POSITION_Y_DE_LA_RUCHE && this.bee_xpos >= GamePanel.POSITION_X_DE_LA_RUCHE) {
                this.letMove = false;
                this.isInHome = true;
                this.information_gotten = false;
                return 1;
            }
        }
        return 0;
    }



    public static int compter_nombre_de_source_decouvertes() {
        int cpt = 0;
        for (int i = 0; i < Bees.infoBoxOfSources.length; i++) {
            if (Bees.infoBoxOfSources[i] != null)
                cpt++;
        }
        return cpt;
    }

    public static void reset_box() {
        Sources tempo = new Sources(0, 0, 0, 9999999, 0);
        for (int i = 0; i < Bees.infoBoxOfSources.length; i++) {
            infoBoxOfSources[i] = tempo;
        }
        }
}