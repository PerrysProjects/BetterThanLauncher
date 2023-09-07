package perry.betterthanlauncher;

import perry.betterthanlauncher.util.WebsiteTool;

public class Main {
    public static void main(String[] args) {
        String url = "https://bta-modding.nouma-vallee.fr";
        String targetPath = "Downloads";

        //document.querySelector("body > main > div > div > div:nth-child(1)") <- this one not
        //document.querySelector("body > main > div > div > div:nth-child(2)")
        //document.querySelector("body > main > div > div > div:nth-child(3)")
        String cssSelector = "body > main > div > div > div.p-5.bg-card.rounded-xl.shadow-lg.markdown-body";

        WebsiteTool wbt = new WebsiteTool(url);
        System.out.println(wbt.getParagraphs("/SamuelDeboni/potato-logistics", cssSelector));
        System.out.println(wbt.countDivs("/mods", "body > main > div > div > div", "my-3 py-4 px-4 bg-card rounded-xl shadow-lg flex "));
        System.out.println(wbt.getDivContent("/mods", "body > main > div > div > div", "my-3 py-4 px-4 bg-card rounded-xl shadow-lg flex "));
    }
}
