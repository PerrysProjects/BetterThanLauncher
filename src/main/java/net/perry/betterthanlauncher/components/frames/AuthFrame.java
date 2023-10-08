package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.panels.AuthPanel;
import net.perry.betterthanlauncher.util.Logger;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AuthFrame extends JFrame {
    public AuthFrame(StepMsaDeviceCode.MsaDeviceCode msaDeviceCode) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Main.name + " Authentication");
        setContentPane(new AuthPanel(msaDeviceCode));
        pack();
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        //createBufferStrategy(4);

        try {
            BufferedImage iconBI = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icons/btl_icon.png")));
            setIconImage(iconBI);
        } catch(IOException e) {
            Logger.error(e);
        }

        setVisible(true);
    }
}
