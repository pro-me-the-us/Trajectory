package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import object.Obj_Gem;
import object.Obj_Heart;
import object.SuperObject;

import javax.imageio.ImageIO;


public class UI {

    GamePanel gp;
    Font Dialog;
    Font maruMonica;
    BufferedImage gemImage;
    Obj_Heart heart ;
    public UI(GamePanel gp){
        this.gp = gp;
        Dialog = new Font("Dialog",Font.BOLD,30);
        maruMonica = new Font("Breathe Fire III",Font.BOLD,60);
        Obj_Gem gem = new Obj_Gem();
        heart = new Obj_Heart(gp);
        gemImage = gem.image;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(Dialog);
        g2.setColor(Color.WHITE);



        if(gp.gameState == gp.titleState){
            try {
                drawTitleScreen(g2);
            }catch (Exception e){

            }
        }else if(gp.gameState == gp.playState){
            drawHearts(g2);
            g2.drawImage(gemImage,gp.tileSize/2,3*gp.tileSize/2,gp.tileSize,gp.tileSize,null);
            g2.drawString("x "+gp.player.gemCount,74,108);
        }else if(gp.gameState == gp.deadState){
            try{
                drawHearts(g2);
                drawDeathScreen(g2);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else if (gp.gameState == gp.winState) {
            try {
                drawWinScreen(g2);
            }catch (Exception _){

            }
        }

    }

    public void drawHearts(Graphics2D g2){
        int full = gp.player.life/2;
        int half = (int)Math.ceil(gp.player.life%2);
        int empty = (int) ((gp.player.maxLife/2) - ((float)gp.player.life/2));
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;

        for(;full != 0 ; full--){
            g2.drawImage(heart.heart_full,x,y,gp.tileSize,gp.tileSize,null);
            x+= gp.tileSize;
        }

        for(;half!=0 ; half--){
            g2.drawImage(heart.heart_half,x,y,gp.tileSize,gp.tileSize,null);
            x+= gp.tileSize;
        }

        for(;empty!=0 ; empty--){
            g2.drawImage(heart.heart_empty,x,y,gp.tileSize,gp.tileSize,null);
            x+= gp.tileSize;
        }


    }

    public void drawTitleScreen(Graphics2D g2) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/background.jpg")));
        BufferedImage belo = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/boy_down_1.png")));
        g2.drawImage(image,0,0,gp.screenWidth,gp.screenHeight,null);
        g2.drawImage(belo,gp.tileSize*7,gp.tileSize*6,gp.tileSize*2,gp.tileSize*2,null);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));
        String text = "TRAJECTORY";
        String play = "Press Enter to Play !";
        g2.setColor(Color.BLACK);
        g2.drawString(text,95,4*gp.tileSize);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,228,10*gp.tileSize);

        g2.setColor(Color.WHITE);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));
        g2.drawString(text,90,4*gp.tileSize-10);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,225,10*gp.tileSize-5);
    }

    public void drawDeathScreen(Graphics2D g2) throws IOException{
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/death_background.png")));
        g2.drawImage(image,0,0,gp.screenWidth,gp.screenHeight,null);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));
        String text = "YOU LOST !";
        String play = "Press Enter to Retry !";
        g2.setColor(Color.BLACK);
        g2.drawString(text,145,6*gp.tileSize);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,218,8*gp.tileSize);
        g2.setColor(Color.ORANGE);

        g2.setColor(Color.RED);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));
        g2.drawString(text,138,6*gp.tileSize-5);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,213,8*gp.tileSize-4);
    }

    public void drawWinScreen(Graphics2D g2) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/win_background.jpg")));
        BufferedImage prize = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Tiles/prize.png")));
        g2.drawImage(image,0,0,gp.screenWidth,gp.screenHeight,null);
        g2.drawImage(prize,gp.tileSize*7,gp.tileSize*6,gp.tileSize*2,gp.tileSize*2,null);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));
        String text = "YOU WON !";
        String play = "Press Enter to Restart !";
        g2.setColor(new Color(148, 0, 211));
        g2.drawString(text,150,5*gp.tileSize);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,218,9*gp.tileSize);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,100));

        g2.setColor(new Color(255, 105, 180));
        g2.drawString(text,142,5*gp.tileSize-5);
        g2.setFont(maruMonica.deriveFont(Font.BOLD,40));
        g2.drawString(play,213,9*gp.tileSize-5);
    }
}
