package net.perry.betterthanlauncher.components.panels;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.Theme;
import net.perry.betterthanlauncher.components.customs.RoundButton;
import net.perry.betterthanlauncher.components.customs.RoundCheckBox;
import net.perry.betterthanlauncher.components.customs.RoundPanel;
import net.perry.betterthanlauncher.components.customs.RoundStatics;
import net.perry.betterthanlauncher.components.customs.uis.CustomComboBoxUI;
import net.perry.betterthanlauncher.instances.Versions;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddPanel extends RoundPanel {
    private final MainPanel panel;
    private final Theme theme;

    public AddPanel(MainPanel panel, Color background, Color border, boolean leftSided, boolean image) {
        super(background, border, leftSided, image);

        this.panel = panel;
        theme = Main.THEME;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

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
        createInstancePanel.setMaximumSize(RoundStatics.SMALL_PANEL_SIZE);
        createInstancePanel.setMinimumSize(RoundStatics.SMALL_PANEL_SIZE);
        createInstancePanel.setPreferredSize(RoundStatics.SMALL_PANEL_SIZE);
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

            if(!"Enter name".equals(name) && versionInfo != null && panel.addInstance(name, versionInfo, babricCheckBox.isSelected())) {
                nameField.setText("Enter name");
                versionComboBox.setSelectedIndex(0);
            } else {
                //nameField.setForeground(Color.decode("#ed4337"));
            }
        });

        RoundButton importButton = new RoundButton("Import", theme.getComponents4(), theme.getComponents5(), theme.getText2());
        importButton.setBounds(293, 160, 60, 40);
        importButton.addActionListener(e -> {
            panel.importInstance();
        });

        createInstancePanel.add(nameField);
        createInstancePanel.add(versionComboBox);
        createInstancePanel.add(babricCheckBox);
        createInstancePanel.add(createInstanceButton);
        createInstancePanel.add(importButton);

        add(Box.createHorizontalGlue());
        add(createInstancePanel);
        add(Box.createHorizontalGlue());
    }
}
