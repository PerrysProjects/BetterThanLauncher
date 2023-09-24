package net.perry.betterthanlauncher.util.files;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private final String filePath;
    private final Map<String, Object> configMap;

    public Config(String path, String name, String fileType) {
        this.filePath = path + File.separator + name + "." + fileType;
        configMap = new HashMap<>();
        createConfigFile();
        readConfigFile();
    }

    public Config(String filePath) {
        this.filePath = filePath;
        configMap = new HashMap<>();
        readConfigFile();
    }

    private void createConfigFile() {
        try {
            File configFile = new File(filePath);
            if(!configFile.exists()) {
                if(!configFile.createNewFile()) {
                    System.err.println("Failed to create config file: " + filePath);
                }
            }
        } catch(IOException e) {
            System.err.println("Error creating config file: " + e.getMessage());
        }
    }

    private void readConfigFile() {
        File configFile = new File(filePath);
        if(!configFile.exists()) {
            System.err.println("Config file not found: " + filePath);
            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if(parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    Object parsedValue;
                    try {
                        parsedValue = Integer.parseInt(value);
                    } catch(NumberFormatException e) {
                        parsedValue = value;
                    }
                    configMap.put(key, parsedValue);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeConfig(String key, String value) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            boolean keyExists = false;
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith(key + ": ")) {
                    keyExists = true;
                    break;
                }
            }

            if(!keyExists) {
                writer.write(key + ": " + value);
                writer.newLine();
            }

        } catch(IOException e) {
            System.err.println("Error writing config: " + e.getMessage());
        }
    }

    public void writeComment(String text) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            boolean commandExists = false;
            String line;
            while((line = reader.readLine()) != null) {
                if(line.equals("#" + text)) {
                    commandExists = true;
                    break;
                }
            }

            if(!commandExists) {
                writer.write("#" + text);
                writer.newLine();
            }

        } catch(IOException e) {
            System.err.println("Error writing config: " + e.getMessage());
        }
    }

    public void writeConfig(Map<String, String> configMap) {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for(Map.Entry<String, String> entry : configMap.entrySet()) {
                boolean keyExists = false;
                String line;
                while((line = reader.readLine()) != null) {
                    if(line.startsWith(entry.getKey() + ": ")) {
                        keyExists = true;
                        break;
                    }
                }

                if(!keyExists) {
                    writer.write(entry.getKey() + ": " + entry.getValue());
                    writer.newLine();
                }
            }

        } catch(IOException e) {
            System.err.println("Error writing config: " + e.getMessage());
        }
    }

    public Object getValue(String key) {
        return configMap.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Configurations:\n");

        for(Map.Entry<String, Object> entry : configMap.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }
}
