package perry.betterthanlauncher;

import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.instances.Instance;

import javax.swing.*;
import java.util.List;

public class Main extends JFrame {
    public static String name;
    public static String version;

    public static String path;
    public static Config config;

    public static List<Instance> instances;

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

        FileManagement.create();

        Instance.createInstance("Test", "bta_1.7.7.0_02");

        instances = Instance.getInstances();

        Frame frame = new Frame();
    }
}
