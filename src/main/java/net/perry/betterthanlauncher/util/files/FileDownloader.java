package net.perry.betterthanlauncher.util.files;

import net.perry.betterthanlauncher.util.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloader {
    public static void download(String fileUrl, String savePath) {
        URL url;
        try {
            url = new URL(fileUrl);
        } catch(MalformedURLException e) {
            throw new RuntimeException(e);
        }

        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        String filePath = savePath + fileName;

        Path destinationPath = Paths.get(filePath);
        if(Files.exists(destinationPath)) {
            return;
        }

        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch(IOException e) {
            Logger.error(e);
        }

        try(FileOutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch(IOException e) {
            Logger.error(e);
        } finally {
            try {
                inputStream.close();
            } catch(IOException e) {
                Logger.error(e);
            }
        }
    }
}
