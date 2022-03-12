package com.pocolifo.pocolifoclient;

import java.util.ArrayList;
import java.util.List;

import com.pocolifo.pocolifoclient.launch.BuildProperties;
import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import com.pocolifo.pocolifoclient.mods.Mod;
import com.pocolifo.pocolifoclient.mods.ModLoader;
import com.pocolifo.pocolifoclient.mods.mods.armorstatus.ArmorStatusMod;
import com.pocolifo.pocolifoclient.mods.mods.autogg.AutoGGMod;
import com.pocolifo.pocolifoclient.mods.mods.autotip.AutoTipMod;
import com.pocolifo.pocolifoclient.mods.mods.cpsdisplay.CPSDisplay;
import com.pocolifo.pocolifoclient.mods.mods.currentserver.CurrentServerMod;
import com.pocolifo.pocolifoclient.mods.mods.discord.DiscordMod;
import com.pocolifo.pocolifoclient.mods.mods.fpsdisplay.FPSDisplayMod;
import com.pocolifo.pocolifoclient.mods.mods.itemphysics.ItemPhysicsMod;
import com.pocolifo.pocolifoclient.mods.mods.keystrokes.KeystrokesMod;
import com.pocolifo.pocolifoclient.mods.mods.oldanimations.OldAnimationsMod;
import com.pocolifo.pocolifoclient.mods.mods.performance.PerformanceMod;
import com.pocolifo.pocolifoclient.mods.mods.position.PositionMod;
import com.pocolifo.pocolifoclient.mods.mods.potionstatus.PotionStatusMod;
import com.pocolifo.pocolifoclient.mods.mods.systemtime.SystemTimeMod;
import com.pocolifo.pocolifoclient.mods.mods.togglesprint.ToggleSprintMod;
import com.pocolifo.pocolifoclient.util.serverid.ServerIdentifier;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class PocolifoClient {
	@Getter
	private static PocolifoClient instance;

	@Getter
	private ModLoader modLoader;

	public static void init() {
		if (instance != null) throw new RuntimeException("already initialized");
		System.out.printf("INFO: Running %s %s%n", BuildProperties.NAME, BuildProperties.VERSION);

		ServerIdentifier.init();

		instance = new PocolifoClient();
	}

	public PocolifoClient() {
		this.initModLoader();
		this.initKeybinds();
	}

	private void initKeybinds() {
		List<KeyBinding> keyBindingList = new ArrayList<>();

		keyBindingList.add(ModLoader.openModMenu);

		for (Mod mod : this.getModLoader().getMods()) {
			keyBindingList.addAll(mod.getKeybindings());
		}

		Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.addAll(Minecraft.getMinecraft().gameSettings.keyBindings, keyBindingList.toArray(new KeyBinding[0]));
	}

	private void initModLoader() {
		this.modLoader = new ModLoader();

		this.modLoader.registerMod(InstanceHolder.discordMod = new DiscordMod());
		this.modLoader.registerMod(InstanceHolder.oldAnimationsMod = new OldAnimationsMod());
		this.modLoader.registerMod(InstanceHolder.armorStatusMod = new ArmorStatusMod());
		this.modLoader.registerMod(InstanceHolder.fpsDisplayMod = new FPSDisplayMod());
		this.modLoader.registerMod(InstanceHolder.cpsDisplayMod = new CPSDisplay());
		this.modLoader.registerMod(InstanceHolder.systemTimeMod = new SystemTimeMod());
		this.modLoader.registerMod(InstanceHolder.potionStatusMod = new PotionStatusMod());
		this.modLoader.registerMod(InstanceHolder.toggleSprintMod = new ToggleSprintMod());
		this.modLoader.registerMod(InstanceHolder.positionMod = new PositionMod());
		this.modLoader.registerMod(InstanceHolder.currentServerMod = new CurrentServerMod());
		this.modLoader.registerMod(InstanceHolder.autoGGMod = new AutoGGMod());
		this.modLoader.registerMod(InstanceHolder.autoTipMod = new AutoTipMod());
		this.modLoader.registerMod(InstanceHolder.performanceMod = new PerformanceMod());
		this.modLoader.registerMod(InstanceHolder.itemPhysicsMod = new ItemPhysicsMod());
		this.modLoader.registerMod(InstanceHolder.keystrokesMod = new KeystrokesMod());
	}

	public void shutdown() {
		this.modLoader.unloadAll();
		instance = null;
	}
}
