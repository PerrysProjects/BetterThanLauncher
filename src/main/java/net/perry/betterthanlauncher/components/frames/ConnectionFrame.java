package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.panels.ConnectionPanel;

import javax.swing.*;

public class ConnectionFrame extends JFrame {
    public ConnectionFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Main.name + " | Connection");
        setContentPane(new ConnectionPanel());
        pack();
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Main.icon);

        setVisible(true);
    }
}
