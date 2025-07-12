package net.perry.betterthanlauncher.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static String timestamp() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    }

    public static void log(String message) {
        System.out.printf("[%s] %s%n", timestamp(), message);
    }

    public static void warn(String message) {
        System.out.printf("[%s] WARNING: %s%n", timestamp(), message);
    }

    public static void error(String message) {
        error(message, null);
    }

    public static void error(Exception e) {
        error("", e);
    }

    public static void error(String message, Exception e) {
        message += (e != null) ? e.getMessage() : "";
        System.out.printf("[%s] ERROR: %s%n", timestamp(), message);
    }
}
