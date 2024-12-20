package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tile[] tile; //Holds type of tile
    int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; //Stores map data
        loadMap("/maps/Map.txt");
        getTileImage();
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col =0;
            int row =0 ;
            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine(); //Stores whole line
                String numbers[] = line.split(" ");
                while(col < gp.maxScreenCol){
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
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Grass_1.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Grass_2.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Grass_3.png")));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Water_1.png")));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Water_2.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Mud.png")));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/Stone.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
       int col=0;
       int row=0;
       int x = 0;
       int y = 0;

       while(col < gp.maxScreenCol && row < gp.maxScreenRow){
           g2.drawImage(tile[mapTileNum[col][row]].image,x,y,gp.tileSize,gp.tileSize,null);
           col++;
           x +=gp.tileSize;

           if(col == gp.maxScreenCol){
               col = 0;
               x=0;
               row++;
               y +=gp.tileSize;
           }
       }
    }
}
