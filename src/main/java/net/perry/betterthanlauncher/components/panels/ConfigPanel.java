package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Theme;
import net.perry.betterthanlauncher.components.customs.RoundButton;
import net.perry.betterthanlauncher.components.customs.RoundPanel;
import net.perry.betterthanlauncher.components.customs.RoundStatics;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.tool.ImageTool;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ConfigPanel extends RoundPanel {
    private final MainPanel panel;
    private final Theme theme;
    private final Auth auth;
    private final StepFullJavaSession.FullJavaSession loadedProfile;

    public ConfigPanel(MainPanel panel, Color background, Color border, boolean leftSided, boolean image) {
        super(background, border, leftSided, image);

        this.panel = panel;
        theme = Main.THEME;
        auth = Main.AUTH;
        loadedProfile = auth.getLoadedProfile();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        RoundPanel accountInfoPanel = new RoundPanel(theme.getComponents(), true);
        accountInfoPanel.setMaximumSize(RoundStatics.SMALL_PANEL_SIZE);
        accountInfoPanel.setMinimumSize(RoundStatics.SMALL_PANEL_SIZE);
        accountInfoPanel.setPreferredSize(RoundStatics.SMALL_PANEL_SIZE);
        accountInfoPanel.setLayout(new BoxLayout(accountInfoPanel, BoxLayout.X_AXIS));

        ImageIcon skinImage = null;
        try {
            URL skinUrl = new URL("https://mc-heads.net/player/" + loadedProfile.getMcProfile().getId());
            skinImage = ImageTool.changeImageWidth(new ImageIcon(ImageIO.read(skinUrl).getScaledInstance(138, 330, Image.SCALE_SMOOTH)), 200);
        } catch(IOException e) {
            Logger.error(e);
        }

        JLabel skinLabel = new JLabel(skinImage, SwingConstants.LEFT);
        skinLabel.setForeground(theme.getText());
        skinLabel.setHorizontalTextPosition(JLabel.CENTER);
        skinLabel.setVerticalTextPosition(JLabel.TOP);
        skinLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel informationPanel = new JPanel();
        informationPanel.setSize(accountInfoPanel.getWidth() / 2, accountInfoPanel.getHeight() / 2);
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(loadedProfile.getMcProfile().getName());
        nameLabel.setForeground(theme.getText());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel(String.valueOf(loadedProfile.getMcProfile().getId()).substring(0, 14) + "...");
        idLabel.setForeground(theme.getText());
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundButton changeButton = new RoundButton("Log Out", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        changeButton.setMaximumSize(new Dimension(70, 40));
        changeButton.setMinimumSize(new Dimension(70, 40));
        changeButton.setPreferredSize(new Dimension(70, 40));
        changeButton.addActionListener(e -> {
            if(auth.getFile().delete()) {
                Main.restart();
            }
        });
        changeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        informationPanel.add(RoundStatics.filler(25, 25));
        informationPanel.add(nameLabel);
        informationPanel.add(RoundStatics.filler(25, 25));
        informationPanel.add(idLabel);
        informationPanel.add(Box.createVerticalGlue());
        informationPanel.add(changeButton);
        informationPanel.add(RoundStatics.filler(25, 25));
        ;
        accountInfoPanel.add(informationPanel);
        accountInfoPanel.add(Box.createHorizontalGlue());
        accountInfoPanel.add(skinLabel);
        accountInfoPanel.add(Box.createHorizontalGlue());

        add(Box.createHorizontalGlue());
        add(accountInfoPanel);
        add(Box.createHorizontalGlue());
    }
}
