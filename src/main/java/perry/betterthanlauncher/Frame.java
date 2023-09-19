package perry.betterthanlauncher;

import perry.betterthanlauncher.instances.Instance;
import perry.betterthanlauncher.instances.Versions;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Frame extends JFrame {
    public Frame() {
        setMinimumSize(new Dimension(750, 400));

        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.BLUE);
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        JPanel topBar = new JPanel();
        topBar.setBackground(Color.GREEN);
        topBar.setPreferredSize(new Dimension(getWidth(), 50));

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JButton createInstance = new JButton("create");
        createInstance.setBounds(5, 5, 40, 40);
        createInstance.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));

            JTextField nameField = new JTextField();
            String[] versionList = new String[Versions.values().length];
            for (int i = 0; i < Versions.values().length; i++) {
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

                JPanel newPanel = new JPanel();
                newPanel.setBackground(Color.RED);
                newPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                newPanel.setLayout(new BorderLayout());

                ImageIcon originalIcon = instance.getIcon();
                ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                JLabel icon = new JLabel(scaledIcon);
                JLabel name = new JLabel(instanceName);
                JButton play = new JButton("Play");
                play.addActionListener(e1 -> {
                    instance.start();
                });

                newPanel.add(icon, BorderLayout.WEST);
                newPanel.add(name, BorderLayout.CENTER);
                newPanel.add(play, BorderLayout.EAST);

                content.add(newPanel);

                content.revalidate();
                content.repaint();
            }
        });
        topBar.add(createInstance);

        for(Instance instance : Main.instances.values()) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.RED);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            panel.setLayout(new BorderLayout());

            ImageIcon originalIcon = (ImageIcon) instance.getIcon();
            ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
            JLabel icon = new JLabel(scaledIcon);
            JLabel name = new JLabel(instance.getName());
            JButton play = new JButton("Play");
            play.addActionListener(e -> {
                instance.start();
            });

            panel.add(icon, BorderLayout.WEST);
            panel.add(name, BorderLayout.CENTER);
            panel.add(play, BorderLayout.EAST);

            content.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(sidebar, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Main.name);
        pack();
        setSize(750, 600);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
