package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.CustomScrollBarUI;
import net.perry.betterthanlauncher.components.Icons;
import net.perry.betterthanlauncher.components.RoundButton;
import net.perry.betterthanlauncher.components.RoundPanel;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Themes;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.ImageTool;
import net.raphimc.mcauth.step.java.StepMCProfile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MainPanel extends JPanel {
    private Config config;
    private Themes theme;
    private Map<String, Instance> instances;
    private Auth auth;
    private StepMCProfile.MCProfile loadedProfile;

    private JPanel centerPanel;
    private JPanel instancesPanel;
    private JPanel sideBar;
    private JPanel topBar;
    private JPanel bottomBar;
    private JScrollPane centerScrollPane;
    private JPanel addPanel;
    private JPanel accountPanel;

    public MainPanel() {
        config = Main.config;
        theme = Themes.DARK;
        instances = Main.instances;
        auth = Main.auth;
        loadedProfile = auth.getLoadedProfile();

        if(config.getValue("theme") != null) {
            theme = Themes.valueOf(String.valueOf(config.getValue("theme")).toUpperCase());
        }

        setBackground(theme.getBackground());
        setLayout(new BorderLayout());

        Dimension leftMinSize = new Dimension(25, getHeight());
        Dimension leftPrefSize = new Dimension(25, getHeight());
        Dimension leftMaxSize = new Dimension(25, getHeight());
        Box.Filler leftFiller = new Box.Filler(leftMinSize, leftPrefSize, leftMaxSize);

        centerPanel = new JPanel();
        centerPanel.setBackground(null);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        instancesPanel = new JPanel();
        instancesPanel.setBackground(null);
        instancesPanel.setLayout(new BoxLayout(instancesPanel, BoxLayout.Y_AXIS));

        createCenterPanel();

        sideBar = new JPanel();
        sideBar.setBackground(null);
        sideBar.setPreferredSize(new Dimension(60, getHeight()));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

        RoundButton homeButton = new RoundButton(Icons.HOME, theme.getComponents4(), theme.getComponents5(), theme.getBackground(), theme.getText2());
        homeButton.setMaximumSize(new Dimension(40, 40));
        homeButton.setMinimumSize(new Dimension(40, 40));
        homeButton.setPreferredSize(new Dimension(40, 40));
        homeButton.addActionListener(e -> {
            remove(addPanel);
            remove(accountPanel);
            add(centerScrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension addMinSize = new Dimension(sideBar.getWidth(), 15);
        Dimension addPrefSize = new Dimension(sideBar.getWidth(), 15);
        Dimension addMaxSize = new Dimension(sideBar.getWidth(), 15);
        Box.Filler addFiller = new Box.Filler(addMinSize, addPrefSize, addMaxSize);

        RoundButton addButton = new RoundButton(Icons.ADD, theme.getComponents4(), theme.getComponents5(), theme.getBackground(), theme.getText2());
        addButton.setMaximumSize(new Dimension(40, 40));
        addButton.setMinimumSize(new Dimension(40, 40));
        addButton.setPreferredSize(new Dimension(40, 40));
        addButton.addActionListener(e -> {
            remove(centerScrollPane);
            remove(accountPanel);
            add(addPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon skinHeadImage = null;
        try {
            URL skinHeadUrl = new URL("https://mc-heads.net/avatar/" + loadedProfile.name());
            skinHeadImage = new ImageIcon(ImageIO.read(skinHeadUrl));
        } catch(IOException e) {
            Logger.error(e);
        }
        RoundButton profileButton = new RoundButton(skinHeadImage, theme.getBackground(), theme.getBackground(), theme.getBackground());
        profileButton.setMaximumSize(new Dimension(40, 40));
        profileButton.setMinimumSize(new Dimension(40, 40));
        profileButton.setPreferredSize(new Dimension(40, 40));
        profileButton.addActionListener(e -> {
            remove(centerScrollPane);
            remove(addPanel);
            add(accountPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension bottomMinSize = new Dimension(sideBar.getWidth(), 15);
        Dimension bottomPrefSize = new Dimension(sideBar.getWidth(), 15);
        Dimension bottomMaxSize = new Dimension(sideBar.getWidth(), 15);
        Box.Filler bottomFiller = new Box.Filler(bottomMinSize, bottomPrefSize, bottomMaxSize);

        sideBar.add(homeButton);
        sideBar.add(addFiller);
        sideBar.add(addButton);
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(profileButton);
        sideBar.add(bottomFiller);

        topBar = new JPanel();
        topBar.setBackground(null);
        topBar.setPreferredSize(new Dimension(getWidth(), 60));
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

        centerScrollPane = new JScrollPane(centerPanel);
        centerScrollPane.setBackground(null);
        centerScrollPane.getViewport().setBackground(null);
        centerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        ((JScrollBar) centerScrollPane.getComponent(1)).setUI(new CustomScrollBarUI(theme.getComponents2(), theme.getComponents3()));
        centerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerScrollPane.setEnabled(true);

        bottomBar = new JPanel();
        bottomBar.setBackground(null);
        bottomBar.setPreferredSize(new Dimension(getWidth(), 25));
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));

        addPanel = new JPanel();
        addPanel.setBackground(null);
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        accountPanel = new JPanel();
        accountPanel.setBackground(null);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.X_AXIS));

        RoundPanel accountInfoPanel = new RoundPanel(theme.getComponents(), theme.getShadow());
        accountInfoPanel.setMaximumSize(new Dimension(400, 400));
        accountInfoPanel.setMinimumSize(new Dimension(400, 400));
        accountInfoPanel.setPreferredSize(new Dimension(400, 400));
        accountInfoPanel.setLayout(new BoxLayout(accountInfoPanel, BoxLayout.X_AXIS));

        ImageIcon skinImage = null;
        try {
            URL skinUrl = new URL("https://mc-heads.net/body/" + loadedProfile.name() + "/right");
            skinImage = ImageTool.changeImageWidth(new ImageIcon(ImageIO.read(skinUrl).getScaledInstance(138, 330, Image.SCALE_SMOOTH)), 200);
        } catch(IOException e) {
            Logger.error(e);
        }

        JLabel skinLabel = new JLabel(skinImage, SwingConstants.LEFT);
        skinLabel.setForeground(theme.getText());
        skinLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Name: " + loadedProfile.name());
        nameLabel.setForeground(theme.getText());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel UUIDLabel = new JLabel("UUID: " + loadedProfile.id());
        UUIDLabel.setForeground(theme.getText());
        UUIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundButton changeButton = new RoundButton("Log Out", theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
        changeButton.setMaximumSize(new Dimension(60, 40));
        changeButton.setMinimumSize(new Dimension(60, 40));
        changeButton.setPreferredSize(new Dimension(60, 40));
        changeButton.addActionListener(e -> {
            if(auth.getFile().delete()) {
                Main.restart();
            }
        });
        changeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        accountInfoPanel.add(skinLabel);
        accountInfoPanel.add(Box.createHorizontalGlue());
        accountInfoPanel.add(changeButton);
        accountInfoPanel.add(Box.createHorizontalGlue());

        accountPanel.add(Box.createHorizontalGlue());
        accountPanel.add(accountInfoPanel);
        accountPanel.add(Box.createHorizontalGlue());

        add(leftFiller, BorderLayout.WEST);
        add(sideBar, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);
        add(centerScrollPane, BorderLayout.CENTER);
        add(bottomBar, BorderLayout.SOUTH);
    }

    private void createCenterPanel() {
        centerPanel.removeAll();
        instancesPanel.removeAll();

        for(Instance instance : instances.values()) {
            int height = 100;

            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BorderLayout());

            RoundPanel instancePanel = new RoundPanel(theme.getComponents(), theme.getShadow());
            instancePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
            instancePanel.setMinimumSize(new Dimension(1, height));
            instancePanel.setLayout(new BoxLayout(instancePanel, BoxLayout.X_AXIS));

            Dimension iconMinSize = new Dimension(25, height);
            Dimension iconPrefSize = new Dimension(25, height);
            Dimension iconMaxSize = new Dimension(25, height);
            Box.Filler iconFiller = new Box.Filler(iconMinSize, iconPrefSize, iconMaxSize);

            ImageIcon originalImageIcon = instance.getIcon();
            ImageIcon scaledImageIcon = new ImageIcon(originalImageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
            ImageIcon roundedImageIcon = ImageTool.createRoundedIcon(scaledImageIcon, 60, 60, 10, theme.getComponents2());
            JLabel iconLabel = new JLabel(roundedImageIcon, SwingConstants.LEFT);
            iconLabel.setForeground(theme.getText());
            iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            Dimension nameMinSize = new Dimension(25, height);
            Dimension namePrefSize = new Dimension(25, height);
            Dimension nameMaxSize = new Dimension(25, height);
            Box.Filler nameFiller = new Box.Filler(nameMinSize, namePrefSize, nameMaxSize);

            JLabel nameLabel = new JLabel(instance.getName());
            nameLabel.setForeground(theme.getText());
            nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            RoundButton editButton = new RoundButton(Icons.EDIT, theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
            editButton.setMaximumSize(new Dimension(40, 40));
            editButton.setMinimumSize(new Dimension(40, 40));
            editButton.setPreferredSize(new Dimension(40, 40));
            editButton.addActionListener(e -> {
            });
            editButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            Dimension editMinSize = new Dimension(25, height);
            Dimension editPrefSize = new Dimension(25, height);
            Dimension editMaxSize = new Dimension(25, height);
            Box.Filler editFiller = new Box.Filler(editMinSize, editPrefSize, editMaxSize);

            RoundButton playButton = new RoundButton(Icons.PLAY, theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
            playButton.setMaximumSize(new Dimension(40, 40));
            playButton.setMinimumSize(new Dimension(40, 40));
            playButton.setPreferredSize(new Dimension(40, 40));
            playButton.addActionListener(e -> instance.start());
            playButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            Dimension playMinSize = new Dimension(25, height);
            Dimension playPrefSize = new Dimension(25, height);
            Dimension playMaxSize = new Dimension(25, height);
            Box.Filler playFiller = new Box.Filler(playMinSize, playPrefSize, playMaxSize);

            instancePanel.add(iconFiller);
            instancePanel.add(iconLabel);
            instancePanel.add(nameFiller);
            instancePanel.add(nameLabel);
            instancePanel.add(Box.createHorizontalGlue());
            instancePanel.add(editButton);
            instancePanel.add(editFiller);
            instancePanel.add(playButton);
            instancePanel.add(playFiller);

            Dimension rightEmptyMinSize = new Dimension(25, height);
            Dimension rightEmptyPrefSize = new Dimension(25, height);
            Dimension rightEmptyMaxSize = new Dimension(25, height);
            Box.Filler rightEmptyFiller = new Box.Filler(rightEmptyMinSize, rightEmptyPrefSize, rightEmptyMaxSize);

            emptyPanel.add(instancePanel, BorderLayout.WEST);
            emptyPanel.add(rightEmptyFiller, BorderLayout.EAST);

            instancesPanel.add(instancePanel);

            if(!instances.values().toArray()[instances.size() - 1].equals(instance)) {
                Dimension betweenMinSize = new Dimension(1, 10);
                Dimension betweenPrefSize = new Dimension(1, 10);
                Dimension betweenMaxSize = new Dimension(Integer.MAX_VALUE, 10);
                Box.Filler betweenFiller = new Box.Filler(betweenMinSize, betweenPrefSize, betweenMaxSize);

                instancesPanel.add(betweenFiller);
            } else {
                instancesPanel.add(Box.createVerticalGlue());
            }
        }

        Dimension centerMinSize = new Dimension(10, centerPanel.getHeight());
        Dimension centerPrefSize = new Dimension(10, centerPanel.getHeight());
        Dimension centerMaxSize = new Dimension(10, centerPanel.getHeight());
        Box.Filler centerFiller = new Box.Filler(centerMinSize, centerPrefSize, centerMaxSize);

        centerPanel.add(instancesPanel);
        centerPanel.add(centerFiller);

        revalidate();
        repaint();
    }

    private void addInstance() {
        Instance.createInstance("Test", Versions.BTA_1_7_7_0_02);
        instances = Main.instances;
        createCenterPanel();
    }
}
