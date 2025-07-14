package net.perry.betterthanlauncher.components.customs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundScrollPane extends JScrollPane {
    private final Color background;
    private final Color border;
    private final boolean shadow;
    private final boolean leftSided;
    private final boolean image;

    public RoundScrollPane(Component view, Color background) {
        this(view, background, null, false, false, false);
    }

    public RoundScrollPane(Component view, Color background, boolean shadow) {
        this(view, background, null, shadow, false, false);
    }

    public RoundScrollPane(Component view, Color background, boolean shadow, boolean image) {
        this(view, background, null, shadow, false, image);
    }

    public RoundScrollPane(Component view, Color background, Color border) {
        this(view, background, border, false, false, false);
    }

    public RoundScrollPane(Component view, Color background, Color border, boolean leftSided) {
        this(view, background, border, false, leftSided, false);
    }

    public RoundScrollPane(Component view, Color background, Color border, boolean leftSided, boolean image) {
        this(view, background, border, false, leftSided, image);
    }

    private RoundScrollPane(Component view, Color background, Color border, boolean shadow, boolean leftSided, boolean image) {
        super(view);

        this.background = background;
        this.border = border;
        this.shadow = shadow;
        this.leftSided = leftSided;
        this.image = image;

        setOpaque(false);
        getViewport().setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder());
        if(border != null) {
            if(leftSided) {
                setViewportBorder(BorderFactory.createEmptyBorder(RoundComponent.borderThickness, RoundComponent.borderThickness, 0, 0));
            } else {
                setViewportBorder(BorderFactory.createEmptyBorder(RoundComponent.borderThickness, 0, RoundComponent.borderThickness, 0));
            }
        }

        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 10;
        int width = getWidth();
        int height = getHeight();

        if(shadow) {
            g2.setColor(RoundComponent.shadowColor);
            g2.fillRoundRect(0, 0, width, height, radius, radius);
            width -= RoundComponent.shadowThickness;
            height -= RoundComponent.shadowThickness;
        } else if(leftSided) {
            width += radius;
            height += radius;
        }

        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        Shape clip = new RoundRectangle2D.Float(0, 0, width, height, radius, radius);
        g2.setClip(clip);

        if(image) {
            Image backgroundImage = RoundComponent.backgroundImage;
            int imgW = RoundComponent.backgroundImageSize;
            int imgH = RoundComponent.backgroundImageSize;

            Composite originalComposite = g2.getComposite();

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, RoundComponent.backgroundImageOpacity));

            for(int x = 0; x < width; x += imgW) {
                for(int y = 0; y < height; y += imgH) {
                    g2.drawImage(backgroundImage, x, y, imgW, imgH, this);
                }
            }

            g2.setComposite(originalComposite);
        }

        g2.setClip(null);

        if(border != null) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(RoundComponent.borderThickness));
            g2.drawRoundRect(0, 0, width, height, radius, radius);
        }

        g2.dispose();
    }

    @Override
    public Color getBackground() {
        return background;
    }

    public Color getBorderColor() {
        return border;
    }

    public boolean isLeftSided() {
        return leftSided;
    }
}
