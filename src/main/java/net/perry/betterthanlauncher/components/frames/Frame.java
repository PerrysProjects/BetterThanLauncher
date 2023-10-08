package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Frame extends JFrame {
    public Frame() {
        setMinimumSize(new Dimension(750, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Main.name);
        pack();
        setSize(750, 600);
        setLocationRelativeTo(null);
        createBufferStrategy(4);

        try {
            BufferedImage iconBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/btl_icon.png")));
            setIconImage(iconBI);
        } catch(IOException e) {
            Logger.error(e);
        }

        setVisible(true);
    }
}
