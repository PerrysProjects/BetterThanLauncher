package net.perry.betterthanlauncher;

import net.perry.betterthanlauncher.components.frames.Frame;
import net.perry.betterthanlauncher.components.panels.MainPanel;
import net.perry.betterthanlauncher.instances.Instance;
import net.perry.betterthanlauncher.instances.Versions;
import net.perry.betterthanlauncher.util.Auth;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.Config;
import net.perry.betterthanlauncher.util.files.FileDownloader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Main extends JFrame {
    public static String name;
    public static String version;

    public static String os;

    public static Image icon;
    public static String path;
    public static Config config;

    public static Auth auth;

    public static Map<String, Instance> instances;

    public static Frame frame;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        name = "BetterThanLauncher";
        version = "1.0.1";

        os = System.getProperty("os.name").toLowerCase();

        try {
            icon = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/btl_icon.png")));
        } catch(IOException e) {
            Logger.error(e);
        }

        if(!isConnectedToInternet()) {
            SystemTray tray = SystemTray.getSystemTray();

            TrayIcon trayIcon = new TrayIcon(icon, "Tray Demo");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("System tray icon demo");
            try {
                tray.add(trayIcon);
            } catch(AWTException e) {
                Logger.error(e);
            }

            trayIcon.displayMessage("No internet connection available!", "Unable to establish an internet connection. Please check your network settings and try again.", TrayIcon.MessageType.ERROR);
            System.exit(80);
        } else {
            loadFiles();

            instances = Instance.getInstances();

            Versions.VersionInfo latestBTA = Versions.getAll().stream()
                    .filter(v -> v.getFileName().startsWith("bta_"))
                    .findFirst()
                    .orElse(null);

            Instance.createInstance("Latest BTA version", latestBTA, false);
            Instance.createInstance("Latest Babric version", latestBTA, true);
            Instance.createInstance("Vanilla Beta 1.7.3", Versions.getByFileName("b_1.7.3"), false);

            auth = new Auth();
            while(auth.getLoadedProfile() == null) {
                auth.codeLogIn();
            }

            SwingUtilities.invokeAndWait(() -> {
                frame = new Frame();
                frame.setContentPane(new MainPanel());
            });

            Logger.log("Starting launcher on " + os + " OS!");
        }
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
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch(IOException e) {
            Logger.error(e);
            return false;
        }
    }

    private static void loadFiles() {
        path = System.getProperty("user.dir").replace("\\", "/");

        config = new Config(path, "config", "conf");
        config.writeComment("Do not change this!");
        config.writeConfig("name", name);
        config.writeConfig("version", version);
        config.writeComment("This can be changed");
        config.writeConfig("java_path", "%JAVA_HOME%");
        config.writeConfig("theme", "dark");

        // JWGL 2
        downloadLibraries("https://libraries.minecraft.net/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar");
        downloadLibraries("https://libraries.minecraft.net/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar");

        downloadLibraries("https://maven.glass-launcher.net/babric/org/lwjgl/lwjgl/lwjgl/2.9.4-babric.1/lwjgl-2.9.4-babric.1.jar");
        downloadLibraries("https://maven.glass-launcher.net/babric/org/lwjgl/lwjgl/lwjgl_util/2.9.4-babric.1/lwjgl_util-2.9.4-babric.1.jar");

        downloadLibraries("https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-osx.jar");
        downloadLibraries("https://maven.glass-launcher.net/babric/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-babric.1/lwjgl-platform-2.9.4-babric.1-natives-linux.jar");
        downloadLibraries("https://maven.glass-launcher.net/babric/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-babric.1/lwjgl-platform-2.9.4-babric.1-natives-windows.jar");
        downloadLibraries("https://maven.glass-launcher.net/babric/org/lwjgl/lwjgl/lwjgl-platform/2.9.4-babric.1/lwjgl-platform-2.9.4-babric.1-natives-osx.jar");

        // JWGL 3
        downloadLibraries("https://libraries.minecraft.net/org/apache/logging/log4j/log4j-api/2.19.0/log4j-api-2.19.0.jar");
        downloadLibraries("https://libraries.minecraft.net/org/apache/logging/log4j/log4j-core/2.19.0/log4j-core-2.19.0.jar");
        downloadLibraries("https://libraries.minecraft.net/org/apache/logging/log4j/log4j-slf4j2-impl/2.19.0/log4j-slf4j2-impl-2.19.0.jar");
        downloadLibraries("https://libraries.minecraft.net/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar");

        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-freetype/lwjgl-freetype-natives-linux-arm32.jar");
        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-freetype/lwjgl-freetype-natives-linux-arm64.jar");

        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-glfw/lwjgl-glfw-natives-linux-arm32.jar");
        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-glfw/lwjgl-glfw-natives-linux-arm64.jar");

        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-jemalloc/lwjgl-jemalloc-natives-linux-arm32.jar");
        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-jemalloc/lwjgl-jemalloc-natives-linux-arm64.jar");

        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-openal/lwjgl-openal-natives-linux-arm32.jar");
        downloadLibraries("https://build.lwjgl.org/release/3.3.3/bin/lwjgl-openal/lwjgl-openal-natives-linux-arm64.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl/3.3.3/lwjgl-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-freetype/3.3.3/lwjgl-freetype-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.3/lwjgl-glfw-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-jemalloc/3.3.3/lwjgl-jemalloc-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-openal/3.3.3/lwjgl-openal-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-opengl/3.3.3/lwjgl-opengl-3.3.3.jar");

        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-linux.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-macos-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-macos.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-windows-arm64.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-windows-x86.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3-natives-windows.jar");
        downloadLibraries("https://libraries.minecraft.net/org/lwjgl/lwjgl-stb/3.3.3/lwjgl-stb-3.3.3.jar");

        /*downloadLibraries("https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.8.0-beta4/slf4j-api-1.8.0-beta4.jar");
        downloadLibraries("https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-slf4j18-impl/2.16.0/log4j-slf4j18-impl-2.16.0.jar");
        downloadLibraries("https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-core/2.16.0/log4j-core-2.16.0.jar");
        downloadLibraries("https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.9/gson-2.8.9.jar");
        downloadLibraries("https://repo1.maven.org/maven2/com/google/guava/guava/31.0.1-jre/guava-31.0.1-jre.jar");
        downloadLibraries("https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar");
        downloadLibraries("https://repo1.maven.org/maven2/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar");
        downloadLibraries("https://repo1.maven.org/maven2/commons-codec/commons-codec/1.15/commons-codec-1.15.jar");
        downloadLibraries("https://repo1.maven.org/maven2/net/minecrell/terminalconsoleappender/1.2.0/terminalconsoleappender-1.2.0.jar");

        downloadLibraries("https://maven.glass-launcher.net/babric/babric/log4j-config/1.0.0/log4j-config-1.0.0.jar");
        downloadLibraries("https://maven.glass-launcher.net/babric/babric/fabric-loader/0.14.24-babric.1/fabric-loader-0.14.24-babric.1.jar");

        downloadLibraries("https://maven.fabricmc.net/net/fabricmc/tiny-mappings-parser/0.3.0%2Bbuild.17/tiny-mappings-parser-0.3.0%2Bbuild.17.jar");
        downloadLibraries("https://maven.fabricmc.net/net/fabricmc/sponge-mixin/0.11.4%2Bmixin.0.8.5/sponge-mixin-0.11.4%2Bmixin.0.8.5.jar");
        downloadLibraries("https://maven.fabricmc.net/net/fabricmc/tiny-remapper/0.8.2/tiny-remapper-0.8.2.jar");
        downloadLibraries("https://maven.fabricmc.net/net/fabricmc/access-widener/2.1.0/access-widener-2.1.0.jar");
        downloadLibraries("https://maven.fabricmc.net/org/ow2/asm/asm/9.3/asm-9.3.jar");
        downloadLibraries("https://maven.fabricmc.net/org/ow2/asm/asm-analysis/9.3/asm-analysis-9.3.jar");
        downloadLibraries("https://maven.fabricmc.net/org/ow2/asm/asm-commons/9.3/asm-commons-9.3.jar");
        downloadLibraries("https://maven.fabricmc.net/org/ow2/asm/asm-tree/9.3/asm-tree-9.3.jar");
        downloadLibraries("https://maven.fabricmc.net/org/ow2/asm/asm-util/9.3/asm-util-9.3.jar");*/

        createFolder("versions");

        for(Versions.VersionInfo version : Versions.getAll()) {
            String folderName = version.getFileName().toLowerCase();
            String versionDir = "versions/" + folderName;

            createFolder(versionDir);

            File jarFile = new File(path + "/" + versionDir + "/client.jar");

            if(!jarFile.exists()) {
                FileDownloader.download(version.getLink(), versionDir + "/");
            }
        }
    }

    private static void createFolder(String name) {
        File folder = new File(path + "/" + name);
        if(!folder.exists() && !folder.mkdirs()) {
            Logger.log("Failed to create " + name + " folder.");
        }
    }

    private static void downloadLibraries(String url) {
        String[] splitUrl = url.split("/");
        StringBuilder path = new StringBuilder("libraries");
        for(int i = 3; i < splitUrl.length - 1; i++) {
            path.append("/").append(splitUrl[i]);
        }
        createFolder(path.toString());
        FileDownloader.download(url, path + "/");
    }
}
