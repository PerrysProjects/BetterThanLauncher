package perry.betterthanlauncher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        this.setMinimumSize(new Dimension(750, 400));

        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.BLUE);
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        JPanel topBar = new JPanel();
        topBar.setBackground(Color.GREEN);
        topBar.setPreferredSize(new Dimension(getWidth(), 50));

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        for(int i = 0; i < Main.instances.size(); i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.RED);
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            panel.setLayout(new BorderLayout());

            ImageIcon originalIcon = (ImageIcon) Main.instances.get(i).getIcon();
            ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
            JLabel icon = new JLabel(scaledIcon);
            JLabel name = new JLabel(Main.instances.get(i).getName());
            icon.setBorder(new EmptyBorder(20, 20, 20, 20));

            panel.add(icon, BorderLayout.WEST);
            panel.add(name, BorderLayout.CENTER);

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
