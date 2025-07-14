package net.perry.betterthanlauncher.components.customs;

import net.perry.betterthanlauncher.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoundComponent {
    public static final int borderThickness = 2;

    public static final Color shadowColor = new Color(0, 0, 0, 50);
    public static final int shadowThickness = 2;

    public static final Image backgroundImage;
    public static final float backgroundImageOpacity = 0.1F;
    public static final int backgroundImageSize = 80;

    static {
        try {
            BufferedImage rawImage = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("background/stone.png"));

            BufferedImage imageWithAlpha = new BufferedImage(
                    rawImage.getWidth(),
                    rawImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            Graphics2D g2 = imageWithAlpha.createGraphics();
            g2.drawImage(rawImage, 0, 0, null);
            g2.dispose();

            backgroundImage = imageWithAlpha;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load background image", e);
        }
    }

}
