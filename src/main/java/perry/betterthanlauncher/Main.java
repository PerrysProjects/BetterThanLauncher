package perry.betterthanlauncher;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import perry.betterthanlauncher.util.files.Config;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main extends JFrame {
    public static String name;
    public static String version;

    public static String path;
    public static Config config;

    public static void main(String[] args) {
        MavenXpp3Reader reader = new MavenXpp3Reader();
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
        name = Objects.requireNonNull(model).getArtifactId().replaceAll("-", " ");
        version = model.getVersion();

        DirectoriesAndFiles.create();

        Frame frame = new Frame();
    }
}
