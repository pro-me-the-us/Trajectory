package main;

import entity.Player;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

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
    public final int maxWorldCol = 100;
    public final int maxWorldRow =100;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;

    //FPS
    final int FPS  = 60;

    KeyHandler keyH = new KeyHandler();
    TileManager tileM = new TileManager(this);

    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    //Set player default position
    public Player player = new Player(this,keyH);


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
        player.update();
    }
public
    void paintComponent(Graphics g){

        super.paintComponent(g); //Required to print graphics

        Graphics2D g2 = (Graphics2D)g; //Converting graphics to 2D for better control over geometry.
        tileM.draw(g2); //Tiles like a layer should be placed first
        player.draw(g2);//else will hide player
        g2.dispose(); // Dispose of graphics context and release any system resources that it takes.

    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        //If set true, all the drawing from this component will be done in an offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
}
