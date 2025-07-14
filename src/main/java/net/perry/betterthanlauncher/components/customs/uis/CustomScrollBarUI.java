package net.perry.betterthanlauncher.components.customs.uis;

import net.perry.betterthanlauncher.components.customs.RoundComponent;
import net.perry.betterthanlauncher.components.customs.RoundScrollPane;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.Serial;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private final Color thumb;
    private final int trackBorder;
    private final Color border;
    private final boolean top;

    private final Dimension d = new Dimension();

    public CustomScrollBarUI(Color thumb) {
        this(thumb, 0);
    }

    public CustomScrollBarUI(Color thumb, int trackBorder) {
        this(thumb, trackBorder, null, false);
    }

    public CustomScrollBarUI(Color thumb, int trackBorder, Color border, boolean top) {
        this.thumb = thumb;
        this.trackBorder = trackBorder;
        this.border = border;
        this.top = top;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Serial
            private static final long serialVersionUID = -3592643796245558676L;

            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize() {
                return d;
            }
        };
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(border != null) {
            g2.setColor(border);
            if(top) {
                g2.drawLine(0, 0, c.getWidth(), 0);
            } else {
                g2.drawLine(0, 0, 0, c.getHeight());
            }
        }

        g2.dispose();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(thumb);

        int arc = Math.min(r.width, r.height) - 2 * trackBorder;

        int x = r.x + trackBorder;
        int y = r.y + trackBorder;
        int width = r.width - 2 * trackBorder;
        int height = r.height - 2 * trackBorder;

        if(border != null) {
            if(top) {
                y += RoundComponent.borderThickness;
                height -= RoundComponent.borderThickness;
            } else {
                x += RoundComponent.borderThickness;
                width -= RoundComponent.borderThickness;
            }
        }

        if(width % 2 != 0) width--;
        if(height % 2 != 0) height--;

        if(arc % 2 != 0) arc--;

        g2.fillRoundRect(x, y, width, height, arc, arc);
        g2.setPaint(thumb);
        g2.drawRoundRect(x, y, width, height, arc, arc);

        g2.dispose();
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);
        scrollbar.repaint();
    }

    public Color getThumb() {
        return thumb;
    }

    public int getTrackBorder() {
        return trackBorder;
    }

    public Color getBorder() {
        return border;
    }

    public boolean isTop() {
        return top;
    }
}