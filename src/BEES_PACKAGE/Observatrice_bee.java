package BEES_PACKAGE;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Observatrice_bee extends Bees{

    public Observatrice_bee()
    {
        super();
        type = "Observatrice";
        try{
            this.image = ImageIO.read(getClass().getResourceAsStream("/BEES_PACKAGE/ImagesAbeilles/observatrice.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
