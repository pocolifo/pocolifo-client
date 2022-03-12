package com.pocolifo.pocolifoclient.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.Objects;

import com.pocolifo.pocolifoclient.PocolifoClient;
import net.superblaubeere27.clientbase.utils.fontRenderer.GlyphPageFontRenderer;

public class Fonts {
	public static GlyphPageFontRenderer regular;
	public static GlyphPageFontRenderer title;
	public static GlyphPageFontRenderer sharp;
	public static GlyphPageFontRenderer updatingText;

	public static GlyphPageFontRenderer getFontRendererFromResources(String resource, int size, boolean bold, boolean italic, boolean boldItalic) throws IOException, FontFormatException {
		return GlyphPageFontRenderer.create(Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(PocolifoClient.class.getResourceAsStream(resource))), size, bold, italic, boldItalic);
	}

	public static void loadFonts() throws IOException, FontFormatException {
		regular = getFontRendererFromResources("/fonts/ibm/IBMPlexSansThaiLooped-Light.ttf", 18, true, true, true);
		title = getFontRendererFromResources("/fonts/montserrat/Montserrat-ExtraBold.ttf", 30, false, false, false);
		sharp = getFontRendererFromResources("/fonts/inter/Inter-Regular.ttf", 20, false, false, false);

		updatingText = getFontRendererFromResources("/fonts/montserrat/Montserrat-ExtraBold.ttf", 70, false, false, false);
	}
}
