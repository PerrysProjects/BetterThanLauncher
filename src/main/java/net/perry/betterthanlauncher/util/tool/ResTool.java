package net.perry.betterthanlauncher.util.tool;

import net.perry.betterthanlauncher.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;

public class ResTool {
    public static void copy(String resourceName, String targetFolderPath) {
        InputStream inputStream = ResTool.class.getClassLoader().getResourceAsStream(resourceName);

        if(inputStream == null) {
            Logger.error("Error: Resource not found - " + resourceName);
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

    public static void copyFromFolder(String sourceFolder, String destinationFolder) {
        try {
            URL folderUrl = ResTool.class.getClassLoader().getResource(sourceFolder);
            if(folderUrl == null) {
                System.err.println("Error: Folder not found - " + sourceFolder);
                return;
            }

            URI uri = folderUrl.toURI();
            Path folderPath;

            if("file".equals(uri.getScheme())) {
                folderPath = Paths.get(uri);
            } else if("jar".equals(uri.getScheme())) {
                String[] parts = uri.toString().split("!");
                URI jarUri = URI.create(parts[0]);

                try(FileSystem fs = FileSystems.newFileSystem(jarUri, Collections.emptyMap())) {
                    folderPath = fs.getPath(parts[1]);
                    copyFilesFromPath(folderPath, destinationFolder);
                }
                return;
            } else {
                System.err.println("Unsupported URI scheme: " + uri.getScheme());
                return;
            }

            copyFilesFromPath(folderPath, destinationFolder);

        } catch(Exception e) {
            System.err.println("Error accessing folder: " + sourceFolder);
            e.printStackTrace();
        }
    }

    private static void copyFilesFromPath(Path folderPath, String destinationFolder) throws IOException {
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for(Path filePath : directoryStream) {
                if(Files.isRegularFile(filePath)) {
                    Path targetPath = Paths.get(destinationFolder, filePath.getFileName().toString());

                    try(InputStream inputStream = Files.newInputStream(filePath)) {
                        Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch(IOException e) {
                        System.err.println("Error copying file: " + filePath.getFileName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
