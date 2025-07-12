package net.perry.betterthanlauncher.components;

import javax.swing.*;
import java.awt.*;

public class RoundScrollPane extends JScrollPane {
    private final Color background;
    private final boolean leftSided;

    public RoundScrollPane(Component view, Color background) {
        this(view, background, false);
    }

    public RoundScrollPane(Component view, Color background, boolean leftSided) {
        super(view);
        this.background = background;
        this.leftSided = leftSided;

        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());

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

        if(leftSided) {
            g2.fillRect(width - radius, 0, radius, radius);
            g2.fillRect(0, height - radius, radius, radius);
            g2.fillRect(width - radius, height - radius, radius, radius);
        }
    }

    @Override
    public Color getBackground() {
        return background;
    }
}
