package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Icons;
import net.perry.betterthanlauncher.components.RoundButton;
import net.perry.betterthanlauncher.components.RoundCheckBox;
import net.perry.betterthanlauncher.components.RoundPanel;
import net.perry.betterthanlauncher.components.uis.CustomComboBoxUI;
import net.perry.betterthanlauncher.components.uis.CustomScrollBarUI;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Themes;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.ImageTool;
import net.perry.betterthanlauncher.util.tool.ZipTool;
import net.raphimc.mcauth.step.java.StepMCProfile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class MainPanel extends JPanel {
    private String path;
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
    private JPanel instanceEditPanel;

    public MainPanel() {
        path = Main.path;
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
            changePanel(centerScrollPane);
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
            changePanel(addPanel);
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
            changePanel(accountPanel);
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

        Dimension logoMinSize = new Dimension(25, topBar.getHeight());
        Dimension logoPrefSize = new Dimension(25, topBar.getHeight());
        Dimension logoMaxSize = new Dimension(25, topBar.getHeight());
        Box.Filler logoFiller = new Box.Filler(logoMinSize, logoPrefSize, logoMaxSize);

        ImageIcon logoIcon = null;
        try {
            BufferedImage logoBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/logo.png")));
            logoIcon = new ImageIcon(logoBI.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch(IOException e) {
            Logger.error(e);
        }

        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.LEFT);
        logoLabel.setForeground(theme.getText());
        logoLabel.setHorizontalTextPosition(JLabel.CENTER);
        logoLabel.setVerticalTextPosition(JLabel.TOP);
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        Dimension btaModdingMinSize = new Dimension(25, topBar.getHeight());
        Dimension btaModdingPrefSize = new Dimension(25, topBar.getHeight());
        Dimension btaModdingMaxSize = new Dimension(25, topBar.getHeight());
        Box.Filler btaModdingFiller = new Box.Filler(btaModdingMinSize, btaModdingPrefSize, btaModdingMaxSize);

        ImageIcon discordIcon = null;
        try {
            BufferedImage discordBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/discord_icon.png")));
            discordIcon = new ImageIcon(discordBI.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            discordIcon = ImageTool.addBackgroundColor(discordIcon, theme.getComponents4());
        } catch(IOException e) {
            Logger.error(e);
        }

        RoundButton btaModding = new RoundButton(Icons.GLOBUS, theme.getComponents4(), theme.getComponents5(), theme.getBackground(), theme.getText2());
        btaModding.setMaximumSize(new Dimension(40, 40));
        btaModding.setMinimumSize(new Dimension(40, 40));
        btaModding.setPreferredSize(new Dimension(40, 40));
        btaModding.addActionListener(e -> {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create("https://bta-modding.nouma-vallee.fr/mods/?query="));
                } catch(Exception ex) {
                    Logger.error(ex);
                }
            }
        });
        btaModding.setAlignmentY(Component.CENTER_ALIGNMENT);

        Dimension babricDCMinSize = new Dimension(72, topBar.getHeight());
        Dimension babricDCPrefSize = new Dimension(72, topBar.getHeight());
        Dimension babricDCMaxSize = new Dimension(72, topBar.getHeight());
        Box.Filler babricDCFiller = new Box.Filler(babricDCMinSize, babricDCPrefSize, babricDCMaxSize);

        RoundButton babricDiscord = new RoundButton(discordIcon, theme.getComponents4(), theme.getComponents5(), theme.getBackground());
        babricDiscord.setMaximumSize(new Dimension(40, 40));
        babricDiscord.setMinimumSize(new Dimension(40, 40));
        babricDiscord.setPreferredSize(new Dimension(40, 40));
        babricDiscord.addActionListener(e -> {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create("https://discord.com/invite/jvwD8BKq5e"));
                } catch(Exception ex) {
                    Logger.error(ex);
                }
            }
        });
        babricDiscord.setAlignmentY(Component.CENTER_ALIGNMENT);

        topBar.add(logoFiller);
        topBar.add(logoLabel);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(btaModding);
        topBar.add(btaModdingFiller);
        //topBar.add(btaDiscord);
        //topBar.add(btaDCFiller);
        topBar.add(babricDiscord);
        topBar.add(babricDCFiller);

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
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.X_AXIS));

        RoundPanel createInstancePanel = new RoundPanel(theme.getComponents(), theme.getShadow()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setStroke(new BasicStroke(2));
                g2.setColor(theme.getComponents2());
                g2.drawLine(250, 25, 250, 375);
            }
        };
        createInstancePanel.setMaximumSize(new Dimension(400, 400));
        createInstancePanel.setMinimumSize(new Dimension(400, 400));
        createInstancePanel.setPreferredSize(new Dimension(400, 400));
        createInstancePanel.setLayout(null);

        JTextField nameField = new JTextField();
        nameField.setBounds(48, 103, 150, 30);
        nameField.setMaximumSize(new Dimension(150, 30));
        nameField.setMinimumSize(new Dimension(150, 30));
        nameField.setPreferredSize(new Dimension(150, 30));
        nameField.setBackground(theme.getComponents2());
        nameField.setForeground(theme.getText());
        nameField.setBorder(new LineBorder(theme.getComponents2(), 2));
        nameField.setText("Enter name");
        nameField.setFont(nameField.getFont().deriveFont(Font.BOLD, 12f));
        nameField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if(nameField.getText().equals("Enter name")) {
                    nameField.setText("");
                    nameField.setForeground(theme.getText());
                }
            }

            public void focusLost(FocusEvent e) {
                if(nameField.getText().isEmpty()) {
                    nameField.setText("Enter name");
                    nameField.setForeground(theme.getText());
                }
            }
        });

        String[] versionList = new String[Versions.values().length];
        for(int i = 0; i < Versions.values().length; i++) {
            versionList[i] = Versions.values()[i].getFileName();
        }
        JComboBox<String> versionComboBox = new JComboBox<>(versionList);
        versionComboBox.setBounds(48, 158, 150, 30);
        versionComboBox.setMaximumSize(new Dimension(150, 30));
        versionComboBox.setMinimumSize(new Dimension(150, 30));
        versionComboBox.setPreferredSize(new Dimension(150, 30));
        versionComboBox.setBackground(theme.getComponents2());
        versionComboBox.setForeground(theme.getText());
        versionComboBox.setUI(new CustomComboBoxUI(theme.getComponents2(), theme.getComponents2(), theme.getText(), theme.getComponents2()));

        RoundCheckBox babricCheckBox = new RoundCheckBox("Babric", theme.getText(), theme.getComponents2(), theme.getComponents(), theme.getText());
        babricCheckBox.setBounds(48, 213, 65, 20);
        babricCheckBox.setMaximumSize(new Dimension(65, 20));
        babricCheckBox.setMinimumSize(new Dimension(65, 20));
        babricCheckBox.setPreferredSize(new Dimension(65, 20));
        babricCheckBox.setBackground(null);
        babricCheckBox.setForeground(theme.getText());

        RoundButton createInstanceButton = new RoundButton("Create", theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
        createInstanceButton.setBounds(93, 258, 60, 40);
        createInstanceButton.setMaximumSize(new Dimension(60, 40));
        createInstanceButton.setMinimumSize(new Dimension(60, 40));
        createInstanceButton.setPreferredSize(new Dimension(60, 40));
        createInstanceButton.addActionListener(e -> {
            if(!nameField.getText().equals("Enter name") && addInstance(nameField.getText(), Versions.fileNameToVersion(Objects.requireNonNull(versionComboBox.getSelectedItem()).toString()), babricCheckBox.isSelected())) {
                nameField.setText("Enter name");
                versionComboBox.setSelectedIndex(0);
            } else {
                nameField.setForeground(Color.decode("#ed4337"));
            }
        });

        RoundButton importButton = new RoundButton("Import", theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
        importButton.setBounds(293, 160, 60, 40);
        importButton.setMaximumSize(new Dimension(60, 40));
        importButton.setMinimumSize(new Dimension(60, 40));
        importButton.setPreferredSize(new Dimension(60, 40));
        importButton.addActionListener(e -> {
            importInstance();
        });

        createInstancePanel.add(nameField);
        createInstancePanel.add(versionComboBox);
        createInstancePanel.add(babricCheckBox);
        createInstancePanel.add(createInstanceButton);
        createInstancePanel.add(importButton);

        addPanel.add(Box.createHorizontalGlue());
        addPanel.add(createInstancePanel);
        addPanel.add(Box.createHorizontalGlue());

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

        JLabel skinLabel = new JLabel(loadedProfile.name(), skinImage, SwingConstants.LEFT);
        skinLabel.setForeground(theme.getText());
        skinLabel.setHorizontalTextPosition(JLabel.CENTER);
        skinLabel.setVerticalTextPosition(JLabel.TOP);
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

        instanceEditPanel = new JPanel();
        instanceEditPanel.setBackground(null);
        instanceEditPanel.setLayout(new BoxLayout(instanceEditPanel, BoxLayout.X_AXIS));

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

            JLabel nameLabel = new JLabel(instance.getDisplayName());
            nameLabel.setForeground(theme.getText());
            nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            RoundButton folderButton = new RoundButton(Icons.FOLDER, theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
            folderButton.setMaximumSize(new Dimension(40, 40));
            folderButton.setMinimumSize(new Dimension(40, 40));
            folderButton.setPreferredSize(new Dimension(40, 40));
            folderButton.addActionListener(e -> {
                try {
                    if(Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        File directory = new File(instance.getInstancePath());

                        if(directory.exists()) {
                            desktop.open(directory);
                        }
                    } else {
                        Logger.error(new Exception("Desktop is not supported on this platform"));
                    }
                } catch(IOException ex) {
                    Logger.error(ex);
                }
            });
            folderButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            Dimension folderMinSize = new Dimension(25, height);
            Dimension folderPrefSize = new Dimension(25, height);
            Dimension folderMaxSize = new Dimension(25, height);
            Box.Filler folderFiller = new Box.Filler(folderMinSize, folderPrefSize, folderMaxSize);

            RoundButton editButton = new RoundButton(Icons.EDIT, theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
            editButton.setMaximumSize(new Dimension(40, 40));
            editButton.setMinimumSize(new Dimension(40, 40));
            editButton.setPreferredSize(new Dimension(40, 40));
            editButton.addActionListener(e -> {
                createInstanceEditPanel(instance);
                changePanel(instanceEditPanel);
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
            instancePanel.add(folderButton);
            instancePanel.add(folderFiller);
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

            Dimension betweenMinSize = new Dimension(1, 10);
            Dimension betweenPrefSize = new Dimension(1, 10);
            Dimension betweenMaxSize = new Dimension(Integer.MAX_VALUE, 10);
            Box.Filler betweenFiller = new Box.Filler(betweenMinSize, betweenPrefSize, betweenMaxSize);

            instancesPanel.add(betweenFiller);
            instancesPanel.add(instancePanel);

            if(instances.values().toArray()[instances.size() - 1].equals(instance)) {
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

    private void createInstanceEditPanel(Instance instance) {
        instanceEditPanel.removeAll();

        RoundPanel settingsPanel = new RoundPanel(theme.getComponents(), theme.getShadow());
        settingsPanel.setMaximumSize(new Dimension(400, 400));
        settingsPanel.setMinimumSize(new Dimension(400, 400));
        settingsPanel.setPreferredSize(new Dimension(400, 400));
        settingsPanel.setLayout(null);

        final int[] memory = {instance.getMemory()};

        JLabel memoryLabel = new JLabel("Memory: " + memory[0]);
        memoryLabel.setForeground(theme.getText());
        memoryLabel.setBounds(25, 25, 350, 30);
        memoryLabel.setMinimumSize(new Dimension(350, 30));
        memoryLabel.setMaximumSize(new Dimension(350, 30));
        memoryLabel.setPreferredSize(new Dimension(350, 30));

        JScrollBar memoryScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
        memoryScrollBar.setUnitIncrement(16);
        memoryScrollBar.setMaximum(20490);
        memoryScrollBar.setMinimum(0);
        memoryScrollBar.setValue(memory[0]);
        memoryScrollBar.setBounds(25, 60, 350, 20);
        memoryScrollBar.setMinimumSize(new Dimension(350, 20));
        memoryScrollBar.setMaximumSize(new Dimension(350, 20));
        memoryScrollBar.setPreferredSize(new Dimension(350, 20));
        memoryScrollBar.setUI(new CustomScrollBarUI(theme.getComponents2(), theme.getComponents3()));
        memoryScrollBar.addAdjustmentListener(e -> {
            memory[0] = memoryScrollBar.getValue();
            memoryLabel.setText("Memory: " + memory[0]);
        });

        RoundButton saveButton = new RoundButton("Save", theme.getComponents4(), theme.getComponents5(), theme.getComponents(), theme.getText2());
        saveButton.setBounds(170, 335, 60, 40);
        saveButton.setMaximumSize(new Dimension(60, 40));
        saveButton.setMinimumSize(new Dimension(60, 40));
        saveButton.setPreferredSize(new Dimension(60, 40));
        saveButton.addActionListener(e -> {
            instance.setMemory(memory[0]);
            Logger.log("Test");
            Logger.log(String.valueOf(instance.getConfig().getValue("memory")));
        });

        settingsPanel.add(memoryLabel);
        settingsPanel.add(memoryScrollBar);
        settingsPanel.add(saveButton);


        /*JPanel modPanel = new JPanel();
        modPanel.setBackground(null);
        modPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        JScrollPane modScrollPane;
        modScrollPane = new JScrollPane(modPanel);
        modScrollPane.setBackground(null);
        modScrollPane.getViewport().setBackground(null);
        modScrollPane.setBorder(BorderFactory.createEmptyBorder());
        ((JScrollBar) modScrollPane.getComponent(1)).setUI(new CustomScrollBarUI(theme.getComponents2(), theme.getComponents3()));
        modScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        modScrollPane.setEnabled(true);*/

        instanceEditPanel.add(Box.createHorizontalGlue());
        instanceEditPanel.add(settingsPanel);
        instanceEditPanel.add(Box.createHorizontalGlue());

        revalidate();
        repaint();
    }

    private boolean importInstance() {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP Files", "zip");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if(selectedFile.getName().endsWith(".zip") && ZipTool.containFile(selectedFile.toString(), "instance.conf")) {
                File folder = new File(path + "/instances/" + selectedFile.getName().replace(".zip", "").replaceAll(" ", "_"));
                if(!folder.exists() || folder.listFiles() == null) {
                    folder.mkdirs();

                    Path sourcePath = Paths.get(selectedFile.toURI());
                    Path targetPath = Paths.get(folder.getPath() + "/" + selectedFile.getName().replaceAll(" ", "_"));

                    try {
                        Files.copy(sourcePath, targetPath);
                        ZipTool.extract(targetPath.toString(), folder.getPath());

                        Config config = new Config(folder.getPath() + "/instance.conf");
                        String instanceVersion = String.valueOf(config.getValue("version"));
                        boolean babric = Boolean.parseBoolean((String) config.getValue("babric"));

                        Instance.createInstance(folder.getName(), Versions.fileNameToVersion(instanceVersion), babric);
                        instances = Main.instances;

                        createCenterPanel();
                        changePanel(centerScrollPane);
                    } catch(IOException e) {
                        Logger.error(e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "An instance with the same name already exists. Please choose a unique name for this instance.", "Duplicate Instance Name", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "The selected file is not a compatible BTL instance in .zip format.", "Invalid File Format", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void changePanel(JComponent component) {
        remove(centerScrollPane);
        remove(addPanel);
        remove(accountPanel);
        remove(instanceEditPanel);

        add(component, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private boolean addInstance(String name, Versions versions, boolean babric) {
        File folder = new File(path + "/instances/" + name.replaceAll(" ", "_"));
        if(!folder.exists() || folder.listFiles() == null) {
            folder.mkdirs();

            Instance.createInstance(name, versions, babric);
            instances = Main.instances;

            createCenterPanel();
            changePanel(centerScrollPane);
        } else {
            return false;
        }
        return true;
    }
}
