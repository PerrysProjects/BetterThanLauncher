package net.perry.betterthanlauncher.components;

import java.awt.*;

public enum Theme {
    DARK(Color.decode("#16181c"), Color.decode("#26292f"), Color.decode("#434956"), Color.decode("#a7b0c0"),
            Color.decode("#2563eb"), Color.decode("#1d4ed8"), Color.decode("#a7b0c0"), Color.decode("#ffffff")),

    WHITE(Color.decode("#ffffff"), Color.decode("#f9fafb"), Color.decode("#e9ecef"), Color.decode("#374151"),
            Color.decode("#cbd5e1"), Color.decode("#94a3b8"), Color.decode("#6b7280"), Color.decode("#111111"));

    private final Color background;
    private final Color components;
    private final Color components2;
    private final Color components3;
    private final Color components4;
    private final Color components5;
    private final Color text;
    private final Color text2;

    Theme(Color background, Color components, Color components2, Color components3, Color components4, Color components5, Color text, Color text2) {
        this.background = background;
        this.components = components;
        this.components2 = components2;
        this.components3 = components3;
        this.components4 = components4;
        this.components5 = components5;
        this.text = text;
        this.text2 = text2;
    }

    public Color[] colors() {
        return new Color[]{background, components, components2, components3, components4, components5, text, text2};
    }

    public Color getBackground() {
        return background;
    }

    public Color getComponents() {
        return components;
    }

    public Color getComponents2() {
        return components2;
    }

    public Color getComponents3() {
        return components3;
    }

    public Color getComponents4() {
        return components4;
    }

    public Color getComponents5() {
        return components5;
    }

    public Color getText() {
        return text;
    }

    public Color getText2() {
        return text2;
    }
}
