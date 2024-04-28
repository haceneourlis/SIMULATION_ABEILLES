package main;

import BEES_PACKAGE.Eclaireuse_Bee;
import BEES_PACKAGE.Employee_bee;
import BEES_PACKAGE.Frelon;
import BEES_PACKAGE.Observatrice_bee;
import Sources.Sources;

import java.util.ArrayList;
import java.util.Random;

public class CreateurDobjets {

    Random rand = new Random();

    CreateurDobjets() {
    }

    public void set_objects() {

        GamePanel.le_frelon = Frelon.creerUnFrelon();
        GamePanel.les_bananes = new ArrayList<>();
        GamePanel.les_fils_frelon = new ArrayList<>();
        // eclaireuses
        for (int k = 0; k < GamePanel.eclaireuses_bees.length; k++) {
            GamePanel.eclaireuses_bees[k] = new Eclaireuse_Bee();
            GamePanel.eclaireuses_bees[k].bee_xpos =
                    // 30 * GamePanel.UNIT_SIZE;
                    rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.eclaireuses_bees[k].bee_ypos =
                    // 10 * GamePanel.UNIT_SIZE;
                    rand.nextInt(0, 6) * GamePanel.UNIT_SIZE;

            GamePanel.eclaireuses_bees[k].bee_id = k;
        }

        // employees.
        for (int l = 0; l < GamePanel.employee_bees.length; l++) {
            GamePanel.employee_bees[l] = new Employee_bee();
            GamePanel.employee_bees[l].bee_xpos = rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.employee_bees[l].bee_ypos = rand.nextInt(0, 6) * GamePanel.UNIT_SIZE;
            GamePanel.employee_bees[l].bee_id = l;
        }

        for (int o = 0; o < GamePanel.observatrice_bees.length; o++) {
            GamePanel.observatrice_bees[o] = new Observatrice_bee();
            GamePanel.observatrice_bees[o].bee_xpos = rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.observatrice_bees[o].bee_ypos = rand.nextInt(0, 6) * GamePanel.UNIT_SIZE;
            GamePanel.observatrice_bees[o].bee_id = o;
        }

        // sources :
        for (int j = 0; j < GamePanel.les_fleurs.length; j++) {
            int x;
            int y;
            x =
                    // 30 * GamePanel.UNIT_SIZE;
                    rand.nextInt(GamePanel.UNIT_SIZE * 7, GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE);
            y =
                    // 10 * GamePanel.UNIT_SIZE;
                    rand.nextInt(GamePanel.UNIT_SIZE * 7, GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE);
            GamePanel.les_fleurs[j] = new Sources(rand.nextInt(1, GamePanel.SOURCES_MAXIMUM_QUALITY), x, y, j,
                    rand.nextInt(500, GamePanel.SOURCES_MAXIMUM_QUANTITY));
        }

    }

    public double quantite_de_pollen() {
        double somme = 0;
        for (int j = 0; j < GamePanel.les_fleurs.length; j++) {
            if (GamePanel.les_fleurs[j] != null)
                somme += GamePanel.les_fleurs[j].quantity;
        }
        return somme;
    }
}
