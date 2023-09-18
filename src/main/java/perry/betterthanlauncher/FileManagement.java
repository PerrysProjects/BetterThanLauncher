package perry.betterthanlauncher;

import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.files.ResTool;
import perry.betterthanlauncher.util.files.ZipExtractor;

import java.io.File;

public class FileManagement {

    public static void create() {
        Main.path = System.getProperty("user.dir");

        createFolder("instances");
        createFolder("libraries");
        createFolder("versions");

        //Folder for different versions
        createFolder("versions/original");
        createFolder("versions/bta_1.7.7.0_02");

        createFolder("libraries/natives/META-INF");

        Main.config = new Config(Main.path, "config", "conf");

        ResTool.copy("libraries.zip", Main.path + "/libraries");
        ResTool.copy("natives.zip", Main.path + "/libraries");
        ResTool.copy("META-INF.zip", Main.path + "/libraries");
        ZipExtractor.extract(Main.path + "/libraries/libraries.zip", Main.path + "/libraries");
        ZipExtractor.extract(Main.path + "/libraries/natives.zip", Main.path + "/libraries/natives");
        ZipExtractor.extract(Main.path + "/libraries/META-INF.zip", Main.path + "/libraries/natives/META-INF");

        //FileDownloader.download("https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar", Main.path + "/versions/original/");
        ResTool.copy("client.jar", Main.path + "/versions/original");
        ResTool.copy("bta_1.7.7.0_02.jar", Main.path + "/versions/bta_1.7.7.0_02");
    }

    private static void createFolder(String name) {
        File folder = new File(Main.path + "/" + name); // + "/"
        if(!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create " + name + " folder.");
        }
    }
}
