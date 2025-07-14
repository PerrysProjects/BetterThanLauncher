package net.perry.betterthanlauncher.components.frames;

import net.perry.betterthanlauncher.Main;
import net.perry.betterthanlauncher.components.panels.AuthPanel;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import javax.swing.*;

public class AuthFrame extends JFrame {
    public AuthFrame(StepMsaDeviceCode.MsaDeviceCode msaDeviceCode) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Main.NAME + " | Authentication");
        setContentPane(new AuthPanel(msaDeviceCode));
        pack();
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(Main.ICON);

        setVisible(true);
    }
}
