package net.perry.betterthanlauncher;

import net.perry.betterthanlauncher.components.CustomScrollBarUI;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Themes;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.files.ImageTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Frame extends JFrame {
    private JPanel center;
    private static Themes theme;

    public Frame() {
        Config config = Main.config;
        theme = Themes.DARK;

        if(config.getValue("theme") != null) {
            theme = Themes.valueOf(String.valueOf(config.getValue("theme")).toUpperCase());
        }

        setMinimumSize(new Dimension(750, 400));
        initUI(theme);
    }

    private void initUI(Themes theme) {
        // Create content panel
        JPanel contentPanel = createContentPanel(theme);
        // Add content panel to the frame
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

        JPanel sidebar = createSidebarPanel();
        JPanel topBar = createTopBarPanel();
        JPanel center = createCenterPanel();

        JScrollPane scrollPane = new JScrollPane(center);
        scrollPane.setBackground(null);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(null);
        //scrollPane.getViewport().setBorder(BorderFactory.createEmptyBorder());
        ((JScrollBar) scrollPane.getComponent(1)).setUI(new CustomScrollBarUI());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

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

    private JPanel createTopBarPanel() {
        JPanel topBar = new JPanel();
        topBar.setBackground(null);
        topBar.setPreferredSize(new Dimension(getWidth(), 50));

        JButton createInstance = new JButton("create");
        createInstance.setBounds(5, 5, 40, 40);
        createInstance.addActionListener(e -> createInstanceAction());

        topBar.add(createInstance);
        return topBar;
    }

    private JPanel createCenterPanel() {
        center = new JPanel();
        center.setBackground(null);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Add existing instances to center panel
        for(Instance instance : Main.instances.values()) {
            createInstancePanel(instance);
        }

        return center;
    }

    private void createInstancePanel(Instance instance) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int radius = 10;
                int width = getWidth() - 20;
                int height = getHeight() - 10;

                g.setColor(theme.getShadow());
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
        ImageIcon roundedIcon = ImageTool.createRoundedIcon(scaledIcon, 60, 60, 10);
        JLabel icon = new JLabel(instance.getName(), roundedIcon, SwingConstants.LEFT);
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

                g.setColor(theme.getShadow());
                g2.fillRoundRect(-20, 5, width + 3, height + 3, radius + 3, radius + 3);
                g2.setColor(theme.getComponents());
                g2.fillRoundRect(-20, 5, width, height, radius, radius);
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
                g2.drawString("Play", width/2 - 12, height/2 + 5);
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

        center.add(panel);

        center.revalidate();
        center.repaint();
    }

    private void createInstanceAction() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JTextField nameField = new JTextField();
        String[] versionList = new String[Versions.values().length];
        for(int i = 0; i < Versions.values().length; i++) {
            versionList[i] = Versions.values()[i].getFileName();
        }
        JComboBox<String> versionComboBox = new JComboBox<>(versionList);

        panel.add(new JLabel("Enter Instance Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Select a Version:"));
        panel.add(versionComboBox);

        int option = JOptionPane.showOptionDialog(null, panel, "Create Instance", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if(option == JOptionPane.OK_OPTION) {
            String instanceName = nameField.getText();
            String selectedVersion = Objects.requireNonNull(versionComboBox.getSelectedItem()).toString();

            Instance.createInstance(instanceName, Versions.fileNameToVersion(selectedVersion));
            Instance instance = Main.instances.get(instanceName);

            createInstancePanel(instance);
        }
    }

    public static Themes getTheme() {
        return theme;
    }
}
