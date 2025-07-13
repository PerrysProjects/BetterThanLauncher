package net.perry.betterthanlauncher.components.customs.uis;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI {
    private Color background;
    private Color pressedBackground;
    private Color triangleBackground;
    private Color highlight;

    public CustomComboBoxUI(Color background, Color pressedBackground, Color triangleBackground, Color highlight) {
        this.background = background;
        this.pressedBackground = pressedBackground;
        this.triangleBackground = triangleBackground;
        this.highlight = highlight;
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, background, pressedBackground, triangleBackground, highlight) {
            @Override
            public int getWidth() {
                return 0;
            }
        };
        return button;
    }
}
