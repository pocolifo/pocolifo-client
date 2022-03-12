package com.pocolifo.pocolifoclient.util;

import net.minecraft.client.renderer.GlStateManager;

public class GlUtil {
	public static double translationX;
	public static double translationY;
	public static double translationZ;

	public static void translate(double x, double y, double z) {
		translationX += x;
		translationY += y;
		translationZ += z;
		GlStateManager.translate(x, y, z);
	}

	public static void resetTranslation() {
		translationX = 0;
		translationY = 0;
		translationZ = 0;
	}
}
