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
        /*MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        try {
            if(new File("pom.xml").exists()) {
                model = reader.read(new FileReader("pom.xml"));
            } else {
                model = reader.read(new InputStreamReader(Objects.requireNonNull(Main.class.getResourceAsStream("/META-INF/maven/org.apache.maven/maven-model/pom.xml"))));
            }
        } catch(IOException | XmlPullParserException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
        name = model.getName();
        version = model.getVersion();*/

        name = "BetterThanLauncher";

        if(!isConnectedToInternet()) {
            int option = JOptionPane.showConfirmDialog(null, "You are not connected to the internet. Proceeding may cause errors during file downloads.", "Internet Connection Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if(option != JOptionPane.YES_OPTION) {
                System.exit(50);
            }
        }

        FileManagement.load();

        Instance.createInstance("Vanilla Beta 1.7.3", Versions.B_1_7_3);
        Instance.createInstance("Latest BTA version", Versions.values()[0]);

        instances = Instance.getInstances();

        Frame frame = new Frame();
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
