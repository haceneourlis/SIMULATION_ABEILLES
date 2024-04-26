package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Bees {

    public BufferedImage image;
    public String type;
    public int bee_xpos, bee_ypos;
    public Sources source_to_explore;
    public int dx = 1, dy = 1;
    public int vitesse = 3;
    public boolean isInHome = false;
    public int bee_id;
    public boolean information_gotten = false;
    public Rectangle solidArea = new Rectangle(0, 0, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE); // pour m'aider à
    // détecter les
    // collisions .

    static public Sources[] infoBoxOfSources = new Sources[GamePanel.NUMBER_OF_SOURCES];
    public boolean letMove = true;
    public int stop_Labeille = 0;
    public boolean gotInfoNowWait = false;
    public boolean goingHome = false;

    Random rand = new Random();

    public abstract void getSourceInformation(Sources[] src);

    public void draw(Graphics2D g2) {
        g2.drawImage(this.image, bee_xpos, bee_ypos, GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE, null);
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
        this.letMove = false; // pour bloquer les abeilles ( t.important );

        this.stop_Labeille++;

        if (this.stop_Labeille > tempsDattente) {
            this.stop_Labeille = 0;
            this.information_gotten = true;
        }
    }

    public int compteur_retour_ruche = 0;

    public int exploreVoisinage() {

        if (this.information_gotten) {
            this.stop_Labeille = 0;

            int[] X_directions = { +0, +0, -1, -1, +0, +0, +0, +0, +0, +1, +1, +1, +0, +0, -1, -1};
            int[] Y_directions = { -1, -1, +0, +0, +1, +1, +1, +1, +1, +0, +0, +0, -1, -1, +0, +0};

            if (compteur_retour_ruche >= X_directions.length) {
                return this.getBackHome(14);
            } else {
                bee_xpos += GamePanel.UNIT_SIZE * X_directions[compteur_retour_ruche];
                bee_ypos += GamePanel.UNIT_SIZE * Y_directions[compteur_retour_ruche];
                compteur_retour_ruche++;
            }
        }

        return 0;
    }

    public int getBackHome(int vitesse) {
        if (!isInHome && information_gotten) {

            this.goingHome = true;

            int target_x_position = GamePanel.POSITION_X_DE_LA_RUCHE + GamePanel.UNIT_SIZE * 2;
            int target_y_position = GamePanel.POSITION_Y_DE_LA_RUCHE - GamePanel.UNIT_SIZE * 3;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse) / distance);
            bee_ypos += (int) ((dy * vitesse) / distance);

            if (this.bee_ypos <= GamePanel.POSITION_Y_DE_LA_RUCHE
                    && this.bee_xpos >= GamePanel.POSITION_X_DE_LA_RUCHE) {
                this.isInHome = true;
                this.information_gotten = false;
                this.compteur_retour_ruche = 0;
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