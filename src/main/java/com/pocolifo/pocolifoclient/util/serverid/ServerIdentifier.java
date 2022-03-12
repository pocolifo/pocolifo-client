package com.pocolifo.pocolifoclient.util.serverid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pocolifo.commons.HttpCommons;
import lombok.Getter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerIdentifier {
	public static final Gson gson = new Gson();
	@Getter private static ServerIdentifier instance;

	@Getter private final List<ServerMapping> servers;

	public static void init() {
		instance = new ServerIdentifier();
	}

	private ServerIdentifier() {
		String url = "https://raw.githubusercontent.com/LunarClient/ServerMappings/master/servers.json";
		List<ServerMapping> serversTemp;

		try {
			serversTemp = gson.fromJson(new String(HttpCommons.download(url)), new TypeToken<List<ServerMapping>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
			serversTemp = new ArrayList<>();
		}

		this.servers = serversTemp;
	}

	public ServerMapping identify(ServerData data) {
		if (data == null) return null;

		for (ServerMapping server : servers) {
			for (String address : server.addresses) {
				if (data.serverIP.equals(address) || data.serverIP.endsWith("." + address)) {
					return server;
				}
			}
		}

		return null;
	}

	public ServerMapping identifyCurrentServer() {
		return this.identify(Minecraft.getMinecraft().getCurrentServerData());
	}
}
