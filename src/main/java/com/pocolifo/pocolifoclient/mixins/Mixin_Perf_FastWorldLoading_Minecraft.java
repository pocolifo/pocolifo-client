package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class Mixin_Perf_FastWorldLoading_Minecraft {
	@Redirect(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Ljava/lang/System;gc()V"))
	public void shouldCollectGarbage() {
		if (!InstanceHolder.performanceMod.isEnabled() || !InstanceHolder.performanceMod.fastWorldLoading) {
			System.gc();
		}
	}
}
