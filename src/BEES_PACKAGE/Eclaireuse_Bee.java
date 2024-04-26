package BEES_PACKAGE;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import Sources.Sources;

public class Eclaireuse_Bee extends Bees {

    public Eclaireuse_Bee() {
        super();
        type = "Eclaireuse";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ.png"));
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
                            .read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ-REV.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dy > 1 && dx == 0) {
                try {
                    this.image = ImageIO
                            .read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ_ROTATION_Y.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dy < 1 && dx == 0) {
                try {
                    this.image = ImageIO
                            .read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ_ROTATE_X.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dx < 1 && dy == 0) {
                try {
                    this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/BEEZ.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bee_xpos += (int) ((dx * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));
            bee_ypos += (int) ((dy * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));

            if (bee_xpos >= GamePanel.SCREEN_WIDTH - GamePanel.UNIT_SIZE || bee_xpos <= 0) {
                dx = dx * -1;
                dy = 0;
            }
            if (bee_ypos >= GamePanel.SCREEN_HEIGHT - GamePanel.UNIT_SIZE || bee_ypos <= 0) {
                dx = 0;
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
                        abeille_suce_et_attend(300);
                        source_decouverte.reduceQuantityBy(1.99);
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
