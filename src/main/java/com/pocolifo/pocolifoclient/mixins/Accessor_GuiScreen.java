package com.pocolifo.pocolifoclient.mixins;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public interface Accessor_GuiScreen {
	@Accessor("width")
	int getWidth();

	@Accessor("height")
	int getHeight();

	@Accessor("buttonList")
	List<GuiButton> getButtonList();

	@Accessor("labelList")
	List<GuiLabel> getLabelList();

	@Accessor("mc")
	Minecraft getMinecraft();
}
