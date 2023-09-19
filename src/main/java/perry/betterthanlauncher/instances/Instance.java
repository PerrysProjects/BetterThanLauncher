package perry.betterthanlauncher.instances;

import perry.betterthanlauncher.Main;
import perry.betterthanlauncher.util.files.Config;
import perry.betterthanlauncher.util.files.ResTool;
import perry.betterthanlauncher.util.jars.JarMerger;

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
        this.name = null;
        version = null;
        icon = null;
        path = null;
        config = null;

        String instancesPath = Main.path + "/instances";
        File parentDirectory = new File(instancesPath);
        File[] files = parentDirectory.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    if(new File(file.getPath() + "/instance.conf").exists()) {
                        Config config = new Config(file.getPath() + "/instance.conf");
                        if(String.valueOf(config.getValue("name")).equals(name)) {
                            this.name = name;
                            path = file.getPath();
                            config = new Config(path + "/instance.conf");
                            version = Versions.fileNameToVersion((String) config.getValue("version"));
                            icon = new ImageIcon(path + "/icon.png");
                        }
                    }
                }
            }
        }
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

    public static boolean createInstance(String name, Versions version) {
        String path = Main.path + "/instances/" + name;
        File instanceFolder = new File(path);

        if(!instanceFolder.exists() || instanceFolder.listFiles() != null && instanceFolder.listFiles().length == 0) {
            instanceFolder.mkdirs();

            JarMerger jarMerger = new JarMerger();
            jarMerger.merge(Main.path + "/versions/" + version.toString().toLowerCase() + "/" + version.getFileName() + ".jar", Main.path + "/versions/" + Versions.B_1_7_3.toString().toLowerCase() + "/" + Versions.B_1_7_3.getFileName() + ".jar", path + "/minecraft.jar");

            Config config = new Config(path, "instance", "conf");
            config.writeConfig("name", name);
            config.writeConfig("version", version.getFileName());

            ResTool.copy("icon.png", path);

            Main.instances = getInstances();
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, Instance> getInstances() {
        String path = Main.path + "/instances";
        Map<String, Instance> instances = new HashMap<>();

        File parentDirectory = new File(path);

        File[] files = parentDirectory.listFiles();

        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    if(new File(file.getPath() + "/instance.conf").exists()) {
                        Config config = new Config(file.getPath() + "/instance.conf");
                        System.out.println(file.getName());
                        instances.put(file.getName(), new Instance(String.valueOf(config.getValue("name"))));
                    }
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

