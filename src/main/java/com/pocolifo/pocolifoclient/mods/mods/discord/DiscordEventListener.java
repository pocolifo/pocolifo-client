/*
todo alpha2

package com.pocolifo.pocolifoclient.mods.mods.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import org.json.JSONObject;

public class DiscordEventListener implements IPCListener {
	private final DiscordUpdateHandler updateHandler;

	public DiscordEventListener(DiscordUpdateHandler updateHandler) {
		this.updateHandler = updateHandler;
	}

	@Override
	public void onReady(IPCClient client) {
		this.updateHandler.update();
	}

	@Override
	public void onActivityJoin(IPCClient client, String secret) {
		System.out.printf("Activity join secret: %s%n", secret);
	}

	@Override
	public void onActivityJoinRequest(IPCClient client, String secret, User user) {
		System.out.printf("Received join request from %s#%s! Secret: %s%n", user.getName(), user.getDiscriminator(), secret);
	}

	@Override
	public void onPacketSent(IPCClient client, Packet packet) {
		System.out.printf("Packet sent: %s%n", packet.toString());
	}

	@Override
	public void onPacketReceived(IPCClient client, Packet packet) {
		System.out.printf("Packet received: %s%n", packet.toString());
	}

	@Override
	public void onActivitySpectate(IPCClient client, String secret) {
		System.out.printf("Activity spectate: %s%n", secret);
	}

	@Override
	public void onClose(IPCClient client, JSONObject json) {
		System.out.printf("Close: %s%n", json == null ? "null" : json.toString());
	}

	@Override
	public void onDisconnect(IPCClient client, Throwable t) {
		System.out.println("Disconnect");
		t.printStackTrace();
	}
}
*/