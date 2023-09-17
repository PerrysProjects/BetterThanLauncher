package perry.betterthanlauncher;

import perry.betterthanlauncher.util.instances.Instance;
import perry.betterthanlauncher.util.instances.InstancesTool;

import javax.swing.*;
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
        content.setLayout(new BorderLayout());

        JList instances = new JList(InstancesTool.getNames().toArray());
        instances.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        instances.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                Instance instance = new Instance(instances.getSelectedValue().toString());
                instance.start();
            }
        });
        content.add(new JScrollPane(instances), BorderLayout.CENTER);


        add(sidebar, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Main.name);
        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
