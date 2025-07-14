package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Icon;
import net.perry.betterthanlauncher.components.customs.RoundButton;
import net.perry.betterthanlauncher.components.customs.RoundCheckBox;
import net.perry.betterthanlauncher.components.customs.RoundPanel;
import net.perry.betterthanlauncher.components.customs.RoundScrollPane;
import net.perry.betterthanlauncher.components.customs.uis.CustomComboBoxUI;
import net.perry.betterthanlauncher.components.customs.uis.CustomScrollBarUI;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.components.Theme;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.OsManager;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.BrowserTool;
import net.perry.betterthanlauncher.util.tool.ImageTool;
import net.perry.betterthanlauncher.util.tool.PanelTool;
import net.perry.betterthanlauncher.util.tool.ZipTool;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    private final String path;
    private final Config config;
    private Theme theme;

    private Map<String, Instance> instances;

    private final Auth auth;
    private final StepFullJavaSession.FullJavaSession loadedProfile;

    private JPanel sideBar;
    private JPanel topBar;

    private JPanel centerPanel;
    private JPanel instancesPanel;
    private RoundScrollPane centerScrollPane;
    private RoundPanel addPanel;
    private RoundPanel accountPanel;
    private RoundPanel instanceEditPanel;

    public MainPanel() {
        path = Main.PATH;
        config = Main.CONFIG;
        theme = Theme.DARK;

        instances = Instance.getInstances();

        auth = Main.AUTH;
        loadedProfile = auth.getLoadedProfile();

        if(config.getValue("theme") != null) {
            theme = Theme.valueOf(String.valueOf(config.getValue("theme")).toUpperCase());
        }

        setBackground(theme.getComponents());
        setLayout(new BorderLayout());

        createSideBar();
        createTopBar();

        createCenterPanel();
        createAddPanel();
        createAccountPanel();
        createInstanceEditPanel();

        add(sideBar, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        add(centerScrollPane, BorderLayout.CENTER);
    }

    private void createSideBar() {
        sideBar = new JPanel();
        sideBar.setBackground(null);
        sideBar.setPreferredSize(new Dimension(60, getHeight()));
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(40, 40);

        RoundButton homeButton = new RoundButton(Icon.HOME, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        homeButton.setMaximumSize(buttonSize);
        homeButton.setMinimumSize(buttonSize);
        homeButton.setPreferredSize(buttonSize);
        homeButton.addActionListener(e -> changePanel(centerScrollPane));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundButton addButton = new RoundButton(Icon.ADD, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        addButton.setMaximumSize(buttonSize);
        addButton.setMinimumSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        addButton.addActionListener(e -> changePanel(addPanel));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon skinHeadImage = null;
        try {
            URL skinHeadUrl = new URL("https://mc-heads.net/avatar/" + loadedProfile.getMcProfile().getName());
            skinHeadImage = new ImageIcon(ImageIO.read(skinHeadUrl));
        } catch(IOException e) {
            Logger.error(e);
        }

        RoundButton profileButton = new RoundButton(skinHeadImage, theme.getBackground(), theme.getBackground());
        profileButton.setMaximumSize(buttonSize);
        profileButton.setMinimumSize(buttonSize);
        profileButton.setPreferredSize(buttonSize);
        profileButton.addActionListener(e -> changePanel(accountPanel));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sideBar.add(PanelTool.filler(sideBar.getWidth(), 15));
        sideBar.add(homeButton);
        sideBar.add(PanelTool.filler(sideBar.getWidth(), 15));
        sideBar.add(addButton);
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(profileButton);
        sideBar.add(PanelTool.filler(sideBar.getWidth(), 15));
    }

    private void createTopBar() {
        topBar = new JPanel();
        topBar.setBackground(null);
        topBar.setPreferredSize(new Dimension(getWidth(), 60));
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

        ImageIcon logoIcon = null;
        try {
            BufferedImage logoBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/logo.png")));
            logoIcon = new ImageIcon(logoBI.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch(IOException e) {
            Logger.error(e);
        }

        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.LEFT);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setVerticalAlignment(JLabel.CENTER);
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(40, 40);

        RoundButton modrinthButton = new RoundButton(Icon.GLOBUS, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        modrinthButton.setMaximumSize(buttonSize);
        modrinthButton.setMinimumSize(buttonSize);
        modrinthButton.setPreferredSize(buttonSize);
        modrinthButton.addActionListener(e -> {
            try {
                BrowserTool.open(URI.create("https://modrinth.com/mods?g=categories:bta-babric"));
            } catch(Exception ex) {
                Logger.error(ex);
            }
        });
        modrinthButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        ImageIcon discordIcon = null;
        try {
            BufferedImage discordBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/discord_icon.png")));
            discordIcon = new ImageIcon(discordBI.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        } catch(IOException e) {
            Logger.error(e);
        }

        RoundButton babricDiscord = new RoundButton(discordIcon, theme.getComponents4(), theme.getComponents5());
        babricDiscord.setMaximumSize(buttonSize);
        babricDiscord.setMinimumSize(buttonSize);
        babricDiscord.setPreferredSize(buttonSize);
        babricDiscord.addActionListener(e -> {
            try {
                BrowserTool.open(URI.create("https://discord.com/invite/jvwD8BKq5e"));
            } catch(Exception ex) {
                Logger.error(ex);
            }
        });
        babricDiscord.setAlignmentY(Component.CENTER_ALIGNMENT);

        topBar.add(PanelTool.filler(10, topBar.getHeight()));
        topBar.add(logoLabel);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(modrinthButton);
        topBar.add(PanelTool.filler(25, topBar.getHeight()));
        topBar.add(babricDiscord);
        topBar.add(PanelTool.filler(30, topBar.getHeight()));
    }

    private void createCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));

        instancesPanel = new JPanel();
        instancesPanel.setOpaque(false);
        instancesPanel.setLayout(new BoxLayout(instancesPanel, BoxLayout.Y_AXIS));

        centerScrollPane = new RoundScrollPane(centerPanel, theme.getBackground(), theme.getComponents2(), true, true);

        JScrollBar horizontalBar = centerScrollPane.getHorizontalScrollBar();
        horizontalBar.setUI(new CustomScrollBarUI(theme.getComponents2(), 4, centerScrollPane.getBorderColor(), false));

        JScrollBar verticalBar = centerScrollPane.getVerticalScrollBar();
        verticalBar.setUI(new CustomScrollBarUI(theme.getComponents2(), 4, centerScrollPane.getBorderColor(), true));

        centerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        centerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        centerScrollPane.setEnabled(true);

        updateCenterPanel();
    }

    private void updateCenterPanel() {
        centerPanel.removeAll();
        instancesPanel.removeAll();

        Dimension topFillerSize = new Dimension(1, 20);
        Box.Filler topFiller = new Box.Filler(topFillerSize, topFillerSize, topFillerSize);

        instancesPanel.add(topFiller);

        for(Instance instance : instances.values()) {
            int height = 100;

            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BorderLayout());

            RoundPanel instancePanel = new RoundPanel(theme.getComponents(), true);
            instancePanel.setLayout(new BoxLayout(instancePanel, BoxLayout.X_AXIS));

            ImageIcon originalImageIcon = instance.getIcon();
            ImageIcon scaledImageIcon = new ImageIcon(originalImageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
            ImageIcon roundedImageIcon = ImageTool.createRoundedIcon(scaledImageIcon, 10, theme.getComponents2());

            JLabel iconLabel = new JLabel(roundedImageIcon, SwingConstants.LEFT);
            iconLabel.setForeground(theme.getText());
            iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            JLabel nameLabel = new JLabel(instance.getDisplayName());
            nameLabel.setForeground(theme.getText());
            nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            Dimension buttonSize = new Dimension(40, 40);

            RoundButton folderButton = new RoundButton(Icon.FOLDER, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            folderButton.setMaximumSize(buttonSize);
            folderButton.setMinimumSize(buttonSize);
            folderButton.setPreferredSize(buttonSize);
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

            Dimension folderFillerSize = new Dimension(25, height);
            Box.Filler folderFiller = new Box.Filler(folderFillerSize, folderFillerSize, folderFillerSize);

            RoundButton editButton = new RoundButton(Icon.EDIT, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            editButton.setMaximumSize(buttonSize);
            editButton.setMinimumSize(buttonSize);
            editButton.setPreferredSize(buttonSize);
            editButton.addActionListener(e -> {
                updateInstanceEditPanel(instance);
                changePanel(instanceEditPanel);
            });
            editButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            RoundButton playButton = new RoundButton(Icon.PLAY, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            playButton.setMaximumSize(buttonSize);
            playButton.setMinimumSize(buttonSize);
            playButton.setPreferredSize(buttonSize);
            playButton.addActionListener(e -> instance.start());
            playButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            instancePanel.add(PanelTool.filler(25, height));
            instancePanel.add(iconLabel);
            instancePanel.add(PanelTool.filler(25, height));
            instancePanel.add(nameLabel);
            instancePanel.add(Box.createHorizontalGlue());
            instancePanel.add(folderButton);
            instancePanel.add(folderFiller);
            instancePanel.add(editButton);
            instancePanel.add(PanelTool.filler(25, height));
            instancePanel.add(playButton);
            instancePanel.add(PanelTool.filler(25, height));

            emptyPanel.add(instancePanel, BorderLayout.WEST);
            emptyPanel.add(PanelTool.filler(25, height), BorderLayout.EAST);

            instancesPanel.add(instancePanel);
            instancesPanel.add(Box.createRigidArea(new Dimension(1, 10)));

            if(instances.values().toArray()[instances.size() - 1].equals(instance)) {
                instancesPanel.add(Box.createVerticalGlue());
            }
        }

        centerPanel.add(PanelTool.filler(20, centerPanel.getHeight()));
        centerPanel.add(instancesPanel);
        centerPanel.add(PanelTool.filler(10, centerPanel.getHeight()));

        revalidate();
        repaint();
    }

    private void createAddPanel() {
        addPanel = new RoundPanel(theme.getBackground(), theme.getComponents2(), true, true);
        addPanel.setBackground(null);
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.X_AXIS));

        RoundPanel createInstancePanel = new RoundPanel(theme.getComponents(), true) {
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

        JTextField nameField = new JTextField("Enter name");
        nameField.setBounds(48, 103, 150, 30);
        nameField.setBackground(theme.getComponents2());
        nameField.setForeground(theme.getText());
        nameField.setCaretColor(theme.getText());
        nameField.setBorder(new LineBorder(theme.getComponents2(), 2));
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

        String[] versionList = Versions.getAll().stream()
                .map(Versions.VersionInfo::getFileName)
                .toArray(String[]::new);
        JComboBox<String> versionComboBox = new JComboBox<>(versionList);
        versionComboBox.setBounds(48, 158, 150, 30);
        versionComboBox.setBackground(theme.getComponents2());
        versionComboBox.setForeground(theme.getText());
        versionComboBox.setUI(new CustomComboBoxUI(theme.getComponents2(), theme.getComponents2(), theme.getText(), theme.getComponents2()));

        RoundCheckBox babricCheckBox = new RoundCheckBox("Babric", theme.getText(), theme.getComponents2(), theme.getText());
        babricCheckBox.setBounds(48, 213, 65, 20);
        babricCheckBox.setBackground(null);
        babricCheckBox.setForeground(theme.getText());

        RoundButton createInstanceButton = new RoundButton("Create", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        createInstanceButton.setBounds(93, 258, 60, 40);
        createInstanceButton.addActionListener(e -> {
            String name = nameField.getText();
            String selectedVersion = (String) versionComboBox.getSelectedItem();
            Versions.VersionInfo versionInfo = Versions.getByFileName(selectedVersion);

            if (!"Enter name".equals(name) && versionInfo != null && addInstance(name, versionInfo, babricCheckBox.isSelected())) {
                nameField.setText("Enter name");
                versionComboBox.setSelectedIndex(0);
                nameField.setForeground(Color.BLACK); // Optional: reset color
            } else {
                nameField.setForeground(Color.decode("#ed4337"));
            }
        });

        RoundButton importButton = new RoundButton("Import", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        importButton.setBounds(293, 160, 60, 40);
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
    }

    private void createAccountPanel() {
        accountPanel = new RoundPanel(theme.getBackground(), theme.getComponents2(), true, true);
        accountPanel.setBackground(null);
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.X_AXIS));

        RoundPanel accountInfoPanel = new RoundPanel(theme.getComponents(), true);
        accountInfoPanel.setMaximumSize(new Dimension(400, 400));
        accountInfoPanel.setMinimumSize(new Dimension(400, 400));
        accountInfoPanel.setPreferredSize(new Dimension(400, 400));
        accountInfoPanel.setLayout(new BoxLayout(accountInfoPanel, BoxLayout.X_AXIS));

        ImageIcon skinImage = null;
        try {
            URL skinUrl = new URL("https://mc-heads.net/body/" + loadedProfile.getMcProfile().getName() + "/right");
            skinImage = ImageTool.changeImageWidth(new ImageIcon(ImageIO.read(skinUrl).getScaledInstance(138, 330, Image.SCALE_SMOOTH)), 200);
        } catch(IOException e) {
            Logger.error(e);
        }

        JLabel skinLabel = new JLabel(loadedProfile.getMcProfile().getName(), skinImage, SwingConstants.LEFT);
        skinLabel.setForeground(theme.getText());
        skinLabel.setHorizontalTextPosition(JLabel.CENTER);
        skinLabel.setVerticalTextPosition(JLabel.TOP);
        skinLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Name: " + loadedProfile.getMcProfile().getName());
        nameLabel.setForeground(theme.getText());
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel UUIDLabel = new JLabel("UUID: " + loadedProfile.getMcProfile().getId());
        UUIDLabel.setForeground(theme.getText());
        UUIDLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundButton changeButton = new RoundButton("Log Out", theme.getComponents4(), theme.getComponents5(), theme.getText2());
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
    }

    private void createInstanceEditPanel() {
        instanceEditPanel = new RoundPanel(theme.getBackground(), theme.getComponents2(), true, true);
        instanceEditPanel.setLayout(new BoxLayout(instanceEditPanel, BoxLayout.X_AXIS));
    }

    private void updateInstanceEditPanel(Instance instance) {
        instanceEditPanel.removeAll();

        RoundPanel settingsPanel = new RoundPanel(theme.getComponents(), true);
        settingsPanel.setMaximumSize(new Dimension(400, 400));
        settingsPanel.setMinimumSize(new Dimension(400, 400));
        settingsPanel.setPreferredSize(new Dimension(400, 400));
        settingsPanel.setLayout(null);

        final int[] memory = {instance.getMemory()};

        JLabel memoryLabel = new JLabel("Memory: " + memory[0]);
        memoryLabel.setForeground(theme.getText());
        memoryLabel.setBounds(25, 25, 350, 30);

        JScrollBar memoryScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
        memoryScrollBar.setUnitIncrement(512);
        memoryScrollBar.setBlockIncrement(512);
        memoryScrollBar.setMaximum(20490);
        memoryScrollBar.setMinimum(0);
        memoryScrollBar.setValue(memory[0]);
        memoryScrollBar.setBounds(25, 60, 350, 20);
        memoryScrollBar.setUI(new CustomScrollBarUI(theme.getComponents3()));
        memoryScrollBar.setBackground(theme.getComponents2());
        memoryScrollBar.addAdjustmentListener(e -> {
            memory[0] = (e.getValue() / 512) * 512;
            memoryLabel.setText("Memory: " + memory[0]);
        });
        memoryScrollBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int rawValue = memoryScrollBar.getValue();
                int steppedValue = (rawValue / 512) * 512;

                if(rawValue != steppedValue) {
                    memoryScrollBar.setValue(steppedValue);
                }
            }
        });

        String javaExecutableLabel = OsManager.isWindows() ? "java.exe" : "java";
        String javaPathLabelText = String.format("Java Path (e.g. %%JAVA_HOME%% or path/to/%s)", javaExecutableLabel);
        String fileChooserTitle = "Select " + javaExecutableLabel;

        JLabel javaLabel = new JLabel(javaPathLabelText);
        javaLabel.setForeground(theme.getText());
        javaLabel.setBounds(25, 100, 350, 20);

        JTextField javaPathField = new JTextField(instance.getJavaPath());
        javaPathField.setBounds(25, 130, 260, 25);
        javaPathField.setBackground(theme.getComponents2());
        javaPathField.setForeground(theme.getText());
        javaPathField.setCaretColor(theme.getText());
        javaPathField.setBorder(new LineBorder(theme.getComponents2(), 2));
        javaPathField.setFont(javaPathField.getFont().deriveFont(Font.BOLD, 12f));

        RoundButton browseButton = new RoundButton("Select", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        browseButton.setBounds(295, 130, 80, 25);
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle(fileChooserTitle);
            int result = fileChooser.showOpenDialog(settingsPanel);
            if(result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                javaPathField.setText(selectedFile.getAbsolutePath());
            }
        });

        RoundButton saveButton = new RoundButton("Save", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        saveButton.setBounds(170, 335, 60, 40);
        saveButton.addActionListener(e -> {
            instance.setMemory(memory[0]);
            instance.setJavaPath(javaPathField.getText());
        });

        settingsPanel.add(memoryLabel);
        settingsPanel.add(memoryScrollBar);
        settingsPanel.add(javaLabel);
        settingsPanel.add(javaPathField);
        settingsPanel.add(browseButton);
        settingsPanel.add(saveButton);

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
                        boolean babric = Boolean.parseBoolean(String.valueOf(config.getValue("babric")));

                        Versions.VersionInfo versionInfo = Versions.getByFileName(instanceVersion);

                        if(versionInfo != null) {
                            Instance.createInstance(folder.getName(), versionInfo, babric);
                            instances = Instance.getInstances();

                            updateCenterPanel();
                            changePanel(centerScrollPane);
                        } else {
                            Logger.error("Unknown version: " + instanceVersion);
                        }
                    } catch (IOException e) {
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

    private boolean addInstance(String name, Versions.VersionInfo versionInfo, boolean babric) {
        File folder = new File(path + "/instances/" + name.replaceAll(" ", "_"));
        if(!folder.exists() || folder.listFiles() == null) {
            folder.mkdirs();

            Instance.createInstance(name, versionInfo, babric);
            instances = Instance.getInstances();

            updateCenterPanel();
            changePanel(centerScrollPane);
        } else {
            return false;
        }
        return true;
    }
}
