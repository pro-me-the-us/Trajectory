package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public GamePanel gp;
    public int worldX,worldY;
    public int speed;
    public int maxLife;
    public int life;
    public boolean alive;
    //It describes an image with an accessible buffer of image data;
    public BufferedImage up1,up2, down1 , down2, left1,left2 ,right1,right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionRect ;
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean onPath = false;


    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol  = (worldX + collisionRect.x)/gp.tileSize;
        int startRow = (worldY + collisionRect.y)/gp.tileSize;

        gp.pFinder.setNode(startCol,startRow,goalCol,goalRow,this);

        if(gp.pFinder.search() == true){
             int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
             int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

             int enLeftX = worldX + collisionRect.x ;
             int enRightX = worldX + collisionRect.width;
             int enTopY = worldY + collisionRect.y;
             int enDownY = worldY + collisionRect.height;

             if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                 direction = "up";
             } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                 direction = "down";
             }else if(enTopY >= nextY && enDownY < nextY + gp.tileSize ){
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
             }else if(enTopY > nextY && enLeftX > nextX){
                 direction = "up";
                 gp.cChecker.checkTile(this);
                 if(collisionOn == true){
                     direction = "left";
                 }
             }else if(enTopY > nextY && enLeftX < nextX){
                 direction = "up";
                 gp.cChecker.checkTile(this);
                 if(collisionOn == true){
                     direction = "right";
                 }
             }else if(enTopY < nextY && enLeftX > nextX){
                 direction = "down";
                 gp.cChecker.checkTile(this);
                 if(collisionOn == true){
                     direction = "left";
                 }
             }else if(enTopY < nextY && enLeftX < nextX){
                 direction = "down";
                 gp.cChecker.checkTile(this);
                 if(collisionOn == true){
                     direction = "right";
                 }
             }
             collisionOn = false;
//             int nextCol = gp.pFinder.pathList.get(0).col;
//             int nextRow = gp.pFinder.pathList.get(0).row;
//             if(nextCol == goalCol && nextRow == goalRow){
//                 onPath = false;
//             }
        }
    }
}
