package com.pocolifo.pocolifoclient.ui.impl;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.ui.api.TextComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.superblaubeere27.clientbase.utils.fontRenderer.GlyphPageFontRenderer;

@AllArgsConstructor
public class LabelComponent implements Component, TextComponent {
	@Getter @Setter protected float x;
	@Getter @Setter protected float y;
	@Getter @Setter protected ClientColor textColor;
	@Getter @Setter protected String text;
	@Getter @Setter protected boolean textShadow;

	@Getter @Setter protected GlyphPageFontRenderer fontRenderer;
	@Getter @Setter protected boolean centered;

	@Override
	public void render() {
		if (centered) {
			fontRenderer.writeCentered(text, x, y, textColor, textShadow);
		} else {
			fontRenderer.write(text, x, y, textColor, textShadow);
		}
	}
}
