package net.perry.betterthanlauncher.util.tool;

import javax.swing.*;
import java.awt.*;

public class PanelTool {
    public static Box.Filler filler(int width, int height) {
        Dimension fillerDimension = new Dimension(width, height);

        Box.Filler filler = new Box.Filler(fillerDimension, fillerDimension, fillerDimension);
        filler.setMinimumSize(fillerDimension);
        filler.setMaximumSize(fillerDimension);
        filler.setPreferredSize(fillerDimension);
        filler.setOpaque(false);

        return filler;
    }
}
