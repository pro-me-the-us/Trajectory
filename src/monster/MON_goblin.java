package monster;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MON_goblin extends Entity {
    public int lockDirection = 0;
    public MON_goblin(GamePanel gp){
        super(gp);
        speed = 3;
        maxLife = 4;
        life = maxLife;

        collisionRect = new Rectangle();
        collisionRect.x = 8;
        collisionRect.y =16;
        collisionRect.width = 32;
        collisionRect.height = 32;
        solidAreaDefaultX = collisionRect.x;
        solidAreaDefaultY = collisionRect.y;
        direction = "down";
        onPath = true;
        getImage();
    }

    public void getImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Monster/monster_right_2.png")));

        }catch (Exception e){

        }
    }

    public void updateDirection(){
        if(onPath == true){
            int goalCol = (gp.player.worldX + gp.player.collisionRect.x)/gp.tileSize;
            int goalRow =  (gp.player.worldY + gp.player.collisionRect.y)/gp.tileSize;
            searchPath(goalCol,goalRow);
        }else{
            lockDirection++;
            Random random = new Random();
            if(lockDirection >= 100){
                int dir = random.nextInt(100);
                if(dir <= 25){
                    direction = "up";
                }else if(dir > 25 & dir < 50){
                    direction = "down";
                }else if(dir >= 50 & dir < 75){
                    direction = "left";
                }else{
                    direction = "right";
                }
                lockDirection = 0;
            }
        }

    }

    public void update(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        updateDirection();
        if(collisionOn == false){
            switch(direction){
                case "up" :
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            spriteNum = (spriteNum%2)+1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //Offsetting location of tile wrt player in screen
        int screenY = worldY - gp.player.worldY+ gp.player.screenY;

        if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            switch (direction){
                case "up":
                    if(spriteNum == 1)
                        image = up1;
                    if(spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if(spriteNum == 1)
                        image = down1;
                    if(spriteNum == 2)
                        image = down2;
                    break;
                case "right":
                    if(spriteNum == 1)
                        image = right1;
                    if(spriteNum == 2)
                        image = right2;
                    break;
                case "left":
                    if(spriteNum == 1)
                        image = left1;
                    if(spriteNum == 2)
                        image = left2;
                    break;
            }
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        }
    }
}
