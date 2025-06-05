package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends SuperObject{
    public Boots(){
        name = "Boots";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/boots.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
