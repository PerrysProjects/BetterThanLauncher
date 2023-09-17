package perry.betterthanlauncher.util.instances;

import perry.betterthanlauncher.Main;
import perry.betterthanlauncher.util.jars.JarMerger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Instance {
    private final String name;
    private final String path;

    public Instance(String name) {
        this.name = name;
        this.path = Main.path + "/instances/" + name;
        createInstance();
    }

    private void createInstance() {
        File instanceFolder = new File(path);
        if(!instanceFolder.exists() && !instanceFolder.mkdirs()) {
            System.err.println("Failed to create instance folder.");
        }

        if(!new File(path + "/minecraft.jar").exists()) {
            JarMerger jarMerger = new JarMerger();
            jarMerger.merge(Main.path + "/versions/bta_1.7.7.0_02/bta_1.7.7.0_02.jar",
                    Main.path + "/versions/original/client.jar",
                    path + "/minecraft.jar");
        }
    }

    public void start() {
        Thread minecraftThread = new Thread(() -> {
            String javaExecutable = "java";
            String memoryOptions = "-Xms256m -Xmx256m";
            String classpath = "instances/" + name + "/minecraft.jar;libraries/jinput.jar;libraries/lwjgl.jar;libraries/lwjgl_util.jar";
            String libraryPath = "-Djava.library.path=libraries/natives";
            String mainClass = "net.minecraft.client.Minecraft";
            String username = "Perry6226";
            String gameArguments = "--gameDir /instances/" + name + " --assetDir /instances/" + name;
            String command = (javaExecutable + " " + memoryOptions + " -cp \"" + classpath + "\" " + libraryPath + " " + mainClass + " " + username + " " + gameArguments)
                    .replace("/", "\\");
            System.out.println(command);

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = process.waitFor();
                System.out.println("Minecraft exited with Exit Code: " + exitCode);
            } catch(IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        minecraftThread.start();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

