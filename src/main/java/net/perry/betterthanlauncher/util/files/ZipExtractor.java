package net.perry.betterthanlauncher.util.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    public static void extract(String zipFilePath, String extractPath) {
        File destDir = new File(extractPath);
        byte[] buffer = new byte[1024];

        try(ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while(zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                try(FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    while((len = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }

            zipInputStream.closeEntry();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        delete(zipFilePath);
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if(!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    private static void delete(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
