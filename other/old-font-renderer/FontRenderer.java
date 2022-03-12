package com.pocolifo.pocolifoclient.render.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.pocolifo.pocolifoclient.render.ClientColor;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class FontRenderer {
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789`1234567890-=[]\\;'\",./~!@#$%^&*()_+{}|:<>?".toCharArray();

	private final FontRenderContext fontRenderContext;
	private final int fontSize;
	private final Font font;

	private Font fontPlain;
	private Font fontBold;
	private Font fontItalics;
	private Font fontBoldItalics;

	private GlyphAtlasRenderer plain;
	private GlyphAtlasRenderer bold;
	private GlyphAtlasRenderer italics;
	private GlyphAtlasRenderer boldItalics;

	private ScaledResolution resolution;
	private int scaleFactor;
	private int scaleDivide;

	private int startChar;
	private int endChar;

	public FontRenderer(Font font, boolean antialias, boolean fractionalMetrics, int size) {
		this.fontRenderContext = new FontRenderContext(new AffineTransform(), antialias, fractionalMetrics);
		this.fontSize = size;
		this.font = font;

		this.setFonts(font, size * this.scaleDivide);
	}

	private void setFonts(Font font, int size) {
		this.fontPlain = font.deriveFont(Font.PLAIN, size);
		this.fontBold = font.deriveFont(Font.BOLD, size);
		this.fontItalics = font.deriveFont(Font.ITALIC, size);
		this.fontBoldItalics = font.deriveFont(Font.BOLD | Font.ITALIC, size);
	}

	private void setGlyphRendererFonts() {
		this.plain.setFont(this.fontPlain);
		this.bold.setFont(this.fontBold);
		this.italics.setFont(this.fontItalics);
		this.boldItalics.setFont(this.fontBoldItalics);
	}

	public void createGlyphAtlases() {
		if (1 > this.scaleDivide) throw new RuntimeException("called too early");

		if (this.plain.isCreated() || this.bold.isCreated() || this.italics.isCreated() || this.boldItalics.isCreated()) {
			this.dispose();
		}

		this.plain.create(this.scaleDivide);
		this.bold.create(this.scaleDivide);
		this.italics.create(this.scaleDivide);
		this.boldItalics.create(this.scaleDivide);
	}

	public void useCharacters(int startChar, int endChar) {
		this.startChar = startChar;
		this.endChar = endChar;
	}

	public void load() {
		this.plain = new GlyphAtlasRenderer(this.fontPlain, this.fontRenderContext);
		this.bold = new GlyphAtlasRenderer(this.fontBold, this.fontRenderContext);
		this.italics = new GlyphAtlasRenderer(this.fontItalics, this.fontRenderContext);
		this.boldItalics = new GlyphAtlasRenderer(this.fontBoldItalics, this.fontRenderContext);

		this.plain.createCharacters(this.startChar, this.endChar);
		this.bold.createCharacters(this.startChar, this.endChar);
		this.italics.createCharacters(this.startChar, this.endChar);
		this.boldItalics.createCharacters(this.startChar, this.endChar);

		this.setGlyphRendererFonts();
	}

	public void writeColorCodes(String string, float x, float y, ClientColor resetColor) {
		this.update();

		GlyphAtlasRenderer currentRenderer = this.plain;
		ChatCodes color = ChatCodes.RESET;
		boolean obfuscated = false;
		boolean bold = false;
		boolean strikethrough = false;
		boolean underline = false;
		boolean italics = false;

		String[] split = string.split(ChatCodes.SPECIAL_CHAR);
		for (int i = 0; split.length > i; i++) {
			String segment = split[i];

			if (i > 0) {
				// get code
				char code = segment.charAt(0);
				ChatCodes cc = ChatCodes.getByCode(code);

				if (cc == null) {
					// invalid code, add special character and rest of segment back
					segment = ChatCodes.SPECIAL_CHAR + segment;
				} else {
					// valid code, update
					segment = segment.substring(1);

					if (cc.isColor()) {
						color = cc;
					} else if (cc == ChatCodes.RESET) {
						color = ChatCodes.RESET;
						obfuscated = false;
						bold = false;
						strikethrough = false;
						underline = false;
						italics = false;
					} else {
						switch (cc) {
							case OBFUSCATED:
								obfuscated = true;
								break;

							case BOLD:
								bold = true;
								break;

							case STRIKETHROUGH:
								strikethrough = true;
								break;

							case UNDERLINE:
								underline = true;
								break;

							case ITALICS:
								italics = true;
								break;
						}
					}
				}
			}

			if (color == ChatCodes.RESET) {
				currentRenderer = this.plain;
			} else if (bold && !italics) {
				currentRenderer = this.bold;
			} else if (!bold && italics) {
				currentRenderer = this.italics;
			} else if (bold) { // no need for italics as it's checked above
				currentRenderer = this.boldItalics;
			}

			if (obfuscated) {
				StringBuilder newSegment = new StringBuilder();

				for (int i2 = 0; segment.length() > i2; i2++) {
					// fast random
					int random = (int) (System.nanoTime() % currentRenderer.loadedCharacters.length);
					newSegment.append(currentRenderer.loadedCharacters[random]);
				}

				segment = newSegment.toString();
			}

			float stringWidth = currentRenderer.getStringWidth(segment);

			if (strikethrough || underline) {
				float stringHeight = currentRenderer.getStringHeight(segment);

				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldRenderer = tessellator.getWorldRenderer();
				GlStateManager.disableTexture2D();
				float lineSize = stringHeight / 16;

				if (strikethrough) {
					float middle = stringHeight / 2;

					worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
					worldRenderer.pos(x, y + middle, 0.0D).endVertex();
					worldRenderer.pos(x + stringWidth, y + middle, 0.0D).endVertex();
					worldRenderer.pos(x + stringWidth, y + middle - lineSize, 0.0D).endVertex();
					worldRenderer.pos(x, y + middle - lineSize, 0.0D).endVertex();
					tessellator.draw();
				}

				if (underline) {
					worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
					worldRenderer.pos(x, y + stringHeight, 0.0D).endVertex();
					worldRenderer.pos(x + stringWidth, y + stringHeight, 0.0D).endVertex();
					worldRenderer.pos(x + stringWidth, y + stringHeight - lineSize, 0.0D).endVertex();
					worldRenderer.pos(x, y + stringHeight - lineSize, 0.0D).endVertex();
					tessellator.draw();
				}
			}

			GlStateManager.enableTexture2D();

			ClientColor drawColor = color == ChatCodes.RESET ? resetColor : color.getColor();
			currentRenderer.write(segment, x, y, drawColor);
			x += stringWidth;
		}
	}

	public void write(String string, float x, float y, ClientColor color) {
		this.update();
		this.plain.write(string, x, y, color);
	}

	public void writeCentered(String string, float x, float y, ClientColor color) {
		this.update();
		this.plain.write(string, x - this.getStringWidth(string) / 2, y - this.getStringHeight(string) / 2, color);
	}

	public void writeColorCodesCentered(String string, float x, float y, ClientColor resetColor) {
		this.update();
		this.writeColorCodes(string, x - this.getStringWidth(string) / 2, y - this.getStringHeight(string) / 2, resetColor);
	}

	public float getStringHeight(String string) {
		return this.plain.getStringHeight(string);
	}

	public float getStringWidth(String string) {
		return this.plain.getStringWidth(string);
	}

	public void loadTextures() {
		this.plain.loadTexture();
		this.bold.loadTexture();
		this.italics.loadTexture();
		this.boldItalics.loadTexture();
	}

	public void dispose() {
		this.plain.atlas.dispose();
		this.bold.atlas.dispose();
		this.italics.atlas.dispose();
	}

	public void update() {
		this.resolution = new ScaledResolution(Minecraft.getMinecraft());

		if (this.resolution.getScaleFactor() != this.scaleFactor) {
			this.scaleDivide = this.resolution.getScaleFactor();
			int size = this.fontSize * this.resolution.getScaleFactor() / 2;
			this.setFonts(this.font, size);
			this.setGlyphRendererFonts();
			this.createGlyphAtlases();
			this.loadTextures();
		}

		this.scaleFactor = this.resolution.getScaleFactor();
	}
}
