package com.pocolifo.pocolifoclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.GuiMainMenu;

@Mixin(GuiMainMenu.class)
public interface Accessor_GuiMainMenu {
	@Invoker
	void invokeRenderSkybox(int mouseX, int mouseY, float partialTicks);
}
