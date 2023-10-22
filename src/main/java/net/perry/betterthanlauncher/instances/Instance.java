package net.perry.betterthanlauncher.instances;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.ResTool;
import net.perry.betterthanlauncher.util.tool.JarTool;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Instance {
    private String path;

    private String name;
    private String instancePath;
    private Config config;
    private Versions version;
    private ImageIcon icon;
    private int memory;

    public Instance(String name) {
        path = Main.path;

        this.name = name;

        File instanceFolder = new File(path + "/instances/" + name);
        instancePath = instanceFolder.getPath();

        config = new Config(instanceFolder.getPath() + "/instance.conf");

        version = Versions.fileNameToVersion((String) config.getValue("version"));
        if(!new File(instancePath + "/icon.png").exists()) {
            ResTool.copy("icons/icon.png", instancePath);
        }
        icon = new ImageIcon(instancePath + "/icon.png");

        memory = (int) config.getValue("memory");
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String javaExecutable = "java";
            String memoryOptions = "-Xms" + memory + "m -Xmx" + memory + "m";
            String classpath = instancePath + "/minecraft.jar;" + path + "/libraries/jinput.jar;" + path + "/libraries/lwjgl.jar;" + path + "/libraries/lwjgl_util.jar";
            String libraryPath = "-Djava.library.path=" + path + "/libraries/natives";
            String mainClass = "net.minecraft.client.Minecraft";
            String username = Main.auth.getLoadedProfile().name();
            String sessionID = Main.auth.getLoadedProfile().prevResult().prevResult().access_token();
            String gameArguments = "--gameDir " + instancePath + " --assetDir " + instancePath;
            String command = (javaExecutable + " " + memoryOptions + " -cp \"" + classpath + "\" " + libraryPath + " " + mainClass + " " + username + " " + sessionID + " " + gameArguments).replace("/", "\\");

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
                Logger.error(e);
            }
        });

        minecraftThread.start();
    }

    public static void createInstance(String name, Versions version) {
        String path = Main.path;
        File instanceFolder = new File(path + "/instances/" + name);

        if(!instanceFolder.exists() || instanceFolder.listFiles() == null) {
            instanceFolder.mkdirs();
        }

        JarTool jarMerger = new JarTool();
        if(!new File(instanceFolder.getPath() + "/minecraft.jar").exists()) {
            jarMerger.merge(path + "/versions/" + version.toString().toLowerCase() + "/" + version.getFileName() + ".jar",
                    path + "/versions/" + Versions.B_1_7_3.toString().toLowerCase() + "/" + Versions.B_1_7_3.getFileName() + ".jar",
                    instanceFolder.getPath() + "/minecraft.jar");
        }

        Config config = new Config(instanceFolder.getPath(), "instance", "conf");
        config.writeConfig("version", version.getFileName());
        config.writeConfig("memory", "1024");

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

    public String getInstancePath() {
        return instancePath;
    }

    public Config getConfig() {
        return config;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
        config.setValue("memory", String.valueOf(memory));
    }
}

