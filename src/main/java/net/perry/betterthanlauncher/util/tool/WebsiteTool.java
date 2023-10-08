package net.perry.betterthanlauncher.util.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebsiteTool {
    private final String url;

    public WebsiteTool(String url) {
        this.url = url;
    }

    public int countDivs(String endUrl, String cssSelector) {
        try {
            Document document = Jsoup.connect(url + endUrl).get();
            Elements divElements = document.select(cssSelector);
            int count = 0;

            for(Element div : divElements) {
                count++;
            }

            return count;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int countDivs(String urlEnd, String cssSelector, String targetClass) {
        try {
            Document document = Jsoup.connect(url + urlEnd).get();
            Elements divElements = document.select(cssSelector);
            int count = 0;

            for(Element div : divElements) {
                if(div.hasClass(targetClass)) {
                    count++;
                }
            }

            return count;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDivContent(String urlEnd, String cssSelector, String targetClass) {
        try {
            List<String> content = new ArrayList<>();
            Document document = Jsoup.connect(url + urlEnd).get();
            Elements divElements = document.select(cssSelector);

            for(Element div : divElements) {
                if(div.hasClass(targetClass)) {
                    String divContent = div.text();
                    content.add(divContent);
                }
            }

            return content;
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getParagraphs(String urlEnd, String cssSelector) {
        List<String> paragraphs = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url + urlEnd).get();

            Element element = doc.select(cssSelector).first();
            if(element != null) {
                Elements pTags = element.select("p");

                for(Element pTag : pTags) {
                    String paragraphText = String.valueOf(pTag);
                    paragraphs.add(paragraphText);
                }
            } else {
                System.err.println("Element not found for the given CSS selector");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return paragraphs;
    }

    public void downloadFile(String urlEnd, String path) {
        try {
            URL url = new URL(this.url + urlEnd);
            try(InputStream in = new BufferedInputStream(url.openStream()); FileOutputStream fileOutputStream = new FileOutputStream(path)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("File downloaded successfully.");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
