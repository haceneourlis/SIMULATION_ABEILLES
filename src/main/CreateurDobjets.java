package main;

import BEES_PACKAGE.Eclaireuse_Bee;
import BEES_PACKAGE.Employee_bee;
import BEES_PACKAGE.Observatrice_bee;
import Sources.Sources;

import java.util.Random;

public class CreateurDobjets {

    Random rand = new Random();

    CreateurDobjets(){
    }


    public void set_objects() {
        // them beez eclaireuses
        for (int k = 0; k < GamePanel.eclaireuses_bees.length; k++) {
            GamePanel.eclaireuses_bees[k] = new Eclaireuse_Bee();
            GamePanel.eclaireuses_bees[k].bee_xpos =
                    // 30 * GamePanel.UNIT_SIZE;
                    rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.eclaireuses_bees[k].bee_ypos =
                    //10 * GamePanel.UNIT_SIZE;
                    rand.nextInt(0, 8) * GamePanel.UNIT_SIZE;

            GamePanel.eclaireuses_bees[k].bee_id = k;
        }

        // them beez employees.
        for (int l = 0; l < GamePanel.employee_bees.length; l++) {
            GamePanel.employee_bees[l] = new Employee_bee();
            GamePanel.employee_bees[l].bee_xpos = rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.employee_bees[l].bee_ypos = rand.nextInt(0, 8) * GamePanel.UNIT_SIZE;
            GamePanel.employee_bees[l].bee_id = l;
        }

        for (int o = 0; o < GamePanel.observatrice_bees.length; o++) {
            GamePanel.observatrice_bees[o] = new Observatrice_bee();
            GamePanel.observatrice_bees[o].bee_xpos = rand.nextInt(31, 37) * GamePanel.UNIT_SIZE;
            GamePanel.observatrice_bees[o].bee_ypos = rand.nextInt(0, 8) * GamePanel.UNIT_SIZE;
            GamePanel.observatrice_bees[o].bee_id = o;
        }


        // them sources :
        for (int j = 0; j < GamePanel.les_fleurs.length; j++) {
            int x;
            int y;
            x =
                   //30 * GamePanel.UNIT_SIZE;
                    rand.nextInt(GamePanel.UNIT_SIZE * 7, GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE);
            y =
                   //10 * GamePanel.UNIT_SIZE;
                    rand.nextInt(GamePanel.UNIT_SIZE * 7, GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE);
            GamePanel.les_fleurs[j] = new Sources(rand.nextInt(1, GamePanel.SOURCES_MAXIMUM_QUALITY), x, y, j, rand.nextInt(1, GamePanel.SOURCES_MAXIMUM_QUANTITY));
        }

//            GamePanel.les_fleurs[0] = new Sources(1, 28 * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE * 9, 0, 111100);
//            GamePanel.les_fleurs[1] = new Sources(2, 30 * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE * 9, 1, 522220);
//            GamePanel.les_fleurs[2] = new Sources(3, 22 * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE * 10, 2, 63220);
//            GamePanel.les_fleurs[3] = new Sources(10, 22 * GamePanel.UNIT_SIZE, GamePanel.UNIT_SIZE * 14, 3, 702220);
//
//        }

    }
}

