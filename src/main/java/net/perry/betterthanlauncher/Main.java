package net.perry.betterthanlauncher;

import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.files.Config;

import javax.swing.*;
import java.net.InetAddress;
import java.util.Map;

public class Main extends JFrame {
    public static String name;
    public static String version;

    public static String path;
    public static Config config;

    public static Map<String, Instance> instances;

    public static void main(String[] args) {
        name = "BetterThanLauncher";
        version = "1.0";

        if(!isConnectedToInternet()) {
            int option = JOptionPane.showConfirmDialog(null, "You are not connected to the internet. Proceeding may cause errors during file downloads.", "Internet Connection Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if(option != JOptionPane.YES_OPTION) {
                System.exit(50);
            }
        }

        FileManagement.load();

        instances = Instance.getInstances();

        Instance.createInstance("Vanilla Beta 1.7.3", Versions.B_1_7_3);
        Instance.createInstance("Latest BTA version", Versions.values()[0]);

        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
        });
    }

    private static boolean isConnectedToInternet() {
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(1000);
        } catch (Exception e) {
            return false;
        }
    }
}
