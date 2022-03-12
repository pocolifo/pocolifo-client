package com.pocolifo.pocolifoclient.render.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.pocolifo.pocolifoclient.render.ClientColor;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GlyphAtlasRenderer {
	private final FontRenderContext fontRenderContext;
	protected char[] loadedCharacters;
	private DynamicTexture texture;
	protected FontGlyphAtlas atlas;
	private int scaleDivide;
	private boolean created;
	private Font font;

	public GlyphAtlasRenderer(Font font, FontRenderContext fontRenderContext) {
		this.font = font;
		this.fontRenderContext = fontRenderContext;
		this.created = false;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getName() {
		return String.format("font_%s-bold_%s-italics_%s", this.font.getFontName().toLowerCase().replaceAll(" ", "-"),this.font.isBold(), this.font.isItalic());
	}

	public void createCharacters(int startChar, int endChar) {
		this.loadedCharacters = new char[endChar - startChar];

		for (int i = startChar; endChar > i; i++) {
			if (this.font.canDisplay(i)) {
				this.loadedCharacters[i - startChar] = (char) i;
			}
		}
	}

	public void create(int scaleDivide) {
		if (this.loadedCharacters == null) throw new RuntimeException("called too early");

		this.scaleDivide = scaleDivide;

		this.created = true;

		this.atlas = new FontGlyphAtlas(this.font, this.fontRenderContext, this.loadedCharacters);
		this.atlas.load();
//		try {
//			this.writeAtlasToFileSystem();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void loadTexture() {
		if (this.atlas == null) throw new RuntimeException("called too early");

		// TODO: possible memory leak if you call load texture too many times. instead of creating new texture, reupload texture
		this.texture = new DynamicTexture(this.atlas.getAtlas());
	}

	public void write(String string, float x, float y, ClientColor color) {
		this.bind();
		color.setCurrentColor();

		float currentX = 0;

		for (char c : string.toCharArray()) {
			if (!this.atlas.getGlyphContainerMap().containsKey(c)) continue;

			GlyphContainer glyphContainer = this.atlas.getGlyphContainerMap().get(c);
			currentX += this.writeChar(glyphContainer, x + currentX, y);
		}

		this.unbind();
	}

	public float getCharWidth(char c) {
		if (this.atlas.getGlyphContainerMap().containsKey(c)) {
			GlyphContainer glyphContainer = this.atlas.getGlyphContainerMap().get(c);
			return glyphContainer.width - FontGlyphAtlas.FONT_WIDTH_ADDITION;
		}

		return 0f;
	}

	public float getCharHeight(char c) {
		if (this.atlas.getGlyphContainerMap().containsKey(c)) {
			GlyphContainer glyphContainer = this.atlas.getGlyphContainerMap().get(c);
			return glyphContainer.height;
		}

		return 0f;
	}

	public float getStringWidth(String string) {
		float width = 0f;

		for (char c : string.toCharArray()) {
			width += this.getCharWidth(c);
		}

		return width / this.scaleDivide;
	}

	public float getStringHeight(String string) {
		float maxHeight = 0f;

		for (char c : string.toCharArray()) {
			float height = this.getCharHeight(c);

			if (height > maxHeight) maxHeight = height;
		}

		return maxHeight / this.scaleDivide;
	}

	private float writeChar(GlyphContainer c, float drawX, float drawY) {
		float charWidth = c.width - FontGlyphAtlas.FONT_WIDTH_ADDITION;
		float charHeight = c.height;
		float charX = c.x;
		float charY = c.y;

		double glyphWidth = charWidth / Math.floor(this.atlas.getAtlas().getWidth());
		double glyphHeight = charHeight / Math.floor(this.atlas.getAtlas().getHeight());
		double glyphXOnAtlas = charX / Math.floor(this.atlas.getAtlas().getWidth());
		double glyphYOnAtlas = charY / Math.floor(this.atlas.getAtlas().getHeight());

		int x = Math.round(drawX);
		int y = Math.round(drawY);
		int w = Math.round(charWidth / this.scaleDivide);
		int h = Math.round(charHeight / this.scaleDivide);

//		Tessellator tessellator = Tessellator.getInstance();
//		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
//
//		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//
//		worldRenderer.pos(x + w, y, 0).tex(glyphXOnAtlas + glyphWidth, glyphYOnAtlas).endVertex();
//		worldRenderer.pos(x, y, 0).tex(glyphXOnAtlas, glyphYOnAtlas).endVertex();
//		worldRenderer.pos(x, y + h, 0).tex(glyphXOnAtlas, glyphYOnAtlas + glyphHeight).endVertex();
//		worldRenderer.pos(x + w, y + h, 0).tex(glyphXOnAtlas + glyphWidth, glyphYOnAtlas + glyphHeight).endVertex();
//
//		tessellator.draw();

		glBegin(GL_TRIANGLES);

		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas);
		glVertex2f(x + w, y);

		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas);
		glVertex2f(x, y);

		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas + glyphHeight);
		glVertex2f(x, y + h);

		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas + glyphHeight);
		glVertex2f(x, y + h);

		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas + glyphHeight);
		glVertex2f(x + w, y + h);

		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas);
		glVertex2f(x + w, y);

		glEnd();

//		glBegin(GL_TRIANGLES);
//
//		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas);
//		glVertex2d(x + w, y);
//
//		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas);
//		glVertex2d(x, y);
//
//		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas + glyphHeight);
//		glVertex2d(x, y + h);
//
//		glTexCoord2d(glyphXOnAtlas, glyphYOnAtlas + glyphHeight);
//		glVertex2d(x, y + h);
//
//		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas + glyphHeight);
//		glVertex2d(x + w, y + h);
//
//		glTexCoord2d(glyphXOnAtlas + glyphWidth, glyphYOnAtlas);
//		glVertex2d(x + h, y);
//
//
//		glEnd();

		return charWidth / this.scaleDivide;
	}

	public void bind() {
		if (this.texture == null) throw new RuntimeException("called too early");
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		GlStateManager.enableTexture2D();
		GlStateManager.bindTexture(this.texture.getGlTextureId());

		GlStateManager.enableTexture2D();
	}

	public void unbind() {
		GlStateManager.bindTexture(0);
		GlStateManager.popMatrix();
		GlStateManager.color(1f, 1f, 1f, 1f);
	}

	public float getSize() {
		return this.font.getSize();
	}

	private void writeAtlasToFileSystem() throws IOException {
		ImageIO.write(this.atlas.getAtlas(), "png", new File(this.getName() + ".png"));
	}

	public boolean isCreated() {
		return this.created;
	}

	public void dispose() {
		this.atlas.dispose();
		this.created = false;
	}
}
