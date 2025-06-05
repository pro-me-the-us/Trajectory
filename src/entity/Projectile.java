package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{
    public int projectileInitial = 0;

    public Projectile(GamePanel gp) {
        super(gp);
        collisionRect = new Rectangle();
        collisionRect.x = 12;
        collisionRect.y =12;
        collisionRect.width = 24;
        collisionRect.height = 24;
        solidAreaDefaultX = collisionRect.x;
        solidAreaDefaultY = collisionRect.y;
    }

    public void set(int worldX,int worldY,String direction,boolean alive){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
    }

    public void update(){
        this.collisionOn = false;
        int monsterIndex = gp.cChecker.checkEntity(this,true);
        if(projectileInitial > 5){
            gp.cChecker.checkTile(this);
            projectileInitial = 0;
        }
        projectileInitial +=1 ;
        if(collisionOn == true || checkBounds()){
            this.alive = false;
            gp.projectiles.remove(this);
            this.life = maxLife;
            return;
        }
        if(monsterIndex != Integer.MAX_VALUE){
            gp.monster[monsterIndex] = null;
            this.alive = false;
            gp.projectiles.remove(this);
        }

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

        life--;
        if(life < 0){
            alive = false;
            life = maxLife;
        }

        spriteCounter++;
        if(spriteCounter > 12){
            spriteNum = (spriteNum%2)+1;
            spriteCounter = 0;
        }
    }

     public boolean checkBounds(){
         if(this.worldX/gp.tileSize > gp.maxWorldCol || this.worldX/gp.tileSize < 0 || this.worldY/gp.tileSize > gp.maxWorldCol || this.worldY/gp.tileSize < 0){
             return true;
         }
         return false;
     }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //Offsetting location of tile wrt player in screen
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
