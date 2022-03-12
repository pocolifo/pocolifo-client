package com.pocolifo.pocolifoclient.render.font;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FontGlyphAtlas {
	public static final int FONT_WIDTH_ADDITION = 5;
	public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	private final Font font;
	private final FontRenderContext fontRenderContext;
	private final char[] characters;
	private final Map<Character, GlyphContainer> glyphContainerMap;

	private BufferedImage atlas;

	protected double charMaxWidth;
	protected double charMaxHeight;
	protected double atlasCellCount;

	public FontGlyphAtlas(Font font, FontRenderContext fontRenderContext, char[] characters) {
		this.font = font;
		this.fontRenderContext = fontRenderContext;
		this.characters = characters;
		this.glyphContainerMap = new HashMap<>();
	}

	private void prepareCanvas() {
		Graphics2D graphics = (Graphics2D) this.atlas.getGraphics();

		graphics.setColor(TRANSPARENT);
		graphics.fillRect(0, 0, this.atlas.getWidth(), this.atlas.getHeight());
		graphics.setColor(Color.WHITE);
		graphics.setFont(this.font);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.fontRenderContext.isAntiAliased() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.fontRenderContext.getAntiAliasingHint());
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fontRenderContext.getFractionalMetricsHint());
	}

	public void load() {
		this.createAtlas();
		this.prepareCanvas();
		this.drawCharacters();
	}

	private void drawCharacters() {
		if (this.characters.length > Math.pow(this.atlasCellCount, 2)) throw new RuntimeException("characters will not fit");

		Graphics2D graphics = (Graphics2D) this.atlas.getGraphics();
		graphics.setFont(this.font);

		List<GlyphContainer> glyphs = new LinkedList<>(this.glyphContainerMap.values());

		int i = 0;
		for (int y = 1; this.atlasCellCount > y; y++) {
			for (int x = 1; this.atlasCellCount > x; x++) {
				if (i >= glyphs.size()) continue;
				this.drawCharacter(graphics, x, y, glyphs.get(i));
				i++;
			}
		}
	}

	private void drawCharacter(Graphics2D graphics, int cellX, int cellY, GlyphContainer glyph) {
		GlyphVector glyphVector = this.font.createGlyphVector(this.fontRenderContext, glyph.characterAsString);

		glyph.x = (float) (cellX * this.charMaxWidth);
		glyph.y = (float) (cellY * this.charMaxHeight);

		// FOR DEBUG: graphics.drawRect((int) glyph.x, (int) glyph.y, (int) glyph.width, (int) glyph.height);
		graphics.drawGlyphVector(glyphVector, glyph.x, glyph.y + graphics.getFontMetrics().getAscent());
	}

	private void createAtlas() {
		for (char character : this.characters) {
			String characterAsString = String.valueOf(character);
			Rectangle bounds = this.font.getStringBounds(characterAsString, this.fontRenderContext).getBounds();

			GlyphContainer container = new GlyphContainer(character, characterAsString, (float) bounds.getWidth() + FONT_WIDTH_ADDITION, (float) bounds.getHeight());

			this.glyphContainerMap.put(character, container);

			if (container.width > this.charMaxWidth) this.charMaxWidth = container.width;
			if (container.height > this.charMaxHeight) this.charMaxHeight = container.height;
		}

		// the 5 here is just extra space, it's separate from FONT_WIDTH_ADDITION
		this.charMaxWidth = Math.ceil(this.charMaxWidth) + 5;
		this.charMaxHeight = Math.ceil(this.charMaxHeight) + 5;
		this.atlasCellCount = Math.ceil(Math.sqrt(this.characters.length)) + 2;

		int width = (int) (this.charMaxWidth * this.atlasCellCount);
		int height = (int) (this.charMaxHeight * this.atlasCellCount);

		this.atlas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public void dispose() {
		this.atlas = null;
		this.glyphContainerMap.clear();
	}

	public Map<Character, GlyphContainer> getGlyphContainerMap() {
		return this.glyphContainerMap;
	}

	public BufferedImage getAtlas() {
		return this.atlas;
	}
}

