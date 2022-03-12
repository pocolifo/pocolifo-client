package com.pocolifo.pocolifoclient.splash;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;

public class DefaultSplashScreen extends AbstractSplashScreen {
	private boolean renderedAlready = false;
	private ResourceLocation splashImage;

	public DefaultSplashScreen() {
		this.splashImage = new ResourceLocation("pmc/splash/s" + (ThreadLocalRandom.current().nextInt(1, 5) + 1) + ".png");
	}

	@Override
	protected void renderElements() {
		drawBackground();
	}

	private void drawBackground() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.splashImage);
		int imgWidth = 1920;
		int imgHeight = 1017;

		int scaledWidth = Math.max(displayWidth, imgWidth);

		int x = displayWidth / 2 - scaledWidth / 2;
		int y = 0;

//		Gui.drawScaledCustomSizeModalRect(, 0, 0, width, height, width, height, width, height);

		Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, scaledWidth, displayHeight, scaledWidth, displayHeight, scaledWidth, displayHeight);

		GlStateManager.bindTexture(0);
		GlStateManager.color(1f, 1f, 1f, 1f);
	}

	@Override
	public boolean keepRendering() {
		if (renderedAlready) {
			return false;
		} else {
			renderedAlready = true;
			return true;
		}
	}
}
