package net.perry.betterthanlauncher.components.customs;

import net.perry.betterthanlauncher.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoundStatics {
    public static final int BORDER_THICKNESS = 2;

    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 50);
    public static final int SHADOW_THICKNESS = 2;

    public static final Image BACKGROUND_IMAGE;
    public static final float BACKGROUND_IMAGE_OPACITY = 0.12F;
    public static final int BACKGROUND_IMAGE_SIZE = 80;

    public static final Dimension SMALL_BUTTON_SIZE = new Dimension(40, 40);

    public static final Dimension SMALL_PANEL_SIZE = new Dimension(400, 400);

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

            BACKGROUND_IMAGE = imageWithAlpha;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load background image", e);
        }
    }

    public static Box.Filler filler(int width, int height) {
        Dimension fillerDimension = new Dimension(width, height);

        Box.Filler filler = new Box.Filler(fillerDimension, fillerDimension, fillerDimension);
        filler.setMinimumSize(fillerDimension);
        filler.setMaximumSize(fillerDimension);
        filler.setPreferredSize(fillerDimension);
        filler.setOpaque(false);

        return filler;
    }
}
