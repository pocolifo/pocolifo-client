package com.pocolifo.pocolifoclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.settings.GameSettings;

@Mixin(GameSettings.class)
public class Mixin_Perf_OptimizedSettings_GameSettings {
	@Shadow public boolean useVbo;

	@Shadow public int clouds;

	@Shadow public boolean enableVsync;

	@Shadow public int renderDistanceChunks;

	@Shadow public int guiScale;

	@Shadow public boolean fancyGraphics;

	@Shadow public int limitFramerate;

	@Shadow public float gammaSetting;

	@Inject(method = "loadOptions", at = @At("HEAD"))
	public void loadOptimizedSettings(CallbackInfo ci) {
		this.useVbo = true;
		this.clouds = 0; // clouds off
		this.enableVsync = false; // fps
		this.renderDistanceChunks = 10; // 10 is default for servers
		this.guiScale = 2; // normal
		this.fancyGraphics = false;
		this.limitFramerate = 260; // 260 is unlimited
		this.gammaSetting = 1.0f; // brightness max
	}
}
