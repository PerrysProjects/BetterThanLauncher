package net.perry.betterthanlauncher.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.lenni0451.commons.httpclient.HttpClient;
import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.frames.AuthFrame;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.java.StepMCProfile;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import javax.naming.AuthenticationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Auth {
    private StepFullJavaSession.FullJavaSession loadedProfile;
    private File file;

    private AuthFrame authFrame;

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
        StepFullJavaSession.FullJavaSession profile = null;
        HttpClient httpClient = MinecraftAuth.createHttpClient();

        try {
            profile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(
                    httpClient,
                    new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
                        authFrame = new AuthFrame(msaDeviceCode);
                    })
            );
        } catch(TimeoutException e) {
            Logger.error("Timeout during device code login", e);
            System.exit(ExitCode.TIMEOUT_LOGIN.code);
        } catch(AuthenticationException e) {
            Logger.error("Authentication failed", e);
            System.exit(ExitCode.AUTH_FAILED.code);
        } catch (Exception e) {
            Logger.error("Unexpected login error", e);
            System.exit(ExitCode.GENERAL_ERROR.code);
        }

        if (profile == null) {
            Logger.error("Login returned null profile");
            System.exit(ExitCode.AUTH_FAILED.code);
        }

        final JsonObject serializedProfile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.toJson(profile);
        String jsonString = new Gson().toJson(serializedProfile);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
            loadedProfile = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.fromJson(serializedProfile);
        } catch(IOException e) {
            Logger.error("Failed to write session to file", e);
            System.exit(ExitCode.FILE_WRITE_FAILED.code);
        } finally {
            if (authFrame != null) authFrame.dispose();
        }
    }

    public StepFullJavaSession.FullJavaSession getLoadedProfile() {
        return loadedProfile;
    }

    public File getFile() {
        return file;
    }
}
