package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.components.RoundButton;
import net.perry.betterthanlauncher.instances.Themes;
import net.perry.betterthanlauncher.util.Logger;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

public class AuthPanel extends JPanel {
    StepMsaDeviceCode.MsaDeviceCode msaDeviceCode;

    public AuthPanel(StepMsaDeviceCode.MsaDeviceCode msaDeviceCode) {
        Themes theme = Themes.DARK;
        this.msaDeviceCode = msaDeviceCode;

        setBackground(theme.getBackground());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Dimension topMinSize = new Dimension(getWidth(), 25);
        Dimension topPrefSize = new Dimension(getWidth(), 25);
        Dimension topMaxSize = new Dimension(getWidth(), 25);
        Box.Filler topFiller = new Box.Filler(topMinSize, topPrefSize, topMaxSize);

        JLabel uriLabel = new JLabel("Go to " + msaDeviceCode.verificationUri());
        uriLabel.setForeground(theme.getText());
        uriLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel codeLabel = new JLabel("And enter this code: " + msaDeviceCode.userCode());
        codeLabel.setForeground(theme.getText());
        codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension textMinSize = new Dimension(getWidth(), 25);
        Dimension textPrefSize = new Dimension(getWidth(), 25);
        Dimension textMaxSize = new Dimension(getWidth(), 25);
        Box.Filler textFiller = new Box.Filler(textMinSize, textPrefSize, textMaxSize);

        RoundButton copyButton = new RoundButton("Copy Code", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        copyButton.setMaximumSize(new Dimension(80, 40));
        copyButton.setMinimumSize(new Dimension(80, 40));
        copyButton.setPreferredSize(new Dimension(80, 40));
        copyButton.addActionListener(e -> {
            StringSelection code = new StringSelection(msaDeviceCode.userCode());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(code, code);
        });
        copyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension copyMinSize = new Dimension(getWidth(), 25);
        Dimension copyPrefSize = new Dimension(getWidth(), 25);
        Dimension copyMaxSize = new Dimension(getWidth(), 25);
        Box.Filler copyFiller = new Box.Filler(copyMinSize, copyPrefSize, copyMaxSize);

        RoundButton goButton = new RoundButton("Open Link", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        goButton.setMaximumSize(new Dimension(80, 40));
        goButton.setMinimumSize(new Dimension(80, 40));
        goButton.setPreferredSize(new Dimension(80, 40));
        goButton.addActionListener(e -> {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(msaDeviceCode.verificationUri()));
                } catch(Exception ex) {
                    Logger.error(ex);
                }
            }
        });
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(topFiller);
        add(uriLabel);
        add(codeLabel);
        add(textFiller);
        add(copyButton);
        add(copyFiller);
        add(goButton);
    }
}
