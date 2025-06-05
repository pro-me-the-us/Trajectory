package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX+entity.collisionRect.x;
        int entityRightWorldX = entity.worldX+entity.collisionRect.x+entity.collisionRect.width;
        int entityTopWorldY = entity.worldY+entity.collisionRect.y;
        int entityBottomWorldY = entity.worldY+entity.collisionRect.y + entity.collisionRect.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1,tileNum2;

        switch(entity.direction){
            case "up" :
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity,boolean player){
        int index = Integer.MAX_VALUE;

        for(int i = 0;i< gp.obj.length ;i++){
            if(gp.obj[i] != null){
                entity.collisionRect.x = entity.worldX + entity.collisionRect.x;
                entity.collisionRect.y = entity.worldY + entity.collisionRect.y;

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.collisionRect.y -= entity.speed;
                        if(entity.collisionRect.intersects(gp.obj[i].solidArea)){
                            index = i;
                        }
                        break;
                    case "down":
                        entity.collisionRect.y += entity.speed;
                        if(entity.collisionRect.intersects(gp.obj[i].solidArea)){
                            index = i;
                        }
                        break;
                    case "left":
                        entity.collisionRect.x += entity.speed;
                        if(entity.collisionRect.intersects(gp.obj[i].solidArea)){
                            index = i;
                        }
                        break;
                    case "right":
                        entity.collisionRect.x -= entity.speed;
                        if(entity.collisionRect.intersects(gp.obj[i].solidArea)){
                            index = i;
                        }
                        break;
                }
                entity.collisionRect.x = entity.solidAreaDefaultX;
                entity.collisionRect.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity,boolean player){
        int index = Integer.MAX_VALUE;

        for(int i = 0;i< gp.monster.length ;i++){
            if(gp.monster[i] != null & gp.monster[i] != entity){
                entity.collisionRect.x = entity.worldX + entity.collisionRect.x;
                entity.collisionRect.y = entity.worldY + entity.collisionRect.y;

                gp.monster[i].collisionRect.x = gp.monster[i].worldX + gp.monster[i].collisionRect.x;
                gp.monster[i].collisionRect.y = gp.monster[i].worldY + gp.monster[i].collisionRect.y;

                switch (entity.direction){
                    case "up":
                        entity.collisionRect.y -= entity.speed;
                        break;
                    case "down":
                        entity.collisionRect.y += entity.speed;
                        break;
                    case "left":
                        entity.collisionRect.x += entity.speed;
                        break;
                    case "right":
                        entity.collisionRect.x -= entity.speed;
                        break;
                }
                if(entity.collisionRect.intersects(gp.monster[i].collisionRect) & player == true){
                    index = i;
                }

                entity.collisionRect.x = entity.solidAreaDefaultX;
                entity.collisionRect.y = entity.solidAreaDefaultY;
                gp.monster[i].collisionRect.x = gp.monster[i].solidAreaDefaultX;
                gp.monster[i].collisionRect.y = gp.monster[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
