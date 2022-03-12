package com.pocolifo.pocolifoclient.render.font;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GlyphContainer {
	protected final char character;
	protected final String characterAsString;
	protected final float width;
	protected final float height;
	protected float x;
	protected float y;
}
