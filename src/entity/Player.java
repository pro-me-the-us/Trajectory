package entity;

import main.GamePanel;
import main.KeyHandler;
import object.Obj_Fireball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean invincible = false;
    public int gemCount = 0;
    public int damageCounter = 0;
    public Projectile projectile;

    public Player(GamePanel gp,KeyHandler keyH){
        super(gp);
        this.gp=gp;
        this.keyH=keyH;
        screenX = (gp.screenWidth/2) - (gp.tileSize/2); //Aligning the player to centre of screen
        screenY = (gp.screenHeight/2) - (gp.tileSize/2);

        collisionRect = new Rectangle();
        collisionRect.x = 8;
        collisionRect.y = 16;
        solidAreaDefaultX = collisionRect.x;
        solidAreaDefaultY = collisionRect.y;
        collisionRect.width = 32;
        collisionRect.height = 32;
        collisionOn = true;

        setDefaultValue();
        getPlayerImage();

    }

    public void setDefaultValue(){
        worldX = 1* gp.tileSize;
        worldY = 1*gp.tileSize;
        speed = 4;
        direction = "down";
        maxLife = 6;
        gemCount = 0;
        invincible = false;
        damageCounter = 0;
        life = maxLife;
        projectile = new Obj_Fireball(gp);
    }

    public void update(){
        //Stops character jiggle
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.downPressed){
                direction ="down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }

            //Check Tile Collision
            collisionOn = false;
            int index = gp.cChecker.checkObject(this,true);
            pickupObject(index);
            gp.cChecker.checkTile(this);
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
        int monIndex = gp.cChecker.checkEntity(this,true);
        monsterCollision(monIndex);
        if(keyH.spacePressed == true && projectile.alive == false){
            projectile.set(worldX,worldY,direction,true);
            gp.projectiles.add(projectile);
            gp.playSound(2);
        }
        checkInvincible();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

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
        //Image Observer : null
    }

    public void pickupObject(int index){
        if(index != Integer.MAX_VALUE ){
            if(Objects.equals(gp.obj[index].name, "Gem")){
                gp.playSound(1);
                gp.obj[index] = null;
                gemCount++;
                if(gemCount >= 20){
                    gp.gameState = gp.winState;
                    gp.stopMusic();
                    gp.playSound(5);
                }
            }
            else if(Objects.equals(gp.obj[index].name, "Boots")){
                gp.playSound(3);
                gp.obj[index] = null;
                for(int i=20 ; i < 24; i++){
                    gp.obj[i] = null;
                }
                speed += 2;
            } else if (Objects.equals(gp.obj[index].name,"Health")) {
                gp.playSound(4);
                gp.obj[index] = null;
                if(life + 1 <= 6){
                    life += 1;
                }
            }
        }
    }

    public void monsterCollision(int monIndex){
        if(monIndex != Integer.MAX_VALUE ){
            if(invincible == false & life > 0){
                gp.player.life -= 1;
                invincible = true;
                if(gp.player.life == 0){
                    gp.gameState = gp.deadState;
                    gp.stopMusic();
                    gp.playSound(6);
                }
            }
        }
    }

    public void checkInvincible(){
        if(invincible == true){
            damageCounter++;
            if(damageCounter > 60){
                invincible = false;
                damageCounter = 0;
            }
        }
    }
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_up_2.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_down_1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
