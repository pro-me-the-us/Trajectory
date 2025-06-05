package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Obj_Gem extends SuperObject{
    public Obj_Gem(){
        name = "Gem";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Object/Gem.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
