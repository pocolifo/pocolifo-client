package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class Mixin_Mods_RenderMods_GuiIngame {
	@Inject(method = "renderGameOverlay", at = @At("TAIL"))
	public void renderMods(float partialTicks, CallbackInfo ci) {
		for (RenderableMod renderableMod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
			if (renderableMod.isEnabled()) {
				GlStateManager.pushMatrix();
				GlStateManager.scale(renderableMod.getPosition().getScale(), renderableMod.getPosition().getScale(), 1.0f);

				double div = Math.pow(renderableMod.getPosition().getScale() - 1, -1) + 1; // magic
				GlStateManager.translate(-renderableMod.getPosition().getRenderX() / div, -renderableMod.getPosition().getRenderY() / div, 0);

				renderableMod.render();

				GlStateManager.popMatrix();
			}
		}
	}
}
