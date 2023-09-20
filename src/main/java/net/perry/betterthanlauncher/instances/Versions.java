package net.perry.betterthanlauncher.instances;

public enum Versions {
    BTA_1_7_7_0_02("bta_1.7.7.0_02", "https://github.com/Better-than-Adventure/bta-download-repo/releases/download/v1.7.7.0_02/bta.jar"),
    BTA_1_7_7_0_01("bta_1.7.7.0_01", "https://github.com/Better-than-Adventure/bta-download-repo/releases/download/v1.7.7.0_01/bta.jar"),
    BTA_1_7_7_0("bta_1.7.7.0", "https://github.com/Better-than-Adventure/bta-download-repo/releases/download/v1.7.7.0/bta.jar"),
    B_1_7_3("b_1.7.3", "https://launcher.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar");

    private String fileName;
    private String link;

    Versions(String fileName, String link) {
        this.fileName = fileName;
        this.link = link;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLink() {
        return link;
    }

    public static Versions fileNameToVersion(String name) {
        for(Versions version : Versions.values()) {
            if(version.getFileName().equalsIgnoreCase(name)) {
                return version;
            }
        }
        return null;
    }

    public static boolean fileNameMatchesVersion(String name) {
        for(Versions version : Versions.values()) {
            if(version.getFileName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
