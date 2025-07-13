package net.perry.betterthanlauncher.components.customs;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {
    private final Color background;
    private final Color border;
    private final boolean shadow;
    private final boolean leftSided;

    public RoundPanel(Color background) {
        this(background, null, false, false);
    }

    public RoundPanel(Color background, boolean shadow) {
        this(background, null, shadow, false);
    }

    public RoundPanel(Color background, Color border) {
        this(background, border, false, false);
    }

    public RoundPanel(Color background, boolean leftSided, boolean dummyFlagToDifferentiate) {
        this(background, null, false, leftSided);
    }

    public RoundPanel(Color background, Color border, boolean shadow) {
        this(background, border, shadow, false);
    }

    public RoundPanel(Color background, Color border, boolean leftSided, boolean dummyFlag, boolean realLeftSided) {
        this(background, border, false, realLeftSided);
    }

    private RoundPanel(Color background, Color border, boolean shadow, boolean leftSided) {
        this.background = background;
        this.border = border;
        this.shadow = shadow;
        this.leftSided = leftSided;

        setOpaque(false);
        setBackground(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 10;
        int width = getWidth();
        int height = getHeight();

        if(shadow) {
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(3, 3, width - 6, height - 6, radius, radius);
        }

        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        if(border != null) {
            g2.setColor(border);
            g2.setStroke(new BasicStroke(2));
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
