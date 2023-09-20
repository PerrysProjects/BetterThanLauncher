package net.perry.betterthanlauncher.util.files;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageTool {
    public static ImageIcon createRoundedIcon(ImageIcon originalIcon, int width, int height, int radius) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a rounded rectangle
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, width, height, radius, radius);

        // Draw the original icon inside the rounded rectangle
        g2d.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
        g2d.drawImage(originalIcon.getImage(), 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(image);
    }
}
