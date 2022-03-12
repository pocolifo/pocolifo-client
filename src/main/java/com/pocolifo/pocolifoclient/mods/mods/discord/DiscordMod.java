package com.pocolifo.pocolifoclient.mods.mods.discord;

import com.pocolifo.pocolifoclient.mods.AbstractBaseMod;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationListener;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.StringConfigurationType;
import com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier;
import com.pocolifo.pocolifoclient.util.serverid.ServerMapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class DiscordMod extends AbstractBaseMod implements ConfigurationListener {
	protected final long APPLICATION_ID = 934848135358996480L;
	private DiscordUpdateHandler discordUpdateHandler;

	@ModConfiguration(value = "show_current_server", name = "Show Current Server", type = BooleanConfigurationType.class)
	public boolean showCurrentServer = true;

	@ModConfiguration(value = "pretty_server_names", name = "Pretty Server Names", type = BooleanConfigurationType.class)
	public boolean prettyServerNames = true;

	// TODO: enum config type
	@ModConfiguration(value = "target_discord_build", name = "Target Discord Build", type = StringConfigurationType.class)
	public String targetDiscordBuild = "STABLE";

	public DiscordMod() {
		super("Discord", "Rep Pocolifo Client on Discord!"); //  AND join a friend's Minecraft server from within Discord!
	}

	@Override
	public void enable() {
		super.enable();

		this.discordUpdateHandler = new DiscordUpdateHandler(this);
		this.discordUpdateHandler.start();
		this.setCurrentState();
	}

	@Override
	public void disable() {
		super.disable();

		if (this.discordUpdateHandler != null) this.discordUpdateHandler.stop();
	}

	private void setCurrentState() {
		ServerData currentServerData = Minecraft.getMinecraft().getCurrentServerData();

		if (currentServerData == null) {
			if (Minecraft.getMinecraft().theWorld == null) {
				this.usingTitleScreen();
			} else {
				this.playingSingleplayer();
			}
		} else {
			this.updateWithServerData(currentServerData);
		}
	}

	public void updateWithServerData(ServerData serverData) {
		if (this.showCurrentServer) {
			// TODO: possible error
			try {
				// TODO: alpha2 DiscordParty.createParty(serverData.serverIP, this.discordUpdateHandler);
			} catch (IllegalStateException ignored) { /* if you're already participating in a Discord party */ }

			this.discordUpdateHandler.details = String.format("Playing on %s", serverData.serverIP);

			if (this.prettyServerNames) {
				ServerMapping identified = ServerIdentifier.getInstance().identify(serverData);

				if (identified != null) {
					this.discordUpdateHandler.details = String.format("Playing on %s", identified.name);
				}
			}
		} else {
			this.discordUpdateHandler.details = "Multiplayer";
		}

		this.updateState();
	}

	public void usingTitleScreen() {
		this.discordUpdateHandler.details = "Not Playing";
		this.updateState();
	}

	public void playingSingleplayer() {
		this.discordUpdateHandler.details = "Singleplayer";
		this.updateState();
	}

	private void updateState() {
		this.discordUpdateHandler.state = String.format("Minecraft %s", Minecraft.getSessionInfo().get("X-Minecraft-Version"));
		this.discordUpdateHandler.update();
	}

	@Override
	public void onConfigurationChanged() {
		if (Minecraft.getMinecraft().getCurrentServerData() != null) this.updateWithServerData(Minecraft.getMinecraft().getCurrentServerData());
	}
}
