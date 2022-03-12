package com.pocolifo.pocolifoclient.mods.mods.autogg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.pocolifo.commons.HttpCommons;
import com.pocolifo.pocolifoclient.mods.AbstractBaseMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.NumberConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.StringConfigurationType;
import com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier;

import static com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier.gson;

import net.minecraft.client.Minecraft;

public class AutoGGMod extends AbstractBaseMod {
	@ModConfiguration(value = "msg", name = "Message", type = StringConfigurationType.class)
	public String message = "gg";

	@ModConfiguration(value = "delay", name = "Message Delay (seconds)", type = NumberConfigurationType.class)
	public float delay = 0.25f;

	private final List<Trigger> triggers = new ArrayList<>();

	public AutoGGMod() {
		super("Auto GG", "Says GG (or any message) at the end of a game because you're too lazy to press a key twice");

		try {
			this.triggers.addAll(gson.fromJson(new String(HttpCommons.download("https://raw.githubusercontent.com/pocolifo/pocolifo-client-open-data/main/autogg.json")), new TypeToken<List<Trigger>>(){}.getType()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean testTriggers(String chat) {
		if (Minecraft.getMinecraft().getCurrentServerData() == null) return false;

		String id = ServerIdentifier.getInstance().identifyCurrentServer().id;

		for (Trigger trigger : this.triggers) {
			if (trigger.serverId.equals(id)) {
				if (trigger.triggers.stream().anyMatch(chat::startsWith)) return true;
			}
		}

		return false;
	}
}
