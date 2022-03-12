package com.pocolifo.pocolifoclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

@Mixin(Gui.class)
public interface Accessor_Gui {
	@Invoker
	void invokeDrawGradientRect(int x, int y, int w, int h, int startColor, int endColor);
}
