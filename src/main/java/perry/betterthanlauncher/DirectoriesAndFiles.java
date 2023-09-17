package perry.betterthanlauncher;

import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.files.FileDownloader;
import perry.betterthanlauncher.util.files.ResTool;
import perry.betterthanlauncher.util.files.ZipExtractor;

import java.io.File;

public class DirectoriesAndFiles {

    public static void create() {
        Main.path = System.getProperty("user.dir");


        creator("instances");
        creator("libraries");
        creator("versions");

        //Folder for different versions
        creator("versions/original");
        creator("versions/bta_1.7.7.0_02");

        creator("libraries/natives/META-INF");

        Main.config = new Config(Main.path, "config", "conf");

        ResTool.copy("lwjgl.jar", Main.path + "/libraries");
        ResTool.copy("lwjgl_util.jar", Main.path + "/libraries");
        ResTool.copy("jinput.jar", Main.path + "/libraries");
        ResTool.copy("natives.zip", Main.path + "/libraries");
        ResTool.copy("META-INF.zip", Main.path + "/libraries");
        ZipExtractor.extract(Main.path + "/libraries/natives.zip", Main.path + "/libraries/natives");
        ZipExtractor.extract(Main.path + "/libraries/META-INF.zip", Main.path + "/libraries/natives/META-INF");

        //ResTool.copy("b1.7.3.jar", path + "/versions/original");
        FileDownloader.download("https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar", Main.path + "/versions/original/");
        ResTool.copy("bta_1.7.7.0_02.jar", Main.path + "/versions/bta_1.7.7.0_02");
    }

    private static void creator(String name) {
        File folder = new File(Main.path + "/" + name + "/");
        if(!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create " + name + " folder.");
        }
    }
}
