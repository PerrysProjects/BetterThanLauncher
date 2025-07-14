package net.perry.betterthanlauncher.instances;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.util.Logger;
import net.perry.betterthanlauncher.util.files.FileDownloader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Versions {
    private static final String BASE_URL =
            "https://downloads.betterthanadventure.net/bta-client/release/";
    private static final Map<String, VersionInfo> versionsMap = new LinkedHashMap<>();

    static {
        initVersionsFromFolders();
        sortVersionsMap();
    }

    private static void sortVersionsMap() {
        Map<String, VersionInfo> sorted = versionsMap.entrySet().stream()
                .sorted(Map.Entry.<String, VersionInfo>comparingByKey().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        versionsMap.clear();
        versionsMap.putAll(sorted);

        versionsMap.put("b_1.7.3", new VersionInfo("b_1.7.3",
                "https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar")
        );
    }

    private static void initVersionsFromFolders() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();

                Pattern pattern = Pattern.compile("href=\"(v[^\"]+/)\"");
                Matcher matcher = pattern.matcher(body);

                while (matcher.find()) {
                    String folder = matcher.group(1).replace("/", "");
                    String name = folder.toLowerCase().replaceFirst("^v", "bta_");
                    String downloadUrl = BASE_URL + folder + "/client.jar";
                    versionsMap.put(name, new VersionInfo(name, downloadUrl));
                }
            } else {
                System.err.println("Failed to fetch directory listing. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static VersionInfo getByFileName(String name) {
        return versionsMap.get(name.toLowerCase());
    }

    public static boolean exists(String name) {
        return versionsMap.containsKey(name.toLowerCase());
    }

    public static Collection<VersionInfo> getAll() {
        return versionsMap.values();
    }

    public static void download() {
        createFolder("versions");

        for(Versions.VersionInfo version : Versions.getAll()) {
            String folderName = version.getFileName().toLowerCase();
            String versionDir = "versions/" + folderName;

            createFolder(versionDir);

            File jarFile = new File(Main.PATH + "/" + versionDir + "/client.jar");

            if(!jarFile.exists()) {
                FileDownloader.download(version.getLink(), versionDir + "/");
            }
        }
    }

    private static void createFolder(String name) {
        File folder = new File(Main.PATH + "/" + name);
        if(!folder.exists() && !folder.mkdirs()) {
            Logger.log("Failed to create " + name + " folder.");
        }
    }

    public static class VersionInfo {
        private final String fileName;
        private final String link;
        private String babric;

        public VersionInfo(String fileName, String link) {
            this.fileName = fileName;
            this.link = link;
        }

        public String getFileName() {
            return fileName;
        }

        public String getLink() {
            return link;
        }

        public String getBabric() {
            return babric;
        }

        public void setBabric(String babric) {
            this.babric = babric;
        }
    }
}
