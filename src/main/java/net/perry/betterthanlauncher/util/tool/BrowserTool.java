package net.perry.betterthanlauncher.util.tool;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.OsManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class BrowserTool {
    public static void open(URI uri) throws IOException {
        if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(uri);
        } else if(OsManager.isLinux()) {
            Runtime.getRuntime().exec(new String[]{"xdg-open", uri.toString()});
        }
    }
}
