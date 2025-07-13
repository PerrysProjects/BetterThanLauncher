package net.perry.betterthanlauncher.components.customs;

import javax.swing.*;
import java.awt.*;

public class RoundScrollPane extends JScrollPane {
    private final Color background;
    private final Color border;
    private final boolean leftSided;

    private final int borderThickness = 2;

    public RoundScrollPane(Component view, Color background) {
        this(view, background, null, false);
    }

    public RoundScrollPane(Component view, Color background, boolean leftSided) {
        this(view, background, null, leftSided);
    }

    public RoundScrollPane(Component view, Color background, Color border) {
        this(view, background, border, false);
    }

    public RoundScrollPane(Component view, Color background, Color border, boolean leftSided) {
        super(view);
        this.background = background;
        this.border = border;
        this.leftSided = leftSided;

        setOpaque(false);
        getViewport().setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder());
        if(border != null) {
            if(leftSided) {
                setViewportBorder(BorderFactory.createEmptyBorder(borderThickness, 0, 0, 0));
            } else {
                setViewportBorder(BorderFactory.createEmptyBorder(borderThickness, 0, borderThickness, 0));
            }
        }

        getVerticalScrollBar().setOpaque(false);
        getHorizontalScrollBar().setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 10;
        int width = getWidth();
        int height = getHeight();

        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        if(border != null) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.drawRoundRect(0, 0, width - 1, height - 1, radius, radius);
        }

        if(leftSided) {
            g2.setColor(background);
            g2.fillRect(radius, 0, width, radius);
            g2.fillRect(0, radius, radius, height);
            g2.fillRect(radius, radius, width, height);

            if(border != null) {
                g2.setColor(border);
                g2.drawLine(0, radius, 0, height);
                g2.drawLine(radius, 0, width, 0);
            }
        }
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
