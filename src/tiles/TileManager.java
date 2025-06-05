package tiles;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile; //Holds type of tile
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; //Stores map data
        loadMap("/maps/world.txt");
        getTileImage();
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row =0 ;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine(); //Stores whole line
                String numbers[] = line.split(" ");
                while(col < gp.maxWorldCol){
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }
                row++;
                col=0;
            }
            br.close();
        }catch(Exception e){

        }
    }

    public void getTileImage(){
        setup(0,"Grass",false);
        setup(1,"Tree1",true);
        setup(2,"stone1",true);
        setup(3,"dryGrass",true);
        setup(4,"Water_1",true);
        setup(5,"Mud1",false);
        setup(6,"Sand",false);
        setup(7,"Ice",false);
        setup(8,"Grass_1",false);

    }

    public void draw(Graphics2D g2){
       int worldCol=0;
       int worldRow=0;

       while(worldCol < gp.maxWorldCol && worldRow< gp.maxWorldRow){
           int worldX = worldCol*gp.tileSize;
           int worldY = worldRow*gp.tileSize;
           int screenX = worldX - gp.player.worldX + gp.player.screenX; //Offsetting location of tile wrt player in screen
           int screenY = worldY - gp.player.worldY+ gp.player.screenY;

           if(     worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
               g2.drawImage(tile[mapTileNum[worldCol][worldRow]].image,screenX,screenY,null);
           } //Creating rendering distance
           worldCol++;

           if(worldCol == gp.maxWorldCol){
               worldCol = 0;
               worldRow++;
           }
       }
    }

    public void setup(int index,String imagePath, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/"+imagePath+".png")));
            tile[index].image = uTool.scaledImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;
        }catch (Exception _){

        }
    }
}
