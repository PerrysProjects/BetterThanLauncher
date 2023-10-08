package net.perry.betterthanlauncher.components;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {
    private Color background;
    private Color shadow;

    public RoundPanel(Color background, Color shadow) {
        this.background = background;
        this.shadow = shadow;

        setBackground(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 10;
        int width = getWidth() - 3;
        int height = getHeight() - 3;

        g2.setColor(shadow);
        g2.fillRoundRect(0, 0, width + 3, height + 3, radius, radius);

        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);
    }
}
