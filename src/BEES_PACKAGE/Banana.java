package BEES_PACKAGE;

import Sources.Sources;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Banana extends Sources {

    Banana(int banana_id, int x , int y )
    {
        super(banana_id,x,y);

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/banana.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
