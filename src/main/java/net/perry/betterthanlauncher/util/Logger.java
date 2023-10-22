package net.perry.betterthanlauncher.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Logger {
    public static String log(String text) {
        String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        return System.out.printf("[%s] %s%n", date, text).toString();
    }

    public static String error(Exception e) {
        String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        return System.out.printf("[%s] An error occurred: %s%n", date, e.getMessage()).toString();
    }
}
