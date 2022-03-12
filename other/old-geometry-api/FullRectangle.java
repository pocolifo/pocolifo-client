//package com.pocolifo.pocolifoclient.render.geometry;
//
//import com.pocolifo.pocolifoclient.render.ClientColor;
//import com.pocolifo.pocolifoclient.render.Shape;
//import org.lwjgl.opengl.GL11;
//
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.WorldRenderer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//
//public class FullRectangle extends Shape {
//	public float width;
//	public float height;
//	public ClientColor color;
//
//	public FullRectangle(float x, float y, float width, float height, ClientColor color) {
//		super(x, y);
//
//		this.width = width;
//		this.height = height;
//		this.color = color;
//	}
//
//	@Override
//	public void render() {
//		Tessellator tessellator = Tessellator.getInstance();
//		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
//		enable2D();
//		this.color.setCurrentColor();
//
//		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
//		worldRenderer.pos(this.x, this.y + this.height, 0.0D).endVertex();
//		worldRenderer.pos(this.x + this.width, this.y + this.height, 0.0D).endVertex();
//		worldRenderer.pos(this.x + this.width, this.y, 0.0D).endVertex();
//		worldRenderer.pos(this.x, this.y, 0.0D).endVertex();
//
//		tessellator.draw();
//		disable2D();
//	}
//}
