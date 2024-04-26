package BEES_PACKAGE;

import javax.imageio.ImageIO;
import java.io.IOException;
import Sources.Sources;

public class Observatrice_bee extends Bees {

    public Observatrice_bee() {
        super();
        type = "Observatrice";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/observatrice.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSourceInformation(Sources[] src) {

        if (!goingHome && (this.information_gotten || this.gotInfoNowWait)) {

            int prev_quality = this.source_to_explore.quality;
            for (int i = 0; i < src.length; i++) {
                if (src[i] != null) {
                    this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
                    src[i].solidArea.setLocation(src[i].source_xpos, src[i].source_ypos);

                    if (this.solidArea.intersects(src[i].solidArea)) {
                        int quality_now = src[i].quality;
                        if (quality_now >= prev_quality) {
                            src[i].reduceQuantityBy(10.99);
                            if (src[i].quantity <= 0) {
                                this.information_gotten = true;
                                src[i] = null;
                            } else {
                                // ralentir l'abeille un peu .
                                this.abeille_suce_et_attend(100);
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
