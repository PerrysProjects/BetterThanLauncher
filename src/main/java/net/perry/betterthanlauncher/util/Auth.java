package net.perry.betterthanlauncher.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.frames.AuthFrame;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.java.StepMCProfile;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;
import net.raphimc.mcauth.util.MicrosoftConstants;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Auth {
    private StepMCProfile.MCProfile loadedProfile;
    private File file;

    public Auth() {
        file = new File(Main.path + "/profile.json");
        if(file.exists()) {
            try(FileReader reader = new FileReader(file)) {
                JsonObject serializedProfile = JsonParser.parseReader(reader).getAsJsonObject();
                loadedProfile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(serializedProfile);
            } catch(Exception e) {
                Logger.error(e);
            }
        }
    }

    public void codeLogIn() {
        StepMCProfile.MCProfile mcProfile = null;
        try(final CloseableHttpClient httpClient = MicrosoftConstants.createHttpClient()) {
            mcProfile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(httpClient, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                AuthFrame authFrame = new AuthFrame(msaDeviceCode);
            }));
        } catch(Exception e) {
            System.exit(80);
            Logger.error(e);
        }

        final JsonObject serializedProfile = mcProfile.toJson();

        String jsonString = new Gson().toJson(serializedProfile);
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
            loadedProfile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(serializedProfile);
        } catch(Exception e) {
            Logger.error(e);
        }
    }

    public StepMCProfile.MCProfile getLoadedProfile() {
        return loadedProfile;
    }

    public File getFile() {
        return file;
    }
}
