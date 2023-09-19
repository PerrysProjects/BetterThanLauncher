package perry.betterthanlauncher;

import perry.betterthanlauncher.instances.Versions;
import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.files.ResTool;
import perry.betterthanlauncher.util.files.ZipExtractor;

import java.io.File;

public class FileManagement {

    public static void load() {
        Main.path = System.getProperty("user.dir");

        Main.config = new Config(Main.path, "config", "conf");

        // Instances
        createFolder("instances");

        // Libraries
        createFolder("libraries");
        createFolder("libraries/natives/META-INF");
        ResTool.copy("natives.zip", Main.path + "/libraries");
        ResTool.copy("libraries.zip", Main.path + "/libraries");
        ResTool.copy("META-INF.zip", Main.path + "/libraries");
        ZipExtractor.extract(Main.path + "/libraries/natives.zip", Main.path + "/libraries/natives");
        ZipExtractor.extract(Main.path + "/libraries/libraries.zip", Main.path + "/libraries");
        ZipExtractor.extract(Main.path + "/libraries/META-INF.zip", Main.path + "/libraries/natives/META-INF");

        // Versions
        createFolder("versions");

        // Versions
        /*for(Versions version: Versions.values()) {
            createFolder("versions/" + version.toString().toLowerCase());
            ResTool.copy(version.getFileName() + ".jar", Main.path + "/versions/" + version.toString().toLowerCase());
        }*/
        createFolder("versions/" + Versions.B_1_7_3.toString().toLowerCase());
        ResTool.copy(Versions.B_1_7_3.getFileName() + ".jar", Main.path + "/versions/" + Versions.B_1_7_3.toString().toLowerCase());
        createFolder("versions/" + Versions.BTA_1_7_7_0_02.toString().toLowerCase());
        ResTool.copy(Versions.BTA_1_7_7_0_02.getFileName() + ".jar", Main.path + "/versions/" + Versions.BTA_1_7_7_0_02.toString().toLowerCase());
        //FileDownloader.download("https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar", Main.path + "/versions/original/");
    }

    private static void createFolder(String name) {
        File folder = new File(Main.path + "/" + name); // + "/"
        if(!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create " + name + " folder.");
        }
    }
}
