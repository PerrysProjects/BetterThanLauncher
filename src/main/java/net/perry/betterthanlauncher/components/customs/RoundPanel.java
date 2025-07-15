package net.perry.betterthanlauncher.components.customs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundPanel extends JPanel {
    private final Color background;
    private final Color border;
    private final boolean shadow;
    private final boolean leftSided;
    private final boolean image;

    public RoundPanel(Color background) {
        this(background, null, false, false, false);
    }

    public RoundPanel(Color background, boolean shadow) {
        this(background, null, shadow, false, false);
    }

    public RoundPanel(Color background, boolean shadow, boolean image) {
        this(background, null, shadow, false, image);
    }

    public RoundPanel(Color background, Color border) {
        this(background, border, false, false, false);
    }
    
    public RoundPanel(Color background, Color border, boolean leftSided) {
        this(background, border, false, leftSided, false);
    }

    public RoundPanel(Color background, Color border, boolean leftSided, boolean image) {
        this(background, border, false, leftSided, image);
    }

    private RoundPanel(Color background, Color border, boolean shadow, boolean leftSided, boolean image) {
        this.background = background;
        this.border = border;
        this.shadow = shadow;
        this.leftSided = leftSided;
        this.image = image;

        setOpaque(false);
        setBackground(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 10;
        int width = getWidth();
        int height = getHeight();

        if(shadow) {
            g2.setColor(RoundStatics.SHADOW_COLOR);
            g2.fillRoundRect(0, 0, width, height, radius, radius);
            width -= RoundStatics.SHADOW_THICKNESS;
            height -= RoundStatics.SHADOW_THICKNESS;
        } else if(leftSided) {
            width += radius;
            height += radius;
        }

        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        if(image) {
            Shape clip = new RoundRectangle2D.Float(0, 0, width, height, radius, radius);
            g2.setClip(clip);

            Image backgroundImage = RoundStatics.BACKGROUND_IMAGE;
            int imgW = RoundStatics.BACKGROUND_IMAGE_SIZE;
            int imgH = RoundStatics.BACKGROUND_IMAGE_SIZE;

            Composite originalComposite = g2.getComposite();

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, RoundStatics.BACKGROUND_IMAGE_OPACITY));

            for(int x = 0; x < width; x += imgW) {
                for(int y = 0; y < height; y += imgH) {
                    g2.drawImage(backgroundImage, x, y, imgW, imgH, this);
                }
            }

            g2.setComposite(originalComposite);
            g2.setClip(null);
        }

        if(border != null) {
            width -= RoundStatics.BORDER_THICKNESS / 2;
            height -= RoundStatics.BORDER_THICKNESS / 2;

            g2.setColor(border);
            g2.setStroke(new BasicStroke(RoundStatics.BORDER_THICKNESS));
            g2.drawRoundRect(0, 0, width, height, radius, radius);
        }

        g2.dispose();
    }

    @Override
    public Color getBackground() {
        return background;
    }

    public boolean hasShadow() {
        return shadow;
    }

    public boolean isLeftSided() {
        return leftSided;
    }

    public Color getBorderColor() {
        return border;
    }
}
