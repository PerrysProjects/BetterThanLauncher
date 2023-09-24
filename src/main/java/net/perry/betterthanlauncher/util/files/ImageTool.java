package net.perry.betterthanlauncher.util.files;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageTool {
    public static ImageIcon createRoundedIcon(ImageIcon originalIcon, int width, int height, int radius, Color fillColor) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, width, height, radius, radius);

        g2d.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        g2d.drawImage(originalIcon.getImage(), 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(image);
    }

    public static ImageIcon addBackgroundColor(ImageIcon originalIcon, Color color) {
        Image originalImage = originalIcon.getImage();

        BufferedImage newImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(newImage);
    }
}
