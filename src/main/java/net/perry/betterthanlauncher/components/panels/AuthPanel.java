package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.tool.BrowserTool;
import net.perry.betterthanlauncher.util.tool.PanelTool;
import net.perry.betterthanlauncher.components.customs.RoundButton;
import net.perry.betterthanlauncher.components.Theme;
import net.perry.betterthanlauncher.util.Logger;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

public class AuthPanel extends JPanel {
    StepMsaDeviceCode.MsaDeviceCode msaDeviceCode;

    public AuthPanel(StepMsaDeviceCode.MsaDeviceCode msaDeviceCode) {
        Theme theme = Theme.DARK;
        this.msaDeviceCode = msaDeviceCode;

        setBackground(theme.getBackground());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel uriLabel = new JLabel("Go to " + msaDeviceCode.getVerificationUri());
        uriLabel.setForeground(theme.getText());
        uriLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel codeLabel = new JLabel("And enter this code: " + msaDeviceCode.getUserCode());
        codeLabel.setForeground(theme.getText());
        codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(80, 40);

        RoundButton copyButton = new RoundButton("Copy Code", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        copyButton.setMaximumSize(buttonSize);
        copyButton.setMinimumSize(buttonSize);
        copyButton.setPreferredSize(buttonSize);
        copyButton.addActionListener(e -> {
            StringSelection code = new StringSelection(msaDeviceCode.getUserCode());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(code, code);
        });
        copyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundButton goButton = new RoundButton("Open Link", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        goButton.setMaximumSize(buttonSize);
        goButton.setMinimumSize(buttonSize);
        goButton.setPreferredSize(buttonSize);
        goButton.addActionListener(e -> {
            try {
                URI uri = URI.create(msaDeviceCode.getVerificationUri());
                BrowserTool.open(uri);
            } catch(Exception ex) {
                Logger.error("", ex);
            }
        });
        goButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(PanelTool.filler(getWidth(), 25));
        add(uriLabel);
        add(codeLabel);
        add(PanelTool.filler(getWidth(), 25));
        add(copyButton);
        add(PanelTool.filler(getWidth(), 25));
        add(goButton);
    }
}
