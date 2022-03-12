/* TODO: alpha2

package com.pocolifo.pocolifoclient.mods.mods.discord;

import java.util.UUID;

import lombok.Getter;

public class DiscordParty {
	public static final String SEPARATOR = "/";

	@Getter private UUID partyId;
	private String serverIp;
	private DiscordUpdateHandler updateHandler;

	DiscordParty() {}

	public static void createParty(String serverIp, DiscordUpdateHandler updateHandler) throws IllegalStateException {
		if (updateHandler.currentParty != null) throw new IllegalStateException("already participating in a party");

		DiscordParty party = new DiscordParty();

		party.partyId = UUID.randomUUID();
		party.serverIp = serverIp;

		party.updateHandler = updateHandler;
		party.updateHandler.currentParty = party;
		party.updateHandler.update();
	}

	public static void joinParty(String partyString, DiscordUpdateHandler updateHandler) throws IllegalArgumentException, IllegalStateException {
		if (updateHandler.currentParty != null) throw new IllegalStateException("already participating in a party");

		String[] split = partyString.split(SEPARATOR);

		if (split.length == 2) {
			DiscordParty party = new DiscordParty();

			party.partyId = UUID.fromString(split[0]);
			party.serverIp = split[1];

			party.updateHandler = updateHandler;
			party.updateHandler.currentParty = party;
			party.updateHandler.update();
		} else {
			throw new IllegalArgumentException("invalid party string: " + partyString);
		}
	}

	public void leaveParty() {
		this.updateHandler.currentParty = null;
		this.updateHandler.update();

		this.partyId = null;
		this.serverIp = null;
		this.updateHandler = null;
	}

	public String getJoinSecret() {
		return partyId + SEPARATOR + serverIp;
	}

	public int getCurrentPlayerCount() {
		return 1;
	}

	public int getMaxPlayers() {
		return 2;
	}
}
*/