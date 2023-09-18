package perry.betterthanlauncher.util.instances;

import perry.betterthanlauncher.Main;
import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.files.ResTool;
import perry.betterthanlauncher.util.jars.JarMerger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Instance {
    private final String name;
    private final String version;
    private final ImageIcon icon;
    private final String path;
    private final Config config;

    public Instance(String name) {
        this.name = name;
        this.path = Main.path + "/instances/" + name;
        config = new Config(path + "/instance.conf");
        version = (String) config.getValue("version");

        File iconFile = new File(path + "/icon.png");
        if(!iconFile.exists()) {
            ResTool.copy("icon.png", path);
        }
        try {
            icon = new ImageIcon(iconFile.toURL());
        } catch(MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String javaExecutable = "java";
            String memoryOptions = "-Xms256m -Xmx256m";
            String classpath = "instances/" + name + "/minecraft.jar;libraries/jinput.jar;libraries/lwjgl.jar;libraries/lwjgl_util.jar";
            String libraryPath = "-Djava.library.path=libraries/natives";
            String mainClass = "net.minecraft.client.Minecraft";
            String username = "Perry6226";
            String gameArguments = "--gameDir /instances/" + name + " --assetDir /instances/" + name;
            String command = (javaExecutable + " " + memoryOptions + " -cp \"" + classpath + "\" " + libraryPath + " " + mainClass + " " + username + " " + gameArguments)
                    .replace("/", "\\");

            System.out.println("Instance " + name + " started with following startup command:");
            System.out.println(command);

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Minecraft exited with Exit Code: " + exitCode);
            } catch(IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        minecraftThread.start();
    }

    public static boolean createInstance(String name, String version) {
        String path = Main.path + "/instances/" + name;
        File instanceFolder = new File(path);

        if(!instanceFolder.exists()) {
            instanceFolder.mkdirs();

            JarMerger jarMerger = new JarMerger();
            jarMerger.merge(Main.path + "/versions/" + version + "/" + version + ".jar", Main.path + "/versions/original/client.jar", path + "/minecraft.jar");

            Config config = new Config(path, "instance", "conf");
            config.writeConfig("name", name);
            config.writeConfig("version", version);

            return true;
        } else {
            return false;
        }
    }

    public static List<Instance> getInstances() {
        String path = Main.path + "/instances";
        List<Instance> instances = new ArrayList<>();

        File parentDirectory = new File(path);

        File[] files = parentDirectory.listFiles();

        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    instances.add(new Instance(file.getName()));
                }
            }
        }

        return instances;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getPath() {
        return path;
    }

    public Config getConfig() {
        return config;
    }
}

