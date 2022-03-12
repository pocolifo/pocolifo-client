package com.pocolifo.pocolifoclient.mods.mods.position;

import com.pocolifo.pocolifoclient.mods.templatemods.AbstractDraggableTextMod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class PositionMod extends AbstractDraggableTextMod {
	public PositionMod() {
		super("Position", "Where are we?");
	}

	@Override
	protected String getText() {
		int x = (int) Minecraft.getMinecraft().getRenderViewEntity().posX;
		int y = (int) Minecraft.getMinecraft().getRenderViewEntity().posY;
		int z = (int) Minecraft.getMinecraft().getRenderViewEntity().posZ;

		float dirYaw = MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().getRenderViewEntity().rotationYaw);

		if (dirYaw >= 0 && dirYaw <= 90) {
			return "XYZ: " + x + " (-) / " + y + " / " + z + " (+)";
		} else if (dirYaw >= 90 && dirYaw <= 180) {
			return "XYZ: " + x + " (-) / " + y + " / " + z + " (-)";
		} else if (dirYaw >= -180 && dirYaw <= -90) {
			return "XYZ: " + x + " (+) / " + y + " / " + z + " (-)";
		} else if (dirYaw >= -90 && dirYaw <= 0) return "XYZ: " + x + " (+) / " + y + " / " + z + " (+)";

		return "XYZ: " + x + " / " + y + " / " + z;
	}
}
