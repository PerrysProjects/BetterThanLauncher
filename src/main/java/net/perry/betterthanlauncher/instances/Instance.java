package net.perry.betterthanlauncher.instances;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.OsManager;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.files.LibrariesManager;
import net.perry.betterthanlauncher.util.tool.JarTool;
import net.perry.betterthanlauncher.util.tool.NativesTool;
import net.perry.betterthanlauncher.util.tool.ResTool;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Instance {
    private final String path;

    private final String name;
    private final String instancePath;
    private final Config config;
    private final Versions.VersionInfo versionInfo;
    private ImageIcon icon;

    private int memory;
    private boolean babric;
    private String javaPath;

    public static Map<String, Instance> INSTANCES;

    public Instance(String name) {
        path = Main.PATH;

        this.name = name.replaceAll(" ", "_");

        instancePath = path + "/instances/" + name;

        config = new Config(instancePath + "/instance.conf");

        versionInfo = Versions.getByFileName(String.valueOf(config.getValue("version")));

        if(!new File(instancePath + "/icon.png").exists()) {
            ResTool.copy("icons/icon.png", instancePath);
        }
        icon = new ImageIcon(instancePath + "/icon.png");

        memory = (int) config.getValue("memory");
        babric = Boolean.parseBoolean((String) config.getValue("babric"));
        javaPath = String.valueOf(config.getValue("java_path"));
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String path = this.path;
            String instancePath = this.instancePath;

            String separator = OsManager.isWindows() ? ";" : ":";

            try {
                if(versionInfo.getFileName().startsWith("bta_7.2") || versionInfo.getFileName().startsWith("bta_7.1") ||
                        versionInfo.getFileName().startsWith("bta_1") || versionInfo.getFileName().startsWith("b_1.7.3")) {

                    if(OsManager.isWindows()) {
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("jinput-platform-2.0.5-natives-windows.jar")), new File(instancePath + "/natives/"));
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("lwjgl-platform-2.9.4-babric.1-natives-windows.jar")), new File(instancePath + "/natives/"));
                    } else if(OsManager.isMac()) {
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("jinput-platform-2.0.5-natives-osx.jar")), new File(instancePath + "/natives/"));
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("lwjgl-platform-2.9.4-babric.1-natives-osx.jar")), new File(instancePath + "/natives/"));
                    } else if(OsManager.isLinux()) {
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("jinput-platform-2.0.5-natives-linux.jar")), new File(instancePath + "/natives/"));
                        NativesTool.extractNatives(new File(path + LibrariesManager.getLib("lwjgl-platform-2.9.4-babric.1-natives-linux.jar")), new File(instancePath + "/natives/"));
                    }
                }
            } catch(IOException e) {
                Logger.error(e);
            }

            String javaExecutable = javaPath.equalsIgnoreCase("%JAVA_HOME%") ? "java" : javaPath;
            String memoryOptions = "-Xms" + memory + "m -Xmx" + memory + "m";

            List<String> flags = new ArrayList<>();
            flags.add("-Djava.awt.headless=false");

            StringBuilder classpath = new StringBuilder();

            if(versionInfo.getFileName().startsWith("bta_7.2") || versionInfo.getFileName().startsWith("bta_7.1") ||
                    versionInfo.getFileName().startsWith("bta_1") || versionInfo.getFileName().startsWith("b_1.7.3")) {
                flags.add("-Djava.library.path=" + instancePath + "/natives/");

                classpath = new StringBuilder(path + LibrariesManager.getLib("jinput-2.0.5.jar") + separator +
                        path + LibrariesManager.getLib("jutils-1.0.0.jar") + separator +
                        path + LibrariesManager.getLib("lwjgl-2.9.4-babric.1.jar") + separator +
                        path + LibrariesManager.getLib("lwjgl_util-2.9.4-babric.1.jar"));
            } else {
                String os = OsManager.isWindows() ? "windows" : (OsManager.isMac() ? "macos" : "linux");

                String[] nativeSuffixes;

                if(OsManager.isLinux()) {
                    nativeSuffixes = new String[] {
                            "-natives-" + os + ".jar"
                    };
                } else if(OsManager.isMac()) {
                    nativeSuffixes = new String[] {
                            "-natives-" + os + ".jar",
                            "-natives-" + os + "-arm64.jar"
                    };
                } else {
                    nativeSuffixes = new String[] {
                            "-natives-" + os + ".jar",
                            "-natives-" + os + "-x86.jar",
                            "-natives-" + os + "-arm64.jar"
                    };
                }

                String[] lwjglModules = {
                        "lwjgl", "lwjgl-freetype", "lwjgl-glfw", "lwjgl-jemalloc",
                        "lwjgl-openal", "lwjgl-opengl", "lwjgl-stb"
                };

                for(String module : lwjglModules) {
                    classpath.append(path).append(LibrariesManager.getLib(module + "-3.3.3.jar")).append(separator);
                    for(String suffix : nativeSuffixes) {
                        classpath.append(path).append(LibrariesManager.getLib(module + "-3.3.3" + suffix)).append(separator);
                    }
                }

                classpath.append(path).append(LibrariesManager.getLib("log4j-api-2.19.0.jar"))
                        .append(separator).append(path).append(LibrariesManager.getLib("log4j-core-2.19.0.jar"))
                        .append(separator).append(path).append(LibrariesManager.getLib("log4j-slf4j2-impl-2.19.0.jar")).append(separator)
                        .append(path).append(LibrariesManager.getLib("slf4j-api-2.0.7.jar"));
            }

            classpath.append(separator).append(instancePath).append("/minecraft.jar");

            String mainClass = "net.minecraft.client.Minecraft";
            String username = Main.AUTH.getLoadedProfile().getMcProfile().getName();
            String sessionID = Main.AUTH.getLoadedProfile().getMcProfile().getMcToken().getAccessToken();

            List<String> cmdList = new ArrayList<>();
            cmdList.add(javaExecutable);

            cmdList.addAll(Arrays.asList(memoryOptions.split(" ")));

            cmdList.addAll(flags.stream().toList());

            cmdList.add("-cp");
            cmdList.add(classpath.toString());

            cmdList.add(mainClass);

            cmdList.add("--username");
            cmdList.add(username);

            cmdList.add("--session");
            cmdList.add(sessionID);

            cmdList.add("--gameDir");
            cmdList.add(instancePath);

            cmdList.add("--assetsDir");
            cmdList.add(instancePath);

            cmdList.add("--title");
            cmdList.add(versionInfo.getFileName());

            String[] cmdArray = cmdList.toArray(new String[0]);

            String command = String.join(" ", Arrays.stream(cmdArray)
                    .map(arg -> arg.contains(" ") ? "\"" + arg + "\"" : arg)
                    .toArray(String[]::new));

            Logger.log("Instance " + name + " started:");
            Logger.log(command.replaceAll(sessionID, "SESSION_ID"));

            try {
                Process process = Runtime.getRuntime().exec(cmdArray);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                new Thread(() -> {
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        Logger.error(e);
                    }
                }).start();

                new Thread(() -> {
                    String line;
                    try {
                        while ((line = errorReader.readLine()) != null) {
                            System.err.println(line);
                        }
                    } catch (IOException e) {
                        Logger.error(e);
                    }
                }).start();

                int exitCode = process.waitFor();
                Logger.log("Minecraft exited with Exit Code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                Logger.error(e);
            }
        });

        minecraftThread.start();
    }

    public static void createInstance(String name, Versions.VersionInfo versionInfo, boolean babric) {
        String path = Main.PATH;
        File instanceFolder = new File(path + "/instances/" + name.replaceAll(" ", "_"));

        if(!instanceFolder.exists() || instanceFolder.listFiles() == null) {
            instanceFolder.mkdirs();
        }

        JarTool jarMerger = new JarTool();
        if(!new File(instanceFolder.getPath() + "/minecraft.jar").exists()) {
            jarMerger.merge(path + "/versions/" + versionInfo.getFileName() + "/client.jar",
                    path + "/versions/b_1.7.3/client.jar",
                    instanceFolder.getPath() + "/minecraft.jar");
        }

        Config config = new Config(instanceFolder.getPath(), "instance", "conf");
        config.writeConfig("version", versionInfo.getFileName());
        config.writeConfig("memory", "1024");
        config.writeConfig("babric", String.valueOf(babric));
        config.writeConfig("java_path", "%JAVA_HOME%");

        ResTool.copy("icons/icon.png", instanceFolder.getPath());

        updateInstances();
    }

    public static void updateInstances() {
        String path = Main.PATH + "/instances";
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

        INSTANCES = instances;
    }

    public static Map<String, Instance> getInstances() {
        return INSTANCES;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return name.replaceAll("_", " ");
    }

    public Versions.VersionInfo getVersionInfo() {
        return versionInfo;
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

    public String getJavaPath() {
        return javaPath;
    }

    public void setJavaPath(String javaPath) {
        this.javaPath = javaPath;
        config.setValue("java_path", String.valueOf(javaPath));
    }
}

