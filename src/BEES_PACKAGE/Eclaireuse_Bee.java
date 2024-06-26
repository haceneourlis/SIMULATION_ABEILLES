package BEES_PACKAGE;

//import graphs.Graph;
//import graphs.Graph;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import main.GamePanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import Sources.Sources;

public class Eclaireuse_Bee extends Bees {

    public Eclaireuse_Bee() {
        super();
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bee_move(int temps, int vitesse) {
        if (letMove) {
            if (dx > 1 && dy == 0) {
                try {
                    this.image = ImageIO
                            .read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ-REV.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dy > 1 && dx == 0) {
                try {
                    this.image = ImageIO
                            .read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ_ROTATION_Y.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dy < 1 && dx == 0) {
                try {
                    this.image = ImageIO
                            .read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ_ROTATE_X.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dx < 1 && dy == 0) {
                try {
                    this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ.png")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bee_xpos += (int) ((dx * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));
            bee_ypos += (int) ((dy * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));

            if (bee_xpos == GamePanel.LARGEUR_ECRAN - GamePanel.TAILLE_CELLULE && bee_ypos == 0) {
                bee_xpos = GamePanel.LARGEUR_ECRAN / 2;
                bee_ypos = GamePanel.HAUTEUR_ECRAN / 2;
            }
            if (bee_xpos >= GamePanel.LARGEUR_ECRAN - GamePanel.TAILLE_CELLULE || bee_xpos <= 0) {
                bee_xpos = Math.max(0, Math.min(bee_xpos, GamePanel.LARGEUR_ECRAN - GamePanel.TAILLE_CELLULE));
                dx = dx * -1;
                dy = 1;
            }
            if (bee_ypos >= GamePanel.HAUTEUR_ECRAN - GamePanel.TAILLE_CELLULE || bee_ypos <= 0) {
                bee_ypos = Math.max(0, Math.min(bee_ypos, GamePanel.HAUTEUR_ECRAN - GamePanel.TAILLE_CELLULE));
                dx = 1;
                dy = dy * -1;
            } else {
                dx = dx + rand.nextInt(-10, 10);
                dy = dy + rand.nextInt(-10, 10);
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
                        abeille_suce_et_attend(70, 0.99);
                        source_decouverte.reduireQuantite(0.99);
                    }
                }
                source_decouverte.solidArea.x = 0;
                source_decouverte.solidArea.y = 0;
            }
        }
        this.solidArea.x = 0;
        this.solidArea.y = 0;
    }
}

//    public ArrayList<?> tirerAleatoirementUnParcours(Graph gr)
//    {
//        List<Integer> sommets = new ArrayList<>();
//        for(int j = 0 ; j < gr.nb_sommets;j++)
//        {
//            sommets.add(j);
//        }
//
//        // sommet de départ .
//        parcours_abeille.add(0);
//
//        Collections.shuffle(sommets,rand);
//
//        for(Integer i : sommets)
//        {
//            if(i != 0)
//            {
//                parcours_abeille.add(i);
//            }
//        }
//
//        return (ArrayList<?>) parcours_abeille;
//    }