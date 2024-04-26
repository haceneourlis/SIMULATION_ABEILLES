package BEES_PACKAGE;

import Sources.Sources;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Employee_bee extends Bees {

    public Employee_bee() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/employeeBEE.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSourceInformation(Sources[] src) {

        if (!goingHome && (this.information_gotten || this.gotInfoNowWait)) {
            /*
             * on comment avec best_quality = 0 ; ensuite ça évolue .
             * ça explique la fonction : box_reset
             */
            int prev_quality = Bees.infoBoxOfSources[this.bee_id].quality;
            for (int i = 0; i < src.length; i++) {
                if (src[i] != null) {
                    this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
                    src[i].solidArea.setLocation(src[i].source_xpos, src[i].source_ypos);

                    if (this.solidArea.intersects(src[i].solidArea)) {
                        int quality_now = src[i].quality;
                        if (quality_now >= prev_quality) {

                            Bees.infoBoxOfSources[this.bee_id] = src[i];
                            src[i].reduceQuantityBy(1.99);

                            if (src[i].quantity <= 0) {
                                // System.out.println("the source : " + src[i].source_id + "has just been
                                // removed BECAUSE THE BEE :"+ this.bee_id + "ate it");
                                this.information_gotten = true;
                                src[i] = null;
                            } else {
                                // ralentir l'abeille un peu .
                                this.abeille_suce_et_attend(100);
                                // System.out.println("the bee = " + this.bee_id + " ate the source " +
                                // src[i].source_id + " and quantity now is = " + src[i].quantity);
                            }
                        }

                    }
                } else {
                    // si deux abeilles sont sur une meme fleure , peut etre que l'une d'elle prenne
                    // tout le pollen ; donc
                    // il faut libérer l'autre , si non elle restera bloqué sur l'ecran ...
                    this.information_gotten = true;
                }
            }
        }
    }
}
