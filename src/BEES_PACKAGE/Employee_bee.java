package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

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

    public void getSourceInformation(Sources[] src) {

        if (!goingHome && (this.information_gotten || this.gotInfoNowWait)) {

            if (this.source_to_explore != null) {
                int prev_quality = this.source_to_explore.source_qualite;

                for (int i = 0; i < src.length; i++) {
                    if (src[i] != null) {
                        this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
                        src[i].solidArea.setLocation(src[i].source_xpos, src[i].source_ypos);

                        if (this.solidArea.intersects(src[i].solidArea)) {

                            int quality_now = src[i].source_qualite;

                            if (quality_now >= prev_quality) {

                                src[i].reduceQuantityBy(1.99);

                                source_to_explore = src[i];
                                if (!source_to_explore.surMoi.contains(this))
                                    source_to_explore.surMoi.add(this);

                                // si l'abeille trouve une source meilleur , elle remplace sa source précédente
                                // !
                                // pas mal ce [this.bee_id] hein !
                                Bees.infoBoxOfSources[this.bee_id] = src[i];

                                if (src[i].source_quantite <= 0) {
                                    this.information_gotten = true;
                                    for (Bees a : this.source_to_explore.surMoi) {
                                        a.source_to_explore = null;
                                    }
                                    src[i] = null;
                                } else {
                                    // ralentir l'abeille un peu .
                                    this.abeille_suce_et_attend(100, 1.99);
                                }
                            } else {

                                this.information_gotten = true;
                            }
                        }
                    }
                }
            } else {
                // si deux abeilles sont sur une meme fleure , peut etre que l'une d'elle prenne
                // tout le pollen ; donc il faut libérer l'autre , si non elle restera bloqué
                // sur l'ecran ...
                this.information_gotten = true;
            }
        }
    }

    public void release_a_banana() {
        if (this.ventre >= (double) (GamePanel.MAX_QUANTITE_pour_SOURCE * 5) / 100) {
            GamePanel.les_bananes.add(new Banana(this.bee_id, this.bee_xpos + GamePanel.TAILLE_CELLULE * 3,
                    GamePanel.TAILLE_CELLULE * 3 + this.bee_ypos));

            this.ventre = 0;
        }
    }
}
