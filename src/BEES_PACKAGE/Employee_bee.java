package BEES_PACKAGE;

import Sources.Sources;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Employee_bee extends Bees{


    public Employee_bee()
    {
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/employeeBEE.PNG"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

//int cpt=0;
    @Override
    public void getSourceInformation(Sources[] src) {

        if (!goingHome && (this.information_gotten || this.gotInfoNowWait) ) {
            // on comment avec best_quality = 0 ; ensuite ça évolue .
            int prev_quality = Bees.infoBoxOfSources[this.bee_id].quality;
            for (int i = 0; i < src.length;i++) {
                if (src[i] != null) {
                    this.solidArea.setLocation(this.bee_xpos, this.bee_ypos);
                    src[i].solidArea.setLocation(src[i].source_xpos, src[i].source_ypos);

                    if (this.solidArea.intersects(src[i].solidArea) ){
                        int quality_now = src[i].quality;
                                //+ (src[i].quantity/20))/2);
                        if(quality_now >= prev_quality) {

                            Bees.infoBoxOfSources[this.bee_id] = src[i];
                            src[i].reduceQuantityBy(1.99);

                            if (src[i].quantity <= 0) {
                                //System.out.println("the source : " + src[i].source_id + "has just been removed  BECAUSE THE BEE :"+ this.bee_id + "ate it");
                                this.information_gotten = true;
                                src[i] = null;
                            } else {
                                // ralentir l'abeille un peu .
                                this.abeille_suce_et_attend(300);
                               // System.out.println("the bee = " + this.bee_id + "  ate the source " + src[i].source_id + "  and quantity now is = " + src[i].quantity);
                            }
                        }


                    }
                }
                else
                {
                    // si deux abeilles sont sur une meme fleure , peut etre que l'une d'elle prenne tout le pollen ; donc
                    // il faut libérer l'autre , si non she will get stuck .
                    this.information_gotten = true;
                }
            }
        }
    }

    public int j = 0;
    public int exploreVoisinage() {

        if (this.information_gotten) {
            this.stop_Labeille = 0;

            int[] X_directions = {+0,+0,   -1,-1,  +0,+0,  +0,+0,+0,   +1,+1,+1,   +0,+0, -1,-1};
            int[] Y_directions = {-1,-1,   +0,+0,  +1,+1,  +1,+1,+1,   +0,+0,+0,   -1,-1,  0,0};

            if (j >= X_directions.length) {
                return  this.getBackHome(2);
            } else {
                bee_xpos += GamePanel.UNIT_SIZE * X_directions[j];
                bee_ypos += GamePanel.UNIT_SIZE * Y_directions[j];
                j++;
            }
        }


        return  0;
    }

}
