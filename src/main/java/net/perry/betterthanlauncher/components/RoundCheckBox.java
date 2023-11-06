package net.perry.betterthanlauncher.components;

import javax.swing.*;
import java.awt.*;

public class RoundCheckBox extends JCheckBox {
    private String text;
    private Color checkmarkColor;
    private Color background;
    private Color borderBackground;
    private Color foreground;

    private boolean hoverCheckBox;

    private final int border = 4;

    public RoundCheckBox(Color checkmarkColor, Color background, Color borderBackground, Color foreground) {
        this.checkmarkColor = checkmarkColor;
        this.background = background;
        this.borderBackground = borderBackground;
        this.foreground = foreground;

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBackground(borderBackground);
    }

    public RoundCheckBox(String text, Color checkmarkColor, Color background, Color borderBackground, Color foreground) {
        this.text = text;
        this.checkmarkColor = checkmarkColor;
        this.background = background;
        this.borderBackground = borderBackground;
        this.foreground = foreground;

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBackground(borderBackground);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ly = (getHeight() - 16) / 2;
        if(isSelected()) {
            g2.setColor(background);
            g2.fillRoundRect(1, ly, 17, 17, border, border);

            int px[] = {4, 8, 14, 12, 8, 6};
            int py[] = {ly + 8, ly + 14, ly + 5, ly + 3, ly + 10, ly + 6};
            g2.setColor(checkmarkColor);
            g2.fillPolygon(px, py, px.length);
        } else {
            g2.setColor(background);
            g2.fillRoundRect(1, ly, 17, 17, border, border);
        }
        if(text != null) {
            g2.setColor(foreground);
            g2.drawString(text, 23, getHeight() / 2 + 5);
        }
        g2.dispose();
    }
}
