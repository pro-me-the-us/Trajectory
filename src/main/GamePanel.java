package main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import monster.MON_goblin;
import object.SuperObject;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    public final int originalTileSize = 16; //16x16 tiles i.e size of player
    public final int scale = 3;
    public final int tileSize = originalTileSize*scale; //Actual tile size
    public final int maxScreenCol= 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol; //768 pixels
    public final int screenHeight = tileSize*maxScreenRow; //576 pixels

    //World Setting
    public final int maxWorldCol = 65;
    public final int maxWorldRow =65;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;
    public final int titleState = 0;
    public int gameState = titleState;
    public int playState = 1;
    public int winState = 2;
    public int deadState = 3;

    //FPS
    final int FPS  = 60;

    //Object Array
    public SuperObject obj[] = new SuperObject[30];
    public MON_goblin monster[] = new MON_goblin[5];
    public AssetSetter aSetter = new AssetSetter(this);
    public Sound sound = new Sound();
    KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);
    public PathFinder pFinder = new PathFinder(this);


    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    //Set player default position
    public Player player = new Player(this,keyH);
    public UI ui = new UI(this);

    public ArrayList<Projectile> projectiles = new ArrayList<>();

    public void startGameThread(){
        this.gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run(){
//        //Game Loop 1
//        double drawInterval = 1000000000/FPS;
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while(gameThread!=null){
//            //More Precise
//            //long currentTime2 = System.currentTimeMillis();
//            //Update information eg : character position
//            update();
//            //Draw the screen with updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//                if(remainingTime < 0){
//                    remainingTime =0;
//                }
//                Thread.sleep((long)remainingTime);
//
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void run(){
        //Game Loop 2 : Accumulator Method
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS :"+drawCount);
                drawCount = 0;
                timer=0;
            }
        }
    }

    public void update(){
        if(gameState == playState){
            player.update();
            for(int i=0;i<monster.length;i++){
                if(monster[i]!= null){
                    monster[i].update();
                }else{
                    createMonster(i);
                }
            }
            for(int i=0;i<projectiles.size();i++) {
                if(projectiles.get(i) != null){
                    if(projectiles.get(i).alive == true){
                        projectiles.get(i).update();
                    }else{
                        projectiles.remove(i);
                    }
                }
            }
        }
    }
public
    void paintComponent(Graphics g){

        super.paintComponent(g); //Required to print graphics

        Graphics2D g2 = (Graphics2D)g; //Converting graphics to 2D for better control over geometry.

        if(gameState == titleState){
            ui.draw(g2);
        }else if(gameState == playState){
            tileM.draw(g2);//Tiles like a layer should be placed first
            for(int i=0;i < obj.length ; i++){
                if(obj[i] != null){
                    obj[i].draw(g2,this);
                }
            }
            for(int i=0;i<monster.length;i++){
                if(monster[i]!=null){
                    monster[i].draw(g2);
                }
            }
            for(int i=0;i<projectiles.size();i++){
                if(projectiles.get(i)!=null){
                    projectiles.get(i).draw(g2);
                }
            }
            player.draw(g2);//else will hide player
            ui.draw(g2);
            g2.dispose(); // Dispose of graphics context and release any system resources that it takes.
        } else if (gameState == deadState) {
            ui.draw(g2);
        }else if(gameState== winState){
            ui.draw(g2);
        }


}

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        //If set true, all the drawing from this component will be done in an offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void createMonster(int index){
        monster[index] = new MON_goblin(this);
        Random random = new Random();
        int maxX = (player.worldX/tileSize) + 16;
        int maxY = (player.worldY/tileSize) + 16;
        int minX = (player.worldX/tileSize) - 16;
        int minY = (player.worldY/tileSize) - 16;

        if(minX < 0){
            minX = 0;
        }
        if(minY < 0){
            minY = 0;
        }
        if(maxX > maxWorldRow){
            maxX = maxWorldRow -1 ;
        }
        if(maxY > maxWorldCol){
            maxY = maxWorldCol -1 ;
        }

        int x = random.nextInt(maxX - minX)+minX;
        int y = random.nextInt(maxY - minY)+minY;
//        int x = random.nextInt(5)+1;
//        int y = random.nextInt(5)+1;
        while(tileM.mapTileNum[x][y] <=4 & this.tileM.mapTileNum[x][y] !=0 ){
//                x = random.nextInt(gp.maxWorldRow-1)+1;
//                y = random.nextInt(gp.maxWorldCol-1)+1;
            x = random.nextInt(maxX - minX)+minX;
            y = random.nextInt(maxY - minY)+minY;
        }
        this.monster[index].worldX = x*this.tileSize;
        this.monster[index].worldY = y*this.tileSize;
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setMonster();
        playMusic(0);
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    public void playSound(int i){
        sound.setFile(i);
        sound.play();
    }

    public void resetGame(){
        Arrays.fill(obj,null);
        Arrays.fill(monster,null);
        player.setDefaultValue();
        setupGame();
    }
}
