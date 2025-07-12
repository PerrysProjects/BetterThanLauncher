package net.perry.betterthanlauncher.util.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageTool {
    public static ImageIcon createRoundedIcon(ImageIcon imageIcon, int radius, Color fillColor) {
        int w = imageIcon.getImage().getWidth(null);
        int h = imageIcon.getImage().getHeight(null);
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(fillColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, radius, radius));

        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(imageIcon.getImage(), 0, 0, null);

        g2.dispose();

        return new ImageIcon(output);
    }

    public static ImageIcon makeSquareImage(ImageIcon inputIcon) {
        Image inputImage = inputIcon.getImage();

        int width = inputImage.getWidth(null);
        int height = inputImage.getHeight(null);

        int newSize = Math.max(width, height);
        BufferedImage outputImage = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();

        int x = (newSize - width) / 2;
        int y = (newSize - height) / 2;
        g2d.drawImage(inputImage, x, y, null);
        g2d.dispose();

        return new ImageIcon(outputImage);
    }

    public static ImageIcon changeImageWidth(ImageIcon inputIcon, int width) {
        Image inputImage = inputIcon.getImage();

        int height = inputImage.getHeight(null);

        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();

        int x = width / 2 - inputImage.getWidth(null) / 2;
        g2d.drawImage(inputImage, x, 0, null);
        g2d.dispose();

        return new ImageIcon(outputImage);
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
