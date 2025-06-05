package object;

import entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Obj_Fireball extends Projectile {
    GamePanel gp;
    String name;

    public Obj_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.name = "Projectile";
        speed = 8;
        maxLife = 40;
        life = maxLife;
        alive =false;
        getImage();

    }

    public void getImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Projectile/fireball_right_2.png")));

        }catch (Exception e){

        }
    }
}
