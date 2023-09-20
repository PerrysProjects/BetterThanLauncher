package net.perry.betterthanlauncher.instances;

import java.awt.*;

public enum Themes {
    DARK(Color.decode("#16181c"), Color.decode("#26292f"), Color.decode("#434956"), Color.decode("#a7b0c0"),
            Color.decode("#2563eb"), Color.decode("#1d4ed8"), Color.decode("#a7b0c0"), Color.decode("#ffffff")),
    WHITE(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);

    private final Color background;
    private final Color components;
    private final Color components2;
    private final Color components3;
    private final Color components4;
    private final Color components5;
    private final Color text;
    private final Color text2;

    private final Color shadow;

    Themes(Color background, Color components, Color components2, Color components3, Color components4, Color components5, Color text, Color text2) {
        this.background = background;
        this.components = components;
        this.components2 = components2;
        this.components3 = components3;
        this.components4 = components4;
        this.components5 = components5;
        this.text = text;
        this.text2 = text2;

        shadow = new Color(0, 0, 0, 50);
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

    public Color getShadow() {
        return shadow;
    }
}
