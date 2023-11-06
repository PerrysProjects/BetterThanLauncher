package net.perry.betterthanlauncher.components;

import java.awt.*;
import java.awt.geom.QuadCurve2D;

public class Shapes {
    public static void drawRoundCornerTriangle(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        Point p1 = new Point(x, y);
        Point p2 = new Point(x + width, y + height / 2);
        Point p3 = new Point(x, y + height);

        Point p1p2a = interpolate(p1, p2, 0.2);
        Point p1p2b = interpolate(p1, p2, 0.8);

        Point p2p3a = interpolate(p2, p3, 0.2);
        Point p2p3b = interpolate(p2, p3, 0.8);

        Point p3p1a = interpolate(p3, p1, 0.2);
        Point p3p1b = interpolate(p3, p1, 0.8);

        g2.drawLine(p1p2a.x, p1p2a.y, p1p2b.x, p1p2b.y);
        g2.drawLine(p2p3a.x, p2p3a.y, p2p3b.x, p2p3b.y);
        g2.drawLine(p3p1a.x, p3p1a.y, p3p1b.x, p3p1b.y);

        QuadCurve2D c1 = new QuadCurve2D.Double(p1p2b.x, p1p2b.y, p2.x, p2.y, p2p3a.x, p2p3a.y);
        QuadCurve2D c2 = new QuadCurve2D.Double(p2p3b.x, p2p3b.y, p3.x, p3.y, p3p1a.x, p3p1a.y);
        QuadCurve2D c3 = new QuadCurve2D.Double(p3p1b.x, p3p1b.y, p1.x, p1.y, p1p2a.x, p1p2a.y);

        g2.draw(c1);
        g2.draw(c2);
        g2.draw(c3);
    }

    public static void drawGear(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        Point p1 = new Point(x, y);
        Point p2 = new Point(x + width / 2, y);
        Point p3 = new Point(x + width, y + height / 2);
        Point p4 = new Point(x + width, y + height);
        Point p5 = new Point(x, y + height);

        Point p1p2a = interpolate(p1, p2, 0.2);
        Point p1p2b = interpolate(p1, p2, 0.8);

        Point p3p4a = interpolate(p3, p4, 0.2);
        Point p3p4b = interpolate(p3, p4, 0.8);

        Point p4p5a = interpolate(p4, p5, 0.2);
        Point p4p5b = interpolate(p4, p5, 0.8);

        Point p5p1a = interpolate(p5, p1, 0.2);
        Point p5p1b = interpolate(p5, p1, 0.8);

        g2.drawLine(p1p2a.x, p1p2a.y, p1p2b.x, p1p2b.y);
        g2.drawLine(p3p4a.x, p3p4a.y, p3p4b.x, p3p4b.y);
        g2.drawLine(p4p5a.x, p4p5a.y, p4p5b.x, p4p5b.y);
        g2.drawLine(p5p1a.x, p5p1a.y, p5p1b.x, p5p1b.y);

        QuadCurve2D c1 = new QuadCurve2D.Double(p3p4b.x, p3p4b.y, p4.x, p4.y, p4p5a.x, p4p5a.y);
        QuadCurve2D c2 = new QuadCurve2D.Double(p4p5b.x, p4p5b.y, p5.x, p5.y, p5p1a.x, p5p1a.y);
        QuadCurve2D c3 = new QuadCurve2D.Double(p5p1b.x, p5p1b.y, p1.x, p1.y, p1p2a.x, p1p2a.y);

        g2.draw(c1);
        g2.draw(c2);
        g2.draw(c3);

        Point p6 = new Point(x + width - width / 8, y);
        Point p7 = new Point(x + width, y + height / 8);
        Point p8 = new Point(x + width / 3 + width / 8, y + height - height / 3);
        Point p9 = new Point(x + width / 3, y + height - height / 3);
        Point p10 = new Point(x + width / 3, y + height - height / 3 - width / 8);

        Point p6p7a = interpolate(p6, p7, 0.2);
        Point p6p7b = interpolate(p6, p7, 0.8);

        Point p7p8a = interpolate(p7, p8, 0.2);
        Point p7p8b = interpolate(p7, p8, 0.8);

        Point p8p9a = interpolate(p8, p9, 0.2);
        Point p8p9b = interpolate(p8, p9, 0.8);

        Point p9p10a = interpolate(p9, p10, 0.2);
        Point p9p10b = interpolate(p9, p10, 0.8);

        Point p10p6a = interpolate(p10, p6, 0.2);
        Point p10p6b = interpolate(p10, p6, 0.8);

        g2.drawLine(p6p7a.x, p6p7a.y, p6p7b.x, p6p7b.y);
        g2.drawLine(p7p8a.x, p7p8a.y, p7p8b.x, p7p8b.y);
        g2.drawLine(p8p9a.x, p8p9a.y, p8p9b.x, p8p9b.y);
        g2.drawLine(p9p10a.x, p9p10a.y, p9p10b.x, p9p10b.y);
        g2.drawLine(p10p6a.x, p10p6a.y, p10p6b.x, p10p6b.y);

        QuadCurve2D c4 = new QuadCurve2D.Double(p6p7b.x, p6p7b.y, p7.x, p7.y, p7p8a.x, p7p8a.y);
        QuadCurve2D c5 = new QuadCurve2D.Double(p7p8b.x, p7p8b.y, p8.x, p8.y, p8p9a.x, p8p9a.y);
        QuadCurve2D c6 = new QuadCurve2D.Double(p8p9b.x, p8p9b.y, p9.x, p9.y, p9p10a.x, p9p10a.y);
        QuadCurve2D c7 = new QuadCurve2D.Double(p9p10b.x, p9p10b.y, p10.x, p10.y, p10p6a.x, p10p6a.y);
        QuadCurve2D c8 = new QuadCurve2D.Double(p10p6b.x, p10p6b.y, p6.x, p6.y, p6p7a.x, p6p7a.y);

        g2.draw(c4);
        g2.draw(c5);
        g2.draw(c6);
        g2.draw(c7);
        g2.draw(c8);
    }

    public static void drawFolder(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        Point p1 = new Point(x, y + height / 6);
        Point p2 = new Point(x + width / 2, y + height / 6);
        Point p3 = new Point(x + width / 2, y + height / 4);
        Point p4 = new Point(x + width, y + height / 4);
        Point p5 = new Point(x + width, y + height);
        Point p6 = new Point(x, y + height);

        Point p1p2a = interpolate(p1, p2, 0.2);
        Point p1p2b = interpolate(p1, p2, 0.8);

        Point p2p3a = interpolate(p2, p3, 0.2);
        Point p2p3b = interpolate(p2, p3, 0.8);

        Point p3p4a = interpolate(p3, p4, 0.2);
        Point p3p4b = interpolate(p3, p4, 0.8);

        Point p4p5a = interpolate(p4, p5, 0.2);
        Point p4p5b = interpolate(p4, p5, 0.8);

        Point p5p6a = interpolate(p5, p6, 0.2);
        Point p5p6b = interpolate(p5, p6, 0.8);

        Point p6p1a = interpolate(p6, p1, 0.2);
        Point p6p1b = interpolate(p6, p1, 0.8);

        g2.drawLine(p1p2a.x, p1p2a.y, p1p2b.x, p1p2b.y);
        g2.drawLine(p2p3a.x, p2p3a.y, p2p3b.x, p2p3b.y);
        g2.drawLine(p3p4a.x, p3p4a.y, p3p4b.x, p3p4b.y);
        g2.drawLine(p4p5a.x, p4p5a.y, p4p5b.x, p4p5b.y);
        g2.drawLine(p5p6a.x, p5p6a.y, p5p6b.x, p5p6b.y);
        g2.drawLine(p6p1a.x, p6p1a.y, p6p1b.x, p6p1b.y);

        QuadCurve2D c1 = new QuadCurve2D.Double(p1p2b.x, p1p2b.y, p2.x, p2.y, p2p3a.x, p2p3a.y);
        QuadCurve2D c2 = new QuadCurve2D.Double(p2p3b.x, p2p3b.y, p3.x, p3.y, p3p4a.x, p3p4a.y);
        QuadCurve2D c3 = new QuadCurve2D.Double(p3p4b.x, p3p4b.y, p4.x, p4.y, p4p5a.x, p4p5a.y);
        QuadCurve2D c4 = new QuadCurve2D.Double(p4p5b.x, p4p5b.y, p5.x, p5.y, p5p6a.x, p5p6a.y);
        QuadCurve2D c5 = new QuadCurve2D.Double(p5p6b.x, p5p6b.y, p6.x, p6.y, p6p1a.x, p6p1a.y);
        QuadCurve2D c6 = new QuadCurve2D.Double(p6p1b.x, p6p1b.y, p1.x, p1.y, p1p2a.x, p1p2a.y);

        g2.draw(c1);
        g2.draw(c2);
        g2.draw(c3);
        g2.draw(c4);
        g2.draw(c5);
        g2.draw(c6);
    }

    public static void drawHouse(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        Point p1 = new Point(x, y + height / 3);
        Point p2 = new Point(x + width / 2, y);
        Point p3 = new Point(x + width, y + height / 3);
        Point p4 = new Point(x + width, y + height);
        Point p5 = new Point(x, y + height);

        Point p1p2a = interpolate(p1, p2, 0.2);
        Point p1p2b = interpolate(p1, p2, 0.8);

        Point p2p3a = interpolate(p2, p3, 0.2);
        Point p2p3b = interpolate(p2, p3, 0.8);

        Point p3p4a = interpolate(p3, p4, 0.2);
        Point p3p4b = interpolate(p3, p4, 0.8);

        Point p4p5a = interpolate(p4, p5, 0.2);
        Point p4p5b = interpolate(p4, p5, 0.8);

        Point p5p1a = interpolate(p5, p1, 0.2);
        Point p5p1b = interpolate(p5, p1, 0.8);

        g2.drawLine(p1p2a.x, p1p2a.y, p1p2b.x, p1p2b.y);
        g2.drawLine(p2p3a.x, p2p3a.y, p2p3b.x, p2p3b.y);
        g2.drawLine(p3p4a.x, p3p4a.y, p3p4b.x, p3p4b.y);
        g2.drawLine(p4p5a.x, p4p5a.y, p4p5b.x, p4p5b.y);
        g2.drawLine(p5p1a.x, p5p1a.y, p5p1b.x, p5p1b.y);

        QuadCurve2D c1 = new QuadCurve2D.Double(p1p2b.x, p1p2b.y, p2.x, p2.y, p2p3a.x, p2p3a.y);
        QuadCurve2D c2 = new QuadCurve2D.Double(p2p3b.x, p2p3b.y, p3.x, p3.y, p3p4a.x, p3p4a.y);
        QuadCurve2D c3 = new QuadCurve2D.Double(p3p4b.x, p3p4b.y, p4.x, p4.y, p4p5a.x, p4p5a.y);
        QuadCurve2D c4 = new QuadCurve2D.Double(p4p5b.x, p4p5b.y, p5.x, p5.y, p5p1a.x, p5p1a.y);
        QuadCurve2D c5 = new QuadCurve2D.Double(p5p1b.x, p5p1b.y, p1.x, p1.y, p1p2a.x, p1p2a.y);

        g2.draw(c1);
        g2.draw(c2);
        g2.draw(c3);
        g2.draw(c4);
        g2.draw(c5);
    }

    public static void drawPlus(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        Point p1 = new Point(x, y + height / 2);
        Point p2 = new Point(x + width / 2, y);
        Point p3 = new Point(x + width, y + height / 2);
        Point p4 = new Point(x + width / 2, y + height);

        Point p1p3a = interpolate(p1, p3, 0.2);
        Point p1p3b = interpolate(p1, p3, 0.8);

        Point p2p4a = interpolate(p2, p4, 0.2);
        Point p2p4b = interpolate(p2, p4, 0.8);

        g2.drawLine(p1p3a.x, p1p3a.y, p1p3b.x, p1p3b.y);
        g2.drawLine(p2p4a.x, p2p4a.y, p2p4b.x, p2p4b.y);
    }

    public static void drawGlobus(Graphics2D g2, int x, int y, int width, int height) {
        g2.setStroke(new BasicStroke(2));

        g2.drawOval(x, y, width, height);
        g2.drawOval(x + width / 4, y, width / 2, height);

        g2.drawLine(x + width / 5 - 2, y + height / 4, x + width - width / 5 + 2, y + height / 4);
        g2.drawLine(x + 2, y + height / 2, x + width - 2, y + height / 2);
        g2.drawLine(x + width / 5 - 2, y + height - height / 5, x + width - width / 5 + 2, y + height - height / 4);
    }

    private static Point interpolate(Point p1, Point p2, double t) {
        return new Point((int) Math.round(p1.x * (1 - t) + p2.x * t), (int) Math.round(p1.y * (1 - t) + p2.y * t));
    }
}
