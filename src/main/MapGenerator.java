package main;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class MapGenerator {
    public int[][] map;
    public int[][] direction = {{2,0},{-2,0},{0,2},{0,-2}};
    public GamePanel gp;
    Random random = new Random();

    public MapGenerator(GamePanel gp){
        this.gp = gp;
        this.map = new int[gp.maxWorldRow][gp.maxWorldCol];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(i <= gp.maxWorldRow/2 & j < gp.maxWorldCol/2){
                    map[i][j] = 1;
                }
                else if(i < gp.maxWorldRow/2 & j >= gp.maxWorldCol/2){
                    map[i][j] = 2;
                }
                else if(i > gp.maxWorldRow/2 & j <= gp.maxWorldCol/2){
                    map[i][j] = 3;
                }
                else if(i >= gp.maxWorldRow/2 & j > gp.maxWorldCol/2){
                    map[i][j] = 4;
                }
            }
        }
    }

    public void shuffleDirection(){
        for(int i = direction.length - 1;i>0 ;i--){
            int index = random.nextInt(i);
            int[] temp = direction[i];
            direction[i] = direction[index];
            direction[index] = temp;
        }
    }

    public void DFS(int x,int y){
        if(this.map[x][y] != 0){
            this.map[x][y] = 0;
        }
        shuffleDirection();
        for(int i=0;i<direction.length;i++){
                int new_x = x + direction[i][0];
                int new_y = y + direction[i][1];
                if(validIndex(new_x,new_y)){
                    if(this.map[new_x][new_y] != 0){
                        map[x + direction[i][0]/2][y + direction[i][1]/2] = 0;
                        DFS(new_x,new_y);
                        map[new_x][new_y] = 0;
                    }
                }
        }
    }

    public boolean validIndex(int x,int y){
        if(x >=0 & y >=0 & x < gp.maxWorldRow  & y < gp.maxWorldCol  ){
            return true;
        }
        return false;
    }

    public void pathGenerate() {
        int startX = 1,startY = 1;
        DFS(startX,startY);
        setPathTile();
        createMapFile();
    }

    public void setPathTile(){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(map[i][j] == 0) {
                    if (i <= gp.maxWorldRow / 2 & j < gp.maxWorldCol / 2) {
                        int r = random.nextInt(3);
                        if(r == 0 ){
                            map[i][j] = 0;
                        }else{
                            map[i][j] = 8;
                        }

                    } else if (i < gp.maxWorldRow / 2 & j >= gp.maxWorldCol / 2) {
                        map[i][j] = 5;
                    } else if (i > gp.maxWorldRow / 2 & j <= gp.maxWorldCol / 2) {
                        map[i][j] = 6;
                    } else if (i >= gp.maxWorldRow / 2 & j > gp.maxWorldCol / 2) {
                        map[i][j] = 7;
                    }
                }
            }
        }
    }

    public void createMapFile(){
        String file_name = "./Resources/maps/world.txt";
        try{
            File file = new File(file_name);
            System.out.println(file.createNewFile());

            FileWriter fw = new FileWriter(file_name);
            for(int i=0;i<this.map.length;i++) {
                for (int j = 0; j < this.map[i].length; j++) {
                    fw.write(map[i][j]+" ");
                }
                fw.write("\n");
                fw.flush();
            }
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
