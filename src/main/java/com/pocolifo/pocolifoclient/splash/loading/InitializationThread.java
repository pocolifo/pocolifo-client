package com.pocolifo.pocolifoclient.splash.loading;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.installer.library.Installation;
import com.pocolifo.pocolifoclient.installer.library.InstallationOptions;
import com.pocolifo.pocolifoclient.installer.library.library.optifine.OptiFineInstallation;
import com.pocolifo.pocolifoclient.installer.library.library.plc.PocolifoClientInstallation;
import com.pocolifo.pocolifoclient.installer.library.library.plc.PocolifoClientLauncherProfile;
import com.pocolifo.pocolifoclient.installer.library.library.plc.PocolifoClientVersion;
import com.pocolifo.pocolifoclient.installer.library.library.plc.PocolifoClientVersionTemplate;
import com.pocolifo.pocolifoclient.launch.BuildProperties;
import com.pocolifo.pocolifoclient.launch.LaunchEnvironment;
import com.pocolifo.pocolifoclient.util.OFConfig;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Objects;

public class InitializationThread extends Thread {
	public String state = "Initializing...";

	@Override
	public void run() {
		try {
			Thread.sleep(500L);

			this.state = "Loading client...";

			PocolifoClient.init();

			this.state = "Checking for updates...";

			PocolifoClientVersion clientVersion = new PocolifoClientVersion(BuildProperties.VERSION);
			PocolifoClientVersionTemplate currentVersionTemplate = clientVersion.getTemplate();
			PocolifoClientVersionTemplate.Versioning currentVersioning = currentVersionTemplate.getVersioning();

			PocolifoClientVersionTemplate latestVersion = PocolifoClientVersionTemplate.getLatestVersion(
					currentVersioning.getChannel(),
					currentVersioning.getCompatibility(),
					LaunchEnvironment.getInstance().optiFine);

			boolean sameChannel = latestVersion.getVersioning().getChannel().equals(currentVersioning.getChannel());
			boolean sameCompatibility = latestVersion.getVersioning().getCompatibility().equals(currentVersioning.getCompatibility());
			boolean differentRevision = !latestVersion.getVersioning().getRevision().equals(currentVersioning.getRevision());
			boolean releasedLater = latestVersion.getReleaseTime() > currentVersionTemplate.getReleaseTime();

			boolean isUpdateAvailable = sameChannel && sameCompatibility && differentRevision && releasedLater;

			if (!isUpdateAvailable) return;

			try {


				this.state = "An update is available! Preparing update...";

				// give the user some time to actually acknowledge what's happening
				Thread.sleep(1000L);

				PocolifoClientVersion plcLatestVersion = new PocolifoClientVersion(latestVersion.getVersioning().getId());
				PocolifoClientInstallation plc = new PocolifoClientInstallation(plcLatestVersion);
				InstallationOptions options = new InstallationOptions(plc);

				// automatically fetches latest OptiFine version if there is a new release
				if (LaunchEnvironment.getInstance().optiFine) {
					String latestOptiFineVersion = OptiFineInstallation.getLatestOptiFineVersion(latestVersion.getVersioning().getCompatibility());

					if (!latestOptiFineVersion.equals(OFConfig.OF_RELEASE)) {
						options.setOptiFineInstallation(new OptiFineInstallation(latestVersion.getVersioning().getCompatibility()));
					} else {
						// hack to include the --tweakClass argument but not download optifine again
						options.setOptiFineInstallation(new OptiFineInstallationAlreadyInstalled(latestVersion.getVersioning().getCompatibility()));
					}
				}

				this.state = "Downloading update...";

				Installation installation = new Installation(options);
				installation.install();

				this.state = "Finishing things up...";

				PocolifoClientLauncherProfile profile = new PocolifoClientLauncherProfile(plcLatestVersion, plc.getLauncherMetadata());
				profile.installProfile(installation);
				profile.selectProfile(installation);

				this.state = "Update complete!";

				// todo draw in-game
				JOptionPane.showMessageDialog(null, "We've just downloaded an update for you that contains exciting new features!\nPlease CLOSE THE MINECRAFT LAUNCHER and RESTART THE CLIENT! Click OK to close the client.", "Fantastic new features ahead!", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/minecraft/pmc/logo/128.png"))));

				// schedule shutdown
				this.state = "Closing game...";
				Minecraft.getMinecraft().shutdown();
			} catch (Exception e) {
				e.printStackTrace();

				this.state = "There was an error while updating.";

				try {
					Thread.sleep(2500L);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			this.state = "There was an error while checking for updates.";

			try {
				Thread.sleep(2500L);
			} catch (InterruptedException ignored) {}
		}
	}

	/*
	todo idk use this later sometime maybe

	public static String getStartCommand() {
		return getCurrentJavaExecPath() + " -cp " + System.getProperty("java.class.path") + " " + String.join(" ", ManagementFactory.getRuntimeMXBean().getInputArguments()) + " " + LaunchArgumentInterceptor.class.getCanonicalName() + " " + String.join(" ", LaunchArgumentInterceptor.arguments);
	}

	public static String getCurrentJavaExecPath() {
		File bin = new File(System.getProperty("java.home"), "bin");
		File exec = bin.listFiles((file, s) -> s.startsWith("java"))[0];

		return exec.getAbsolutePath();
	}*/
}
