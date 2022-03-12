package com.pocolifo.pocolifoclient.render.geometry;

import com.pocolifo.pocolifoclient.render.ClientColor;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

public class Geometry {
	public static final double R_180 = Math.toRadians(180f);
	public static final double R_90 = Math.toRadians(90f);
	public static final double R_N90 = Math.toRadians(-90f);

	private static void enable2D() {
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	}

	private static void disable2D() {
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawFullOval(float x, float y, float width, float height, float detail, double rotationRadians, double drawDegrees, ClientColor color) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		color.setCurrentColor();
		enable2D();
		worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);

		worldRenderer.pos(x, y, 0.0D).endVertex();

		for (int i = 0; detail >= i; i++) {
			double angle = Math.toRadians(i * drawDegrees / detail) + rotationRadians;

			// TODO: Doesn't sin go with y and cos with x?
			worldRenderer.pos(
					x + Math.sin(angle) * width / 2,
					y + Math.cos(angle) * height / 2,
					0).endVertex();
		}

		tessellator.draw();
		disable2D();
	}

	public static void drawLinedOval(float x, float y, float width, float height, float detail, double rotationRadians, double drawDegrees, float lineWidth, ClientColor color) {
		enable2D();
		GL11.glLineWidth(lineWidth);

		GL11.glBegin(GL11.GL_LINE_STRIP);

		for (int i = 0; detail >= i; i++) {
			double angle = Math.toRadians(i * drawDegrees / detail) + rotationRadians;
			GL11.glVertex2d(x + Math.cos(angle) * width / 2, y + Math.sin(angle) * height / 2);
		}

		GL11.glEnd();
		disable2D();
	}

	public static void drawFullOval(float x, float y, float width, float height, float detail, ClientColor color) {
		drawFullOval(x, y, width, height, detail, 0d, 360d, color);
	}

	public static void drawFullCircle(float x, float y, float radius, float detail, ClientColor color) {
		drawFullOval(x, y, radius, radius, detail, color);
	}

	public static void drawFullRectangle(float x, float y, float width, float height, ClientColor color) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		enable2D();
		color.setCurrentColor();

		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldRenderer.pos(x, y + height, 0.0D).endVertex();
		worldRenderer.pos(x + width, y + height, 0.0D).endVertex();
		worldRenderer.pos(x + width, y, 0.0D).endVertex();
		worldRenderer.pos(x, y, 0.0D).endVertex();

		tessellator.draw();
		disable2D();
	}

	public static void drawLinedRectangle(float x, float y, float width, float height, float lineWidth, ClientColor color) {
		drawFullRectangle(x, y, width, lineWidth, color); // top
		drawFullRectangle(x, y + height - lineWidth, width, lineWidth, color); // bottom
		drawFullRectangle(x, y, lineWidth, height, color); // left
		drawFullRectangle(x + width - lineWidth, y, lineWidth, height, color); // right
	}

	public static void drawFullRoundedRectangle(float x, float y, float width, float height, float roundness, float detail, ClientColor color) {
		float radius = roundness / 2f;

		// TODO: fix: ovals look weird when roundness > height or roundness > width
		drawFullOval(x + radius, y + radius, roundness, roundness, detail, R_180, 90d, color); // top left
		drawFullOval(x + width - radius, y + radius, roundness, roundness, detail, R_90, 90d, color); // top right
		drawFullOval(x + radius, y + height - radius, roundness, roundness, detail, R_N90, 90d, color); // bottom left
		drawFullOval(x + width - radius, y + height - radius, roundness, roundness, detail, 0d, 90d, color); // bottom right

		drawFullRectangle(x, y + radius, radius, height - roundness, color); // left
		drawFullRectangle(x + width - radius, y + radius, radius, height - roundness, color); // right
		drawFullRectangle(x + radius, y, width - roundness, height, color); // middle
	}

	public static void drawLinedRoundedRectangle(float x, float y, float width, float height, float roundness, float detail, float lineWidth, ClientColor color) {
		float radius = roundness / 2f;

		// TODO: fix: ovals look weird when roundness > height or roundness > width
//		drawLinedOval(x + radius, y + radius, roundness, roundness, detail, R_180, 90d, lineWidth, color); // top left
//
//		drawLinedOval(x + width - radius, y + radius, roundness, roundness, detail, R_N90, 90d, lineWidth, color); // top right
//		drawLinedOval(x + radius, y + height - radius, roundness, roundness, detail, R_90, 90d, lineWidth, color); // bottom left
//		drawLinedOval(x + width - radius, y + height - radius, roundness, roundness, detail, 0d, 90d, lineWidth, color); // bottom right

//		drawLinedRectangle(x, y + radius, radius, height - roundness, lineWidth, color); // left
//		drawLinedRectangle(x + width - radius, y + radius, radius, height - roundness, lineWidth, color); // right
//		drawLinedRectangle(x + radius, y, width - roundness, height, lineWidth, color); // middle
	}

	public static void drawRoundedRectangle(double x, double y, double width, double height, float roundness, float detail, ClientColor color) {
//		enable2D();
//		color.setCurrentColor();
//		GL11.glEnable(GL11.GL_LINE_SMOOTH);
//		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
//
//		for (int i = 0; 90 >= i; i++) {
//			double angle = Math.toRadians(i);
//
//			GL11.glVertex2d(x + Math.sin(angle) * 10, y + Math.cos(angle) * 10);
//		}
//
//		GL11.glVertex2d(x + w);
//
//		GL11.glEnd();
//		GL11.glDisable(GL11.GL_LINE_SMOOTH);
//		disable2D();
	}
}
