package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class  Main {
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();
        window.setIconImage(ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/Player/belo_1.png"))));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Trajectory");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //Window sized to fit gamePanel

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}
