package net.perry.betterthanlauncher.instances;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.tool.JarTool;
import net.perry.betterthanlauncher.util.tool.ResTool;

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
    private boolean babric;

    public Instance(String name) {
        path = Main.path;

        this.name = name.replaceAll(" ", "_");

        instancePath = path + "/instances/" + name;

        config = new Config(instancePath + "/instance.conf");

        version = Versions.fileNameToVersion((String) config.getValue("version"));
        if(!new File(instancePath + "/icon.png").exists()) {
            ResTool.copy("icons/icon.png", instancePath);
        }
        icon = new ImageIcon(instancePath + "/icon.png");

        memory = (int) config.getValue("memory");

        babric = Boolean.parseBoolean((String) config.getValue("babric"));
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String path = this.path;
            String instancePath = this.instancePath;

            if(Main.os.contains("nix") || Main.os.contains("nux") || Main.os.contains("unix") || Main.os.contains("mac")) {
                path = path.replace(" ", "\\ ");
                instancePath = instancePath.replace(" ", "\\ ");
            }

            String javaExecutable = "java";
            String memoryOptions = "-Xms" + memory + "m -Xmx" + memory + "m";
            String classpath = instancePath + "/minecraft.jar;" +
                    path + "/libraries/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar;" +
                    path + "/libraries/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar;" +
                    path + "/libraries/babric/org/lwjgl/lwjgl/lwjgl/2.9.4-babric.1/lwjgl-2.9.4-babric.1.jar;" +
                    path + "/libraries/babric/org/lwjgl/lwjgl/lwjgl_util/2.9.4-babric.1/lwjgl_util-2.9.4-babric.1.jar;";
            if(Main.os.contains("win")) {
                classpath += path + "/libraries/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-windows.jar;" +
                        path + "/libraries/maven2/org/lwjgl/lwjgl-platform/3.0.0/lwjgl-platform-3.0.0-natives-windows.jar;";
            } else if(Main.os.contains("nix") || Main.os.contains("nux") || Main.os.contains("unix")) {
                classpath += path + "/libraries/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-linux.jar;" +
                        path + "/libraries/maven2/org/lwjgl/lwjgl-platform/3.0.0/lwjgl-platform-3.0.0-natives-linux.jar;";
            } else if(Main.os.contains("mac")) {
                classpath += path + "/libraries/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-osx.jar;" +
                        path + "/libraries/maven2/org/lwjgl/lwjgl-platform/3.0.0/lwjgl-platform-3.0.0-natives-osx.jar;";
            }
            if(babric) {
                classpath += path + "/libraries/babric/babric/fabric-loader/0.14.24-babric.1/fabric-loader-0.14.24-babric.1.jar;" +
                        path + "/libraries/babric/babric/log4j-config/1.0.0/log4j-config-1.0.0.jar;" +
                        path + "/libraries/maven2/net/minecrell/terminalconsoleappender/1.2.0/terminalconsoleappender-1.2.0.jar;" +
                        path + "/libraries/maven2/org/slf4j/slf4j-api/1.8.0-beta4/slf4j-api-1.8.0-beta4.jar;" +
                        path + "/libraries/maven2/org/apache/logging/log4j/log4j-slf4j18-impl/2.16.0/log4j-slf4j18-impl-2.16.0.jar;" +
                        path + "/libraries/maven2/org/apache/logging/log4j/log4j-api/2.16.0/log4j-api-2.16.0.jar;" +
                        path + "/libraries/maven2/org/apache/logging/log4j/log4j-core/2.16.0/log4j-core-2.16.0.jar;" +
                        path + "/libraries/maven2/com/google/code/gson/gson/2.8.9/gson-2.8.9.jar;" +
                        path + "/libraries/maven2/com/google/guava/guava/31.0.1-jre/guava-31.0.1-jre.jar;" +
                        path + "/libraries/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar;" +
                        path + "/libraries/maven2/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar;" +
                        path + "/libraries/maven2/commons-codec/commons-codec/1.15/commons-codec-1.15.jar;" +
                        path + "/libraries/net/fabricmc/tiny-mappings-parser/0.3.0%2Bbuild.17/tiny-mappings-parser-0.3.0%2Bbuild.17.jar;"+
                        path + "/libraries/net/fabricmc/sponge-mixin/0.11.4%2Bmixin.0.8.5/sponge-mixin-0.11.4%2Bmixin.0.8.5.jar;" +
                        path + "/libraries/net/fabricmc/tiny-remapper/0.8.2/tiny-remapper-0.8.2.jar;" +
                        path + "/libraries/net/fabricmc/access-widener/2.1.0/access-widener-2.1.0.jar;" +
                        path + "/libraries/org/ow2/asm/asm/9.3/asm-9.3.jar;" +
                        path + "/libraries/org/ow2/asm/asm-analysis/9.3/asm-analysis-9.3.jar;" +
                        path + "/libraries/org/ow2/asm/asm-commons/9.3/asm-commons-9.3.jar;" +
                        path + "/libraries/org/ow2/asm/asm-tree/9.3/asm-tree-9.3.jar;" +
                        path + "/libraries/org/ow2/asm/asm-util/9.3/asm-util-9.3.jar;";
                if(Main.os.contains("nix") || Main.os.contains("nux") || Main.os.contains("unix") || Main.os.contains("mac")) {
                    classpath = classpath.replaceAll(";", ":");
                }
            }
            String flags = "-Djava.library.path=" + path + "/libraries/natives";
            if(babric) {
                flags += " -Dfabric.gameVersion=1.7.7.0";
            }
            String mainClass = "net.minecraft.client.Minecraft";
            if(babric) {
                mainClass = "net.fabricmc.loader.impl.launch.knot.KnotClient";
            }
            String username = Main.auth.getLoadedProfile().name();
            String sessionID = Main.auth.getLoadedProfile().prevResult().prevResult().access_token();
            String gameArguments = "--gameDir " + instancePath + " --assetsDir " + instancePath + " --version " + version.getFileName();
            String command = javaExecutable + " " + memoryOptions + " -cp " + classpath + " " + flags + " " + mainClass + " " + username + " " + sessionID + " " + gameArguments;

            System.out.println("Instance " + name + " started:");
            System.out.println(command.replace(sessionID, "SESSION_ID"));

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

    public static void createInstance(String name, Versions version, boolean babric) {
        String path = Main.path;
        File instanceFolder = new File(path + "/instances/" + name.replaceAll(" ", "_"));

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
        config.writeConfig("babric", String.valueOf(babric));

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

    public String getDisplayName() {
        return name.replaceAll("_", " ");
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

