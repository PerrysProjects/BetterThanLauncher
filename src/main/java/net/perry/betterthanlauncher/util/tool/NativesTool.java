package net.perry.betterthanlauncher.util.tool;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class NativesTool {
    public static void extractNatives(File jarFile, File outputDir) throws IOException {
        if(!outputDir.exists()) outputDir.mkdirs();

        File metaDir = new File(outputDir, "META-INF/");
        if(!metaDir.exists()) metaDir.mkdirs();

        try(ZipFile zip = new ZipFile(jarFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while(entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if(name.endsWith(".dll") || name.endsWith(".so") || name.endsWith(".dylib")) {
                    File outFile = new File(outputDir, new File(name).getName());

                    try(InputStream in = zip.getInputStream(entry);
                        OutputStream out = new FileOutputStream(outFile)) {

                        byte[] buffer = new byte[4096];
                        int len;
                        while((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    }
                } else if(name.startsWith("META-INF/") && !entry.isDirectory()) {
                    String strippedName = name.substring("META-INF/".length());
                    File outFile = new File(metaDir, strippedName);
                    outFile.getParentFile().mkdirs();

                    try(InputStream in = zip.getInputStream(entry);
                        OutputStream out = new FileOutputStream(outFile)) {

                        byte[] buffer = new byte[4096];
                        int len;
                        while((len = in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
            }
        }
    }
}
