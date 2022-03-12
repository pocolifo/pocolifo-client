package com.pocolifo.pocolifoclient.mods.mods.fpsdisplay;

import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;

import net.minecraft.client.Minecraft;

public class FPSDisplayMod extends AbstractDraggableTextMod {
	public FPSDisplayMod() {
		super("FPS Display", "Displays your Frames Per Second");
	}

	@Override
	protected String getText() {
		return Minecraft.getDebugFPS() + " FPS";
	}
}
