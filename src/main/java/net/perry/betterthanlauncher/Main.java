package net.perry.betterthanlauncher;

import net.perry.betterthanlauncher.components.frames.Frame;
import net.perry.betterthanlauncher.components.panels.MainPanel;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.files.FileDownloader;
import net.perry.betterthanlauncher.util.tool.ResTool;
import net.perry.betterthanlauncher.util.tool.ZipTool;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

public class Main extends JFrame {
    public static String name;
    public static String version;

    public static String path;
    public static Config config;

    public static Auth auth;

    public static Map<String, Instance> instances;

    public static Frame frame;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        name = "BetterThanLauncher";
        version = "1.0";

        if(!isConnectedToInternet()) {
            JOptionPane.showMessageDialog(null, "Unable to establish an internet connection. Please check your network settings and try again.", "Internet Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(50);
        }

        loadFiles();

        instances = Instance.getInstances();

        Instance.createInstance("Vanilla Beta 1.7.3", Versions.B_1_7_3);
        Instance.createInstance("Latest BTA version", Versions.values()[0]);

        auth = new Auth();
        while(auth.getLoadedProfile() == null) {
            auth.codeLogIn();
        }

        SwingUtilities.invokeAndWait(() -> {
            frame = new Frame();
            frame.setContentPane(new MainPanel());
        });
    }

    public static void restart() {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        File currentJar = null;
        try {
            currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch(URISyntaxException e) {
            Logger.error(e);
        }

        if(!currentJar.getName().endsWith(".jar")) {
            return;
        }

        ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch(IOException e) {
            Logger.error(e);
        }
        System.exit(81);
    }

    private static boolean isConnectedToInternet() {
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(1000);
        } catch(Exception e) {
            return false;
        }
    }

    private static void loadFiles() {
        path = System.getProperty("user.dir");

        config = new Config(path, "config", "conf");
        config.writeComment("Do not change this!");
        config.writeConfig("name", name);
        config.writeConfig("version", version);
        config.writeComment("This can be changed");
        config.writeConfig("theme", "dark");

        createFolder("instances");

        createFolder("libraries");
        createFolder("libraries/natives/META-INF");
        ResTool.copy("libraries/natives.zip", path + "/libraries");
        ResTool.copy("libraries/libraries.zip", path + "/libraries");
        ResTool.copy("libraries/META-INF.zip", path + "/libraries");
        ZipTool.extract(path + "/libraries/natives.zip", path + "/libraries/natives");
        ZipTool.extract(path + "/libraries/libraries.zip", path + "/libraries");
        ZipTool.extract(path + "/libraries/META-INF.zip", path + "/libraries/natives/META-INF");

        createFolder("versions");

        for(Versions version : Versions.values()) {
            createFolder("versions/" + version.toString().toLowerCase());
            if(!new File(path + "/versions/" + version.toString().toLowerCase() + "/" + version.getFileName() + ".jar").exists()) {
                FileDownloader.download(version.getLink(), path + "/versions/" + version.toString().toLowerCase() + "/");

                File oldFile;
                if(version == Versions.B_1_7_3) {
                    oldFile = new File(path + "/versions/" + version.toString().toLowerCase() + "/client.jar");
                } else {
                    oldFile = new File(path + "/versions/" + version.toString().toLowerCase() + "/bta.jar");
                }
                File newFile = new File(path + "/versions/" + version.toString().toLowerCase() + "/" + version.getFileName() + ".jar");
                if(!oldFile.renameTo(newFile)) {
                    System.out.println("Failed to rename the file: " + oldFile.getName());
                }
            }
        }
    }

    private static void createFolder(String name) {
        File folder = new File(path + "/" + name);
        if(!folder.exists() && !folder.mkdirs()) {
            System.err.println("Failed to create " + name + " folder.");
        }
    }
}
