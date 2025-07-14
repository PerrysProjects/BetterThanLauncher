package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Theme;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        setMinimumSize(new Dimension(750, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Main.NAME);
        setBackground(Theme.DARK.getBackground());
        pack();
        setSize(750, 600);
        setLocationRelativeTo(null);
        createBufferStrategy(4);
        setIconImage(Main.ICON);

        setVisible(true);
    }
}
