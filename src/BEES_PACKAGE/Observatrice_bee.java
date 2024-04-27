package BEES_PACKAGE;

import javax.imageio.ImageIO;
import java.io.IOException;
import Sources.Sources;

public class Observatrice_bee extends Bees {

    public Observatrice_bee() {
        super();

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/observatrice.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSourceInformation(Sources[] src) {

        if (!goingHome && (this.information_gotten || this.gotInfoNowWait)) {

            if(this.source_to_explore != null)
            {
                int prev_quality = this.source_to_explore.quality;
                for (int i = 0; i < src.length; i++) {
                        if(src[i] != null) {
                            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
                            src[i].solidArea.setLocation(src[i].source_xpos, src[i].source_ypos);




                            if (this.solidArea.intersects(src[i].solidArea)) {

                                int quality_now = src[i].quality;

                                if (quality_now >= prev_quality) {

                                    src[i].reduceQuantityBy(10.99);


                                    source_to_explore = src[i];
                                    if(!source_to_explore.surMoi.contains(this))
                                        source_to_explore.surMoi.add(this);


                                    if (src[i].quantity <= 0) {
                                        this.information_gotten = true;
                                        for(Bees a : this.source_to_explore.surMoi)
                                        {
                                            a.source_to_explore = null ;
                                        }
                                        src[i] = null;
                                    } else {
                                        // ralentir l'abeille un peu .
                                        this.abeille_suce_et_attend(30,10.99);
                                    }
                                }
                                else {
                                    this.information_gotten = true;
                                }
                            }
                        }
                }
            }else {
                    // si deux abeilles sont sur une meme fleure , peut etre que l'une d'elle prenne
                    // tout le pollen ; donc il faut libérer l'autre , si non elle restera bloqué sur l'ecran ...
                    this.information_gotten = true;
            }
        }
    }
}
