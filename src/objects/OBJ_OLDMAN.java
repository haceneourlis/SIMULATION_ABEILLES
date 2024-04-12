package objects;
import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_OLDMAN extends SuperObject{
    public OBJ_OLDMAN()
    {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/oldman.png"));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
