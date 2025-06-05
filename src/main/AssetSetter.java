package main;

import monster.MON_goblin;
import object.Boots;
import object.Obj_Gem;
import object.Obj_Health;

import java.util.Random;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        Random random = new Random();
        for(int i=0; i < 20 ; i++){
            gp.obj[i] = new Obj_Gem();
            int x = random.nextInt(gp.maxWorldRow-1)+1;
            int y = random.nextInt(gp.maxWorldCol-1)+1;
            while(gp.tileM.mapTileNum[x][y] <=4 & gp.tileM.mapTileNum[x][y] !=0 ){
                x = random.nextInt(gp.maxWorldRow-1)+1;
                y = random.nextInt(gp.maxWorldCol-1)+1;
            }
            gp.obj[i].worldX = x*gp.tileSize;
            gp.obj[i].worldY = y*gp.tileSize;
        }

        for(int i=20 ; i < 24 ; i++){
            int x = random.nextInt(gp.maxWorldRow-1)+1;
            int y = random.nextInt(gp.maxWorldCol-1)+1;
            while(gp.tileM.mapTileNum[x][y] <=4 & gp.tileM.mapTileNum[x][y] !=0 ){
                x = random.nextInt(gp.maxWorldRow-1)+1;
                y = random.nextInt(gp.maxWorldCol-1)+1;
            }
            gp.obj[i] = new Boots();
            gp.obj[i].worldX = x*gp.tileSize;
            gp.obj[i].worldY = y*gp.tileSize;
        }

        for(int i = 24 ; i <= 29 ; i++){
            int x = random.nextInt(gp.maxWorldRow-1)+1;
            int y = random.nextInt(gp.maxWorldCol-1)+1;
            while(gp.tileM.mapTileNum[x][y] <=4 & gp.tileM.mapTileNum[x][y] !=0 ){
                x = random.nextInt(gp.maxWorldRow-1)+1;
                y = random.nextInt(gp.maxWorldCol-1)+1;
            }
            gp.obj[i] = new Obj_Health(gp);
            gp.obj[i].worldX = x*gp.tileSize;
            gp.obj[i].worldY = y*gp.tileSize;
        }

    }

    public void setMonster(){
        Random random = new Random();
        for(int i=0; i < gp.monster.length; i++){
            gp.monster[i] = new MON_goblin(gp);
            int maxX = (gp.player.worldX/gp.tileSize) + 16;
            int maxY = (gp.player.worldY/gp.tileSize) + 16;
            int minX = (gp.player.worldX/gp.tileSize) - 16;
            int minY = (gp.player.worldY/gp.tileSize) - 16;

            if(minX < 0){
                minX = 0;
            }
            if(minY < 0){
                minY = 0;
            }
            if(maxX > gp.maxWorldRow){
                maxX = gp.maxWorldRow-1;
            }
            if(maxY > gp.maxWorldCol){
                maxY = gp.maxWorldCol -1 ;
            }
            int x = random.nextInt(maxX - minX)+minX;
            int y = random.nextInt(maxY- minY)+maxY;
            while(gp.tileM.mapTileNum[x][y] <=4 & gp.tileM.mapTileNum[x][y] !=0 ){
//                x = random.nextInt(gp.maxWorldRow-1)+1;
//                y = random.nextInt(gp.maxWorldCol-1)+1;
                x = random.nextInt(maxX - minX)+minX;
                y = random.nextInt(maxY- minY)+maxY;
            }
            gp.monster[i].worldX = x*gp.tileSize;
            gp.monster[i].worldY = y*gp.tileSize;
        }
    }
}
