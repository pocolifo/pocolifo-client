package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;

@Mixin(Minecraft.class)
public class Mixin_Mods__Discord__WorldChange_MinecraftMixin {
	@Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;)V", at = @At("HEAD"))
	public void worldChange(WorldClient world, CallbackInfo ci) {
		if (!InstanceHolder.discordMod.isEnabled()) return;

		if (world == null) {
			InstanceHolder.discordMod.usingTitleScreen();
		} else if (Minecraft.getMinecraft().getCurrentServerData() != null) {
			InstanceHolder.discordMod.updateWithServerData(Minecraft.getMinecraft().getCurrentServerData());
		} else {
			InstanceHolder.discordMod.playingSingleplayer();
		}
	}
}
