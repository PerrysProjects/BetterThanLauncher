package net.perry.betterthanlauncher.instances;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.files.ResTool;
import net.perry.betterthanlauncher.util.jars.JarMerger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Instance {
    private String name;
    private Versions version;
    private ImageIcon icon;
    private String path;
    private Config config;

    public Instance(String name) {
        File instanceFolder = new File(Main.path + "/instances/" + name);

        this.name = name;
        path = instanceFolder.getPath();
        config = new Config(instanceFolder.getPath() + "/instance.conf");

        version = Versions.fileNameToVersion((String) config.getValue("version"));
        if(!new File(path + "/icon.png").exists()) {
            ResTool.copy("icons/icon.png", path);
        }
        icon = new ImageIcon(path + "/icon.png");
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String javaExecutable = "java";
            String memoryOptions = "-Xms256m -Xmx256m";
            String classpath = path + "/minecraft.jar;" + Main.path + "/libraries/jinput.jar;" + Main.path + "/libraries/lwjgl.jar;" + Main.path + "/libraries/lwjgl_util.jar";
            String libraryPath = "-Djava.library.path=" + Main.path + "/libraries/natives";
            String mainClass = "net.minecraft.client.Minecraft";
            String username = "Perry6226";
            String gameArguments = "--gameDir " + path + " --assetDir " + path;
            String command = (javaExecutable + " " + memoryOptions + " -cp \"" + classpath + "\" " + libraryPath + " " + mainClass + " " + username + " " + gameArguments).replace("/", "\\");

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

    public static void createInstance(String name, Versions version) {
        String path = Main.path + "/instances/";
        File instanceFolder = new File(path + name);

        if(!instanceFolder.exists() || instanceFolder.listFiles() == null) {
            instanceFolder.mkdirs();
        }

        JarMerger jarMerger = new JarMerger();
        if(!new File(instanceFolder.getPath() + "/minecraft.jar").exists()) {
            jarMerger.merge(Main.path + "/versions/" + version.toString().toLowerCase() + "/" + version.getFileName() + ".jar",
                    Main.path + "/versions/" + Versions.B_1_7_3.toString().toLowerCase() + "/" + Versions.B_1_7_3.getFileName() + ".jar",
                    instanceFolder.getPath() + "/minecraft.jar");
        }

        Config config = new Config(instanceFolder.getPath(), "instance", "conf");
        config.writeConfig("version", version.getFileName());

        ResTool.copy("icons/icon.png", instanceFolder.getPath());

        Main.instances = getInstances();
    }

    public static Map<String, Instance> getInstances() {
        String path = Main.path + "/instances";
        Map<String, Instance> instances = new HashMap<>();

        File parentDirectory = new File(path);

        File[] files = parentDirectory.listFiles();

        if(files != null) {
            for(File file : files) {
                if(file.isDirectory() && new File(file.getPath() + "/instance.conf").exists()) {
                    instances.put(file.getName(), new Instance(file.getName()));
                }
            }
        }

        return instances;
    }

    public String getName() {
        return name;
    }

    public Versions getVersion() {
        return version;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getPath() {
        return path;
    }

    public Config getConfig() {
        return config;
    }
}

