package perry.betterthanlauncher.util.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {
    public static void download(String fileUrl, String savePath) {
        URL url = null;
        try {
            url = new URL(fileUrl);
        } catch(MalformedURLException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

        String filePath = savePath + fileName;

        try(FileOutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
