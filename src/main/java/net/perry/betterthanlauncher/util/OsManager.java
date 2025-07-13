package net.perry.betterthanlauncher.util;

public class OsManager {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

    public static boolean isLinux() {
        return OS.contains("nux") || OS.contains("nix");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static String getOSName() {
        if (isWindows()) return "Windows";
        if (isMac()) return "Mac";
        if (isLinux()) return "Linux";
        return "Unknown";
    }
}

