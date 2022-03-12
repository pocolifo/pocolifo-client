//package com.pocolifo.pocolifoclient.render.geometry;
//
//import com.pocolifo.pocolifoclient.render.ClientColor;
//import com.pocolifo.pocolifoclient.render.DetailedShape;
//import org.lwjgl.opengl.GL11;
//
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.WorldRenderer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//
//public class FullOval extends DetailedShape {
//	private float height;
//	private float width;
//
//	public ClientColor color;
//	public float degrees;
//	public double rotationRadians;
//
//	public FullOval(float x, float y, int detail, float width, float height, ClientColor color) {
//		super(x, y, detail);
//
//		this.width = width;
//		this.height = height;
//		this.color = color;
//		this.degrees = 360f;
//	}
//
//	@Override
//	public static void render(float x, float y, float width, float height, float detail, ClientColor color) {
//		Tessellator tessellator = Tessellator.getInstance();
//		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
//		this.color.setCurrentColor();
//		enable2D();
//		worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
//
//		worldRenderer.pos(.x, this.y, 0.0D).endVertex();
//
//		for (int i = 0; this.detail >= i; i++) {
//			double angle = Math.toRadians(i * this.degrees / this.detail) + this.rotationRadians;
//
//			worldRenderer.pos(
//					this.x + Math.sin(angle) * this.width / 2,
//					this.y + Math.cos(angle) * this.height / 2,
//					0).endVertex();
//		}
//
//		tessellator.draw();
//		disable2D();
//	}
//}
