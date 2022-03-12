package com.pocolifo.pocolifoclient.mods.mods.currentserver;

import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;
import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;
import com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier;
import com.pocolifo.pocolifoclient.util.serverid.ServerMapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class CurrentServerMod extends AbstractDraggableTextMod {
	@ModConfiguration(value = "pretty_server_names", name = "Pretty Server Names", type = BooleanConfigurationType.class)
	public boolean prettyServerNames = true;

	@ModConfiguration(value = "use_server_name", name = "Use Server Name, not IP", type = BooleanConfigurationType.class)
	public boolean useServerName = false;

	private ServerData lastServerData;
	public String lastServerName;

	public CurrentServerMod() {
		super("Current Server", "Displays the name/IP of the server you're connected to");
	}

	@Override
	protected String getText() {
		ServerData currentServerData = Minecraft.getMinecraft().getCurrentServerData();
		if (currentServerData == null) return null;

		return getName(currentServerData);
	}

	public String getName(ServerData serverData) {
		if (serverData == this.lastServerData) {
			return lastServerName;
		} else {
			String serverName;

			ServerMapping identified = ServerIdentifier.getInstance().identify(serverData);

			if (identified == null) {
				if (useServerName) {
					serverName = serverData.serverName;
				} else {
					serverName = serverData.serverIP;
				}
			} else {
				serverName = identified.name;
			}

			this.lastServerName = serverName;
			this.lastServerData = serverData;
			return serverName;
		}
	}
}
