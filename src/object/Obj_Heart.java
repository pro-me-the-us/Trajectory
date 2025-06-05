package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Obj_Heart extends SuperObject{
    public BufferedImage heart_full,heart_half,heart_empty;
    GamePanel gp;

    public Obj_Heart(GamePanel gp){
        this.gp = gp;
        this.name = "Heart";
        try{
            heart_full = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Heart_full.png"))));
            heart_half = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Heart_half.png"))));
            heart_empty = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Heart_empty.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
