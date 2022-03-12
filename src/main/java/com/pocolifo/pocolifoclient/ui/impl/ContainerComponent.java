package com.pocolifo.pocolifoclient.ui.impl;

import java.util.ArrayList;
import java.util.List;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.util.GlUtil;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ContainerComponent extends RectangleComponent {
	@Getter protected List<Component> components;

	public ContainerComponent(float x, float y, float width, float height, ClientColor color) {
		super(x, y, width, height, color);
		this.components = new ArrayList<>();
	}


	@Override
	public void render() {
		super.render();

		GlStateManager.pushMatrix();
		GlUtil.translate(this.x, this.y, 0);

		this.renderContent();

		GlStateManager.popMatrix();
		GlUtil.resetTranslation();
	}

	protected void renderContent() {
		wrapScissor(() -> {
			for (Component component : components) {
				component.render();
			}
		}, (int) x, (int) y, (int) width, (int) height);
	}

	public static void wrapScissor(Runnable runnable, int x, int y, int w, int h) {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);

		GL11.glScissor(
				oglToMCX(x),
				oglToMCY(y, h),
				oglToMCW(w),
				oglToMCH(h)
		);

		runnable.run();

		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}

	public static int oglToMCX(int x) {
		return x * new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
	}

	public static int oglToMCY(int y, int h) {
		return (new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - h - y) * new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
	}

	public static int oglToMCW(int w) {
		return w * new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
	}

	public static int oglToMCH(int h) {
		return h * new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
	}
}
