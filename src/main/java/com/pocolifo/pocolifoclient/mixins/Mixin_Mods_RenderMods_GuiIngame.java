package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class Mixin_Mods_RenderMods_GuiIngame {
	@Inject(method = "renderGameOverlay", at = @At("TAIL"))
	public void renderMods(float partialTicks, CallbackInfo ci) {
		for (RenderableMod renderableMod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
			if (renderableMod.isEnabled()) renderableMod.render();
		}
	}
}
