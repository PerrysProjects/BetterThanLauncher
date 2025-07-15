package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Icon;
import net.perry.betterthanlauncher.components.customs.*;
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
import net.perry.betterthanlauncher.util.tool.ZipTool;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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
    private final Theme theme;

    private Map<String, Instance> instances;

    private final Auth auth;
    private final StepFullJavaSession.FullJavaSession loadedProfile;

    private JPanel sideBar;
    private JPanel topBar;

    private JPanel centerPanel;
    private JPanel instancesPanel;
    private RoundScrollPane centerScrollPane;
    private final AddPanel addPanel;
    private final ConfigPanel configPanel;
    private RoundPanel instanceEditPanel;

    public MainPanel() {
        path = Main.PATH;
        config = Main.CONFIG;
        theme = Main.THEME;

        instances = Instance.getInstances();

        auth = Main.AUTH;
        loadedProfile = auth.getLoadedProfile();

        setBackground(theme.getComponents());
        setLayout(new BorderLayout());

        createSideBar();
        createTopBar();

        createCenterPanel();
        addPanel = new AddPanel(this, theme.getBackground(), theme.getComponents2(), true, true);
        configPanel = new ConfigPanel(this, theme.getBackground(), theme.getComponents2(), true, true);
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

        RoundButton homeButton = new RoundButton(Icon.HOME, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        homeButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
        homeButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
        homeButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
        homeButton.addActionListener(e -> changePanel(centerScrollPane));
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundButton addButton = new RoundButton(Icon.ADD, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        addButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
        addButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
        addButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
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
        profileButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
        profileButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
        profileButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
        profileButton.addActionListener(e -> changePanel(configPanel));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sideBar.add(RoundStatics.filler(sideBar.getWidth(), 15));
        sideBar.add(homeButton);
        sideBar.add(RoundStatics.filler(sideBar.getWidth(), 15));
        sideBar.add(addButton);
        sideBar.add(Box.createVerticalGlue());
        sideBar.add(profileButton);
        sideBar.add(RoundStatics.filler(sideBar.getWidth(), 15));
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

        RoundButton modrinthButton = new RoundButton(Icon.GLOBUS, theme.getComponents4(), theme.getComponents5(), theme.getText2());
        modrinthButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
        modrinthButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
        modrinthButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
        modrinthButton.addActionListener(e -> {
            try {
                BrowserTool.open(URI.create("https://www.betterthanadventure.net/"));
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
        babricDiscord.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
        babricDiscord.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
        babricDiscord.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
        babricDiscord.addActionListener(e -> {
            try {
                BrowserTool.open(URI.create("https://discord.com/invite/jvwD8BKq5e"));
            } catch(Exception ex) {
                Logger.error(ex);
            }
        });
        babricDiscord.setAlignmentY(Component.CENTER_ALIGNMENT);

        topBar.add(RoundStatics.filler(10, topBar.getHeight()));
        topBar.add(logoLabel);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(modrinthButton);
        topBar.add(RoundStatics.filler(25, topBar.getHeight()));
        topBar.add(babricDiscord);
        topBar.add(RoundStatics.filler(30, topBar.getHeight()));
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

        instancesPanel.add(RoundStatics.filler(1, 20));

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

            RoundButton folderButton = new RoundButton(Icon.FOLDER, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            folderButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
            folderButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
            folderButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
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

            RoundButton editButton = new RoundButton(Icon.EDIT, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            editButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
            editButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
            editButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
            editButton.addActionListener(e -> {
                updateInstanceEditPanel(instance);
                changePanel(instanceEditPanel);
            });
            editButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            RoundButton playButton = new RoundButton(Icon.PLAY, theme.getComponents4(), theme.getComponents5(), theme.getText2());
            playButton.setMaximumSize(RoundStatics.SMALL_BUTTON_SIZE);
            playButton.setMinimumSize(RoundStatics.SMALL_BUTTON_SIZE);
            playButton.setPreferredSize(RoundStatics.SMALL_BUTTON_SIZE);
            playButton.addActionListener(e -> instance.start());
            playButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            instancePanel.add(RoundStatics.filler(25, height));
            instancePanel.add(iconLabel);
            instancePanel.add(RoundStatics.filler(25, height));
            instancePanel.add(nameLabel);
            instancePanel.add(RoundStatics.filler(25, height));
            instancePanel.add(Box.createHorizontalGlue());
            instancePanel.add(folderButton);
            instancePanel.add(RoundStatics.filler(25, height));
            instancePanel.add(editButton);
            instancePanel.add(RoundStatics.filler(25, height));
            instancePanel.add(playButton);
            instancePanel.add(RoundStatics.filler(25, height));

            emptyPanel.add(instancePanel, BorderLayout.WEST);
            emptyPanel.add(RoundStatics.filler(25, height), BorderLayout.EAST);

            instancesPanel.add(instancePanel);
            instancesPanel.add(Box.createRigidArea(new Dimension(1, 10)));

            if(instances.values().toArray()[instances.size() - 1].equals(instance)) {
                instancesPanel.add(Box.createVerticalGlue());
            }
        }

        centerPanel.add(RoundStatics.filler(20, centerPanel.getHeight()));
        centerPanel.add(instancesPanel);
        centerPanel.add(RoundStatics.filler(10, centerPanel.getHeight()));

        revalidate();
        repaint();
    }

    private void createInstanceEditPanel() {
        instanceEditPanel = new RoundPanel(theme.getBackground(), theme.getComponents2(), true, true);
        instanceEditPanel.setLayout(new BoxLayout(instanceEditPanel, BoxLayout.X_AXIS));
    }

    private void updateInstanceEditPanel(Instance instance) {
        instanceEditPanel.removeAll();

        RoundPanel settingsPanel = new RoundPanel(theme.getComponents(), true);
        settingsPanel.setMaximumSize(RoundStatics.SMALL_PANEL_SIZE);
        settingsPanel.setMinimumSize(RoundStatics.SMALL_PANEL_SIZE);
        settingsPanel.setPreferredSize(RoundStatics.SMALL_PANEL_SIZE);
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

    public boolean importInstance() {
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

    public void changePanel(JComponent component) {
        remove(centerScrollPane);
        remove(addPanel);
        remove(configPanel);
        remove(instanceEditPanel);

        add(component, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public boolean addInstance(String name, Versions.VersionInfo versionInfo, boolean babric) {
        File folder = new File(path + "/instances/" + name.replaceAll(" ", "_"));
        if(!folder.exists() || folder.listFiles() == null) {
            folder.mkdirs();

            Instance.createInstance(name, versionInfo, babric);
            instances = Instance.getInstances();

            updateCenterPanel();
            changePanel(centerScrollPane);

            return true;
        }
        return false;
    }
}
