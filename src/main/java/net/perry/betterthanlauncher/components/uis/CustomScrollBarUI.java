package net.perry.betterthanlauncher.components.uis;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.Serial;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private final Color track;
    private final Color thumb;

    private final Dimension d = new Dimension();

    private final int trackBorder;

    public CustomScrollBarUI(Color track, Color thumb) {
        this(track, thumb, 0);
    }

    public CustomScrollBarUI(Color track, Color thumb, int trackBorder) {
        this.track = track;
        this.thumb = thumb;
        this.trackBorder = trackBorder;
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

        g2.setPaint(track);
        g2.fillRect(r.x, r.y, r.width, r.height);

        g2.setPaint(track);
        g2.drawRect(r.x, r.y, r.width, r.height);

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

        if (width % 2 != 0) width--;
        if (height % 2 != 0) height--;

        if (arc % 2 != 0) arc--;

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
}