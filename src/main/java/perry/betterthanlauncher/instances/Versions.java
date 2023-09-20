package perry.betterthanlauncher.instances;

public enum Versions {
    BTA_1_7_7_0_02("bta_1.7.7.0_02"),
    BTA_1_7_7_0_01("bta_1.7.7.0_01"),
    BTA_1_7_7_0("bta_1.7.7.0"),
    B_1_7_3("b_1.7.3");

    private String fileName;

    Versions(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
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
