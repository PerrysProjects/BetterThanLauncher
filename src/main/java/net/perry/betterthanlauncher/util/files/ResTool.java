package net.perry.betterthanlauncher.util.files;

import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ResTool {
    public static void copy(String resourceName, String targetFolderPath) {
        InputStream inputStream = ResTool.class.getClassLoader().getResourceAsStream(resourceName);

        if(inputStream == null) {
            System.err.println("Error: Resource not found - " + resourceName);
            return;
        }

        String[] resourceNameSplit = resourceName.replace("\\", "/").split("/");
        Path targetFilePath = Paths.get(targetFolderPath, resourceNameSplit[resourceNameSplit.length - 1]);

        try {
            if(!Files.exists(targetFilePath)) {
                Files.copy(inputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch(IOException e) {
            System.err.println("Error: Unable to copy the file.");
            e.printStackTrace();
        }
    }

    public static void copyFolder(String sourceFolder, String destinationFolder) {
        File source = new File(ClassLoader.getSystemClassLoader().getResource(sourceFolder).getFile());
        File destination = new File(destinationFolder);

        try {
            FileUtils.copyDirectory(source, destination);
            System.out.println("Folder copied successfully from " + source.getAbsolutePath() +
                    " to " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error copying folder: " + e.getMessage());
        }
    }
}
