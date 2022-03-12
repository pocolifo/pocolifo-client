package com.pocolifo.pocolifoclient.mods.mods.autotip;

import java.util.Objects;
import java.util.TimerTask;

import com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier;
import com.pocolifo.pocolifoclient.util.serverid.ServerMapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class AutoTipTask extends TimerTask {
	private final AutoTipMod autoTipMod;

	public AutoTipTask(AutoTipMod autoTipMod) {
		this.autoTipMod = autoTipMod;
	}

	@Override
	public void run() {
		ServerMapping serverMapping = ServerIdentifier.getInstance().identifyCurrentServer();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		if (serverMapping == null || player == null) return;

		if (serverMapping.id.equals("hypixel")) {
			StringBuilder builder = new StringBuilder("/tip ");

			if (this.autoTipMod.tipOnePlayer) {
				builder.append(this.autoTipMod.tipPlayerName);
			} else {
				builder.append("all");
			}

			if (this.autoTipMod.tipAnonymously) {
				builder.append(" -a");
			}

			player.sendChatMessage(builder.toString());
		}
	}
}
