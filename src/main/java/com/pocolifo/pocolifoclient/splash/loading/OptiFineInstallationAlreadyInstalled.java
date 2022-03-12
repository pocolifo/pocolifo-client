package com.pocolifo.pocolifoclient.splash.loading;

import com.pocolifo.pocolifoclient.installer.library.Installation;
import com.pocolifo.pocolifoclient.installer.library.library.optifine.OptiFineInstallation;

import java.io.IOException;

public class OptiFineInstallationAlreadyInstalled extends OptiFineInstallation {
    public OptiFineInstallationAlreadyInstalled(String minecraftVersion) throws IOException {
        super(minecraftVersion);
    }

    @Override
    public void downloadAndInstall(Installation installation) throws IOException {}
}
