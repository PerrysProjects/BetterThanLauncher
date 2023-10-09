package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.instances.Themes;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel {
    StepMsaDeviceCode.MsaDeviceCode msaDeviceCode;

    public ConnectionPanel() {
        Themes theme = Themes.DARK;

        setBackground(theme.getBackground());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel textLabel = new JLabel("Unable to establish an internet connection.");
        textLabel.setForeground(theme.getText());
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel2 = new JLabel("Please check your network settings and try again.");
        textLabel2.setForeground(theme.getText());
        textLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(textLabel, BorderLayout.CENTER);
        add(textLabel2, BorderLayout.CENTER);
        add(Box.createVerticalGlue());
    }
}
