package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class FrelonVoleurDeFrelon extends Bees implements Voleur {

    static int Id =0;
    FrelonVoleurDeFrelon()
    {
        bee_xpos  = GamePanel.TAILLE_CELLULE * 16;
        bee_ypos   = 4 * GamePanel.TAILLE_CELLULE;
        this.bee_id = Id;
        this.letMove = true;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/ff.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Id +=1;
    }

    public void bee_move(int temps, int vitesse) {
        if (letMove) {
            bee_xpos += (int) ((dx * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));
            bee_ypos += (int) ((dy * vitesse * temps)
                    / Math.sqrt((dx * dx + dy * dy)));

            if(bee_xpos == GamePanel.LARGEUR_ECRAN - GamePanel.TAILLE_CELLULE && bee_ypos == 0 )
            {
                bee_xpos = GamePanel.LARGEUR_ECRAN /2;
                bee_ypos = GamePanel.HAUTEUR_ECRAN /2;
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

    public void voler()
    {
        for(int i = 0 ; i < GamePanel.les_bananes.size();i++)
        {
            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            GamePanel.les_bananes.get(i).solidArea.setLocation( GamePanel.les_bananes.get(i).source_xpos,
                    GamePanel.les_bananes.get(i).source_ypos);

            if (this.solidArea.intersects( GamePanel.les_bananes.get(i).solidArea)) {
                GamePanel.les_bananes.remove(i);
                i--;

                this.letMove = false ;
                this.information_gotten = true ;
            }
        }
    }


    public boolean attaquer(int vitesse) {
        if (this.information_gotten) {
            int target_x_position = GamePanel.le_frelon.bee_xpos;
            int target_y_position = GamePanel.le_frelon.bee_ypos;

            this.dx = target_x_position - this.bee_xpos;
            this.dy = target_y_position - this.bee_ypos;

            double distance = Math.sqrt(dx * dx + dy * dy);
            bee_xpos += (int) ((dx * vitesse) / distance);
            bee_ypos += (int) ((dy * vitesse) / distance);

            this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
            GamePanel.le_frelon.solidArea.setLocation(target_x_position,target_y_position);
            if(this.solidArea.intersects(GamePanel.le_frelon.solidArea))
            {

                return true;
            }
        }
        return false ;
    }


    @Override
    public void getSourceInformation(Sources[] src) {

    }
}
