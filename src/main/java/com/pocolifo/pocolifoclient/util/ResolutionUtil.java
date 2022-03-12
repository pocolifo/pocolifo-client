package com.pocolifo.pocolifoclient.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ResolutionUtil {
	// TODO: clean this up

	public static int getScaledMouseX() {
		return Mouse.getX() * getScaledRes().getScaledWidth() / Display.getWidth();
	}

	public static int getScaledMouseY() {
		return getScaledRes().getScaledHeight() - Mouse.getY() * getScaledRes().getScaledHeight() / Display.getHeight() - 1;
	}

	private static ScaledResolution getScaledRes() {
		return new ScaledResolution(Minecraft.getMinecraft());
	}
}
