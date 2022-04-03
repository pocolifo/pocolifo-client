package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.ModLoader;
import com.pocolifo.pocolifoclient.mods.gui.GuiModPositioning;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

@Mixin(GuiIngame.class)
public class Mixin_Mods_OpenModSettings_GuiIngame {
	@Shadow @Final private Minecraft mc;

	@Inject(method = "renderGameOverlay", at = @At("TAIL"))
	public void openModSettings(float partialTicks, CallbackInfo ci) {
		if (ModLoader.openModMenu.isPressed()) {
			this.mc.displayGuiScreen(new GuiModPositioning());
		}
	}
}
