package objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bee extends SuperObject{

    public OBJ_Bee()
    {
        name = "Bee";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/BEEZ.png"));
    }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
