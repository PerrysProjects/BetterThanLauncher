package net.perry.betterthanlauncher.components.customs;

import net.perry.betterthanlauncher.components.Icon;
import net.perry.betterthanlauncher.components.Shapes;
import net.perry.betterthanlauncher.util.tool.ImageTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {
    private String text;
    private Icon icon;
    private ImageIcon imageIcon;
    private final Color background;
    private final Color hoverBackground;
    private final Color foreground;

    private boolean hoverButton;

    public RoundButton(String text, Color background, Color hoverBackground, Color foreground) {
        this(background, hoverBackground, foreground);
        this.text = text;
    }

    public RoundButton(Icon icon, Color background, Color hoverBackground, Color foreground) {
        this(background, hoverBackground, foreground);
        this.icon = icon;
    }

    public RoundButton(ImageIcon imageIcon, Color background, Color hoverBackground) {
        this(background, hoverBackground, null);
        this.imageIcon = imageIcon;
    }

    private RoundButton(Color background, Color hoverBackground, Color foreground) {
        this.background = background;
        this.hoverBackground = hoverBackground;
        this.foreground = foreground;

        hoverButton = false;

        setBackground(null);
        setForeground(null);
        setBorder(BorderFactory.createEmptyBorder());

        setOpaque(false);
        setContentAreaFilled(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                hoverButton = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                hoverButton = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = getHeight() / 2;
        int width = getWidth();
        int height = getHeight();

        if(hoverButton) {
            g2.setColor(hoverBackground);
        } else {
            g2.setColor(background);
        }
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        g2.setColor(foreground);
        if(text != null) {
            int textWidth = g2.getFontMetrics().stringWidth(text);
            g2.drawString(text, width / 2 - textWidth / 2, height / 2 + 5);
        } else if(icon != null) {
            int iconWidth = height / 2;
            int iconHeight = height / 2;
            switch(icon) {
                case PLAY ->
                        Shapes.drawRoundCornerTriangle(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
                case FOLDER -> Shapes.drawFolder(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
                case EDIT -> Shapes.drawGear(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
                case HOME -> Shapes.drawHouse(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
                case ADD -> Shapes.drawPlus(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
                case GLOBUS -> Shapes.drawGlobus(g2, width / 2 - iconWidth / 2, iconHeight / 2, iconWidth, iconHeight);
            }
        } else {
            ImageIcon imageIcon = new ImageIcon(this.imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
            if(hoverButton) {
                imageIcon = ImageTool.createRoundedIcon(imageIcon, radius, hoverBackground);
            } else {
                imageIcon = ImageTool.createRoundedIcon(imageIcon, radius, background);
            }
            g2.drawImage(imageIcon.getImage(), 0, 0, null);
        }
    }
}
