package perry.betterthanlauncher.util.instances;

import perry.betterthanlauncher.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InstancesTool {
    public static final String path = Main.path + "/instances";

    public static List<String> getNames() {
        List<String> folderNames = new ArrayList<>();

        File parentDirectory = new File(path);

        if(!parentDirectory.isDirectory()) {
            System.err.println("The provided path is not a directory.");
            return folderNames;
        }

        File[] files = parentDirectory.listFiles();

        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    folderNames.add(file.getName());
                }
            }
        }

        return folderNames;
    }
}
