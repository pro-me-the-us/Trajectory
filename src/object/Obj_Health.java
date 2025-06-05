package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Obj_Health extends SuperObject {
    GamePanel gp;

    public Obj_Health(GamePanel gp){
        this.gp = gp;
        this.name = "Health";
        try{
            image = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Object/health.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
