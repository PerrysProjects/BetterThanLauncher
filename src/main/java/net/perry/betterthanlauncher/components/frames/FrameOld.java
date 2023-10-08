package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.CustomScrollBarUI;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Themes;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.ImageTool;
import net.perry.betterthanlauncher.util.tool.ZipTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FrameOld extends JFrame {
    private static Themes theme;

    public FrameOld() {
        Config config = Main.config;
        theme = Themes.DARK;

        if(config.getValue("theme") != null) {
            theme = Themes.valueOf(String.valueOf(config.getValue("theme")).toUpperCase());
        }

        setMinimumSize(new Dimension(750, 400));
        initUI(theme);
    }

    private void initUI(Themes theme) {
        JPanel contentPanel = createContentPanel(theme);
        add(contentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Main.name);
        pack();
        setSize(750, 600);
        setLocationRelativeTo(null);
        createBufferStrategy(4);

        BufferedImage iconBI;
        try {
            iconBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/btl_icon.png")));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        setIconImage(iconBI);

        setVisible(true);
    }

    private JPanel createContentPanel(Themes theme) {
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(theme.getBackground());
        contentPanel.setLayout(new BorderLayout());

        JPanel center = createCenterPanel();
        JPanel sidebar = createSidebarPanel();
        JPanel topBar = createTopBarPanel(center);

        JScrollPane scrollPane = new JScrollPane(center);
        scrollPane.setBackground(null);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(null);
        ((JScrollBar) scrollPane.getComponent(1)).setUI(new CustomScrollBarUI(Color.gray, Color.blue));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setEnabled(true);

        contentPanel.add(sidebar, BorderLayout.EAST);
        contentPanel.add(topBar, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(null);
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        return sidebar;
    }

    private JPanel createTopBarPanel(JPanel parent) {
        JPanel topBar = new JPanel();
        topBar.setBackground(null);
        topBar.setPreferredSize(new Dimension(getWidth(), 60));
        topBar.setLayout(new BorderLayout());

        JPanel create = new JPanel();
        create.setLayout(null);
        create.setBackground(null);
        create.setMaximumSize(new Dimension(200, topBar.getHeight()));
        create.setMinimumSize(new Dimension(200, topBar.getHeight()));
        create.setPreferredSize(new Dimension(200, topBar.getHeight()));

        final boolean[] overCreateButton = {false};

        JButton createButton = new JButton("Create") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 20;
                int width = getWidth();
                int height = getHeight();
                g2.setColor(theme.getBackground());
                g2.fillRect(0, 0, width, height);
                if(overCreateButton[0]) {
                    g2.setColor(theme.getComponents5());
                } else {
                    g2.setColor(theme.getComponents4());
                }
                g2.fillRoundRect(0, 0, width, height, radius, radius);
                g2.setColor(theme.getText2());
                g2.drawString("Create", width / 2 - 18, height / 2 + 5);
            }
        };
        createButton.setPreferredSize(new Dimension(60, 40));
        createButton.setBounds(15, create.getHeight() + 10, 60, 40);
        createButton.setBackground(null);
        createButton.setForeground(theme.getText());
        createButton.setBorder(BorderFactory.createEmptyBorder());
        createButton.addActionListener(e -> createInstanceAction(parent));
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                overCreateButton[0] = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                overCreateButton[0] = false;
            }
        });

        final boolean[] overImportButton = {false};

        JButton importButton = new JButton("Import") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 20;
                int width = getWidth();
                int height = getHeight();
                g2.setColor(theme.getBackground());
                g2.fillRect(0, 0, width, height);
                if(overImportButton[0]) {
                    g2.setColor(theme.getComponents5());
                } else {
                    g2.setColor(theme.getComponents4());
                }
                g2.fillRoundRect(0, 0, width, height, radius, radius);
                g2.setColor(theme.getText2());
                g2.drawString("Import", width / 2 - 18, height / 2 + 5);
            }
        };
        importButton.setPreferredSize(new Dimension(60, 40));
        importButton.setBounds(createButton.getX() + createButton.getWidth() + 15, create.getHeight() + 10, 60, 40);
        importButton.setBackground(null);
        importButton.setForeground(theme.getText());
        importButton.setBorder(BorderFactory.createEmptyBorder());
        importButton.addActionListener(e -> importInstanceAction(parent));
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                overImportButton[0] = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                overImportButton[0] = false;
            }
        });

        create.add(createButton);
        create.add(importButton);

        topBar.add(create, BorderLayout.WEST);
        return topBar;
    }

    private JPanel createCenterPanel() {
        JPanel center = new JPanel();
        center.setBackground(null);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        for(Instance instance : Main.instances.values()) {
            createInstancePanel(instance, center);
        }

        return center;
    }

    private void createInstancePanel(Instance instance, JPanel parent) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 10;
                int width = getWidth() - 20;
                int height = getHeight() - 10;

                g2.setColor(theme.getShadow());
                g2.fillRoundRect(10, 5, width + 3, height + 3, radius + 3, radius + 3);
                g2.setColor(theme.getComponents());
                g2.fillRoundRect(10, 5, width, height, radius, radius);
            }
        };
        panel.setBackground(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.setMinimumSize(new Dimension(1, 100));

        panel.setLayout(new BorderLayout());

        BufferedImage blockerBI;
        try {
            blockerBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/empty.png")));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        ImageIcon blockerIcon = new ImageIcon(blockerBI);
        ImageIcon scaledBlockerIcon = new ImageIcon(blockerIcon.getImage().getScaledInstance(25, 100, Image.SCALE_DEFAULT));
        JLabel blocker = new JLabel(scaledBlockerIcon);

        ImageIcon originalIcon = instance.getIcon();
        ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        ImageIcon roundedIcon = ImageTool.createRoundedIcon(scaledIcon, 60, 60, 10, theme.getComponents2());
        JLabel icon = new JLabel("     " + instance.getName(), roundedIcon, SwingConstants.LEFT);
        icon.setForeground(theme.getText());

        JPanel play = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 10;
                int width = getWidth();
                int height = getHeight() - 10;

                g2.setColor(theme.getShadow());
                g2.fillRoundRect(-10, 5, width + 3, height + 3, radius + 3, radius + 3);
                g2.setColor(theme.getComponents());
                g2.fillRoundRect(-10, 5, width, height, radius, radius);
            }
        };
        play.setLayout(null);

        play.setBackground(null);
        play.setMaximumSize(new Dimension(120, 100));
        play.setMinimumSize(new Dimension(120, 100));
        play.setPreferredSize(new Dimension(120, 100));

        final boolean[] overButton = {false};

        JButton playButton = new JButton("Play") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 20;
                int width = getWidth();
                int height = getHeight();
                g2.setColor(theme.getComponents());
                g2.fillRect(0, 0, width, height);
                if(overButton[0]) {
                    g2.setColor(theme.getComponents5());
                } else {
                    g2.setColor(theme.getComponents4());
                }
                g2.fillRoundRect(0, 0, width, height, radius, radius);
                g2.setColor(theme.getText2());
                g2.drawString("Play", width / 2 - 12, height / 2 + 5);
            }
        };
        playButton.setPreferredSize(new Dimension(60, 40));
        playButton.setBounds(15, play.getHeight() + 30, 60, 40);
        playButton.setBackground(null);
        playButton.setForeground(theme.getText());
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.addActionListener(e -> instance.start());
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                overButton[0] = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                overButton[0] = false;
            }
        });

        play.add(playButton);

        panel.add(blocker, BorderLayout.WEST);
        panel.add(icon, BorderLayout.CENTER);
        panel.add(play, BorderLayout.EAST);

        parent.add(panel);

        parent.revalidate();
        parent.repaint();
    }

    private void createInstanceAction(JPanel parent) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.setBackground(theme.getBackground());

        JTextField nameField = new JTextField();
        nameField.setBackground(null);
        nameField.setForeground(theme.getText());
        nameField.setBorder(BorderFactory.createEmptyBorder());

        String[] versionList = new String[Versions.values().length];
        for(int i = 0; i < Versions.values().length; i++) {
            versionList[i] = Versions.values()[i].getFileName();
        }
        JComboBox<String> versionComboBox = new JComboBox<>(versionList);

        JLabel nameLabel = new JLabel("Enter Instance Name: ");
        nameLabel.setForeground(theme.getText());

        JLabel versionLabel = new JLabel("Select a Version: ");
        versionLabel.setForeground(theme.getText());

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(versionLabel);
        panel.add(versionComboBox);

        int option = JOptionPane.showOptionDialog(null, panel, "Create Instance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if(option == JOptionPane.OK_OPTION) {
            String instanceName = nameField.getText();
            String selectedVersion = Objects.requireNonNull(versionComboBox.getSelectedItem()).toString();

            if(Main.instances == null || !Main.instances.containsKey(instanceName)) {
                Instance.createInstance(instanceName, Versions.fileNameToVersion(selectedVersion));
                Instance instance = Main.instances.get(instanceName);

                createInstancePanel(instance, parent);
            } else {
                JOptionPane.showMessageDialog(null, "An instance with the same name already exists. Please choose a unique name for this instance.", "Duplicate Instance Name", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importInstanceAction(JPanel parent) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP Files", "zip");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if(selectedFile.getName().endsWith(".zip") && ZipTool.containFile(selectedFile.toString(), "instance.conf")) {
                File folder = new File(Main.path + "/instances/" + selectedFile.getName().split("\\.")[0]);
                if(!folder.exists() || folder.listFiles() == null) {
                    folder.mkdirs();

                    Path sourcePath = Paths.get(selectedFile.toURI());
                    Path targetPath = Paths.get(folder.getPath() + "/" + selectedFile.getName());

                    try {
                        Files.copy(sourcePath, targetPath);
                        ZipTool.extract(targetPath.toString(), folder.getPath());

                        Config config = new Config(folder.getPath() + "/instance.conf");
                        String instanceVersion = String.valueOf(config.getValue("version"));

                        Instance.createInstance(folder.getName(), Versions.fileNameToVersion(instanceVersion));
                        Instance instance = Main.instances.get(folder.getName());

                        createInstancePanel(instance, parent);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "An instance with the same name already exists. Please choose a unique name for this instance.", "Duplicate Instance Name", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "The selected file is not a compatible BTL instance in .zip format.", "Invalid File Format", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static Themes getTheme() {
        return theme;
    }
}
