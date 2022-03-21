package com.pocolifo.pocolifoclient.mods.mods.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.launch.BuildProperties;
import lombok.Getter;

import java.time.OffsetDateTime;

public class DiscordUpdateHandler {
	private static final OffsetDateTime startDateTime = OffsetDateTime.now();

	@Getter private final IPCClient client;
	@Getter private final DiscordMod mod;

	// TODO: alpha2 protected DiscordParty currentParty;

	protected String details;
	protected String state;

	public DiscordUpdateHandler(DiscordMod mod) {
		this.client = new IPCClient(mod.APPLICATION_ID);
		this.mod = mod;
	}

	public RichPresence.Builder getPresence() {
		if (!this.isConnected()) return null;

		RichPresence.Builder builder = new RichPresence.Builder();

		builder.setStartTimestamp(startDateTime);
		builder.setLargeImage("pcl_1024x1024", BuildProperties.NAME);

		return builder;
	}

	public void start() {
		// todo alpha2 this.client.setListener(new DiscordEventListener(this));

		try {
			try {
				this.client.connect(DiscordBuild.ANY);
			} catch (Exception ignored) {}
		} catch (Exception ignored) {}
	}

	public void update() {
		if (this.isConnected()) {

			RichPresence.Builder b = getPresence();

			b.setDetails(this.details);
			b.setState(this.state);

			/* TODO: alpha2
			if (this.currentParty != null) {
				b.setParty(this.currentParty.getPartyId().toString(), this.currentParty.getCurrentPlayerCount(), this.currentParty.getMaxPlayers());
				b.setJoinSecret(this.currentParty.getJoinSecret());
			}*/

			this.update(b);
		}
	}

	public void update(RichPresence.Builder builder) {
		if (this.isConnected()) this.client.sendRichPresence(builder.build());
	}

	private boolean isConnected() {
		return this.client.getStatus() == PipeStatus.CONNECTED;
	}

	public void stop() {
		if (this.isConnected()) this.client.close();
	}
}
