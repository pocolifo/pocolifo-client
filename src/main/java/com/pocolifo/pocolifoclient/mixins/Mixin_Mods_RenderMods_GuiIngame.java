package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;

@Mixin(GuiIngame.class)
public abstract class Mixin_Mods_RenderMods_GuiIngame {
	@Inject(method = "renderGameOverlay", at = @At("TAIL"))
	public void renderMods(float partialTicks, CallbackInfo ci) {
		for (RenderableMod renderableMod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
			if (renderableMod.isEnabled()) renderableMod.render();
		}
	}
}
