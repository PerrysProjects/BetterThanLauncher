package net.perry.betterthanlauncher.util.jars;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class JarMerger {

    private Map<String, Boolean> entryMap;

    public JarMerger() {
        entryMap = new HashMap<>();
    }

    public void merge(String jarFile1, String jarFile2, String outputJar) {
        try {
            FileOutputStream fos = new FileOutputStream(outputJar);
            JarOutputStream jos = new JarOutputStream(fos);

            addEntries(jarFile1, jos);

            addEntries(jarFile2, jos);

            jos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntries(String jarFile, JarOutputStream jos) throws IOException {
        FileInputStream fis = new FileInputStream(jarFile);
        JarInputStream jis = new JarInputStream(fis);

        JarEntry entry;
        while((entry = jis.getNextJarEntry()) != null) {
            String entryName = entry.getName();

            if(entryMap.containsKey(entryName) && entryMap.get(entryName)) {
                continue;
            }

            JarEntry newEntry = new JarEntry(entryName);
            jos.putNextEntry(newEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = jis.read(buffer)) != -1) {
                jos.write(buffer, 0, bytesRead);
            }

            entryMap.put(entryName, true);

            jos.closeEntry();
        }

        jis.close();
    }
}
