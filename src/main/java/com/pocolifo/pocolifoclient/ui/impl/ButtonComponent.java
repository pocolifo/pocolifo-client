package com.pocolifo.pocolifoclient.ui.impl;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.api.TextComponent;
import com.pocolifo.pocolifoclient.util.Fonts;
import com.pocolifo.pocolifoclient.util.GlUtil;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;
import static com.pocolifo.pocolifoclient.util.ResolutionUtil.getScaledMouseX;
import static com.pocolifo.pocolifoclient.util.ResolutionUtil.getScaledMouseY;

public class ButtonComponent extends RectangleComponent implements TextComponent {
	@Getter @Setter protected String text;
	@Getter @Setter protected ClientColor textColor;
	@Getter @Setter protected boolean textShadow;

	public ButtonComponent(String text, float x, float y, float width, float height, ClientColor color, ClientColor textColor) {
		super(x, y, width, height, color);
		this.text = text;
		this.textColor = textColor;
	}

	@Override
	public void render() {
		Geometry.drawFullRoundedRectangle(x, y, width, height, 4f, 4f, isHovering() ? color.darkerBy(0.15f) : color);
		Fonts.sharp.writeCentered(text, x + width / 2f, y + height / 2f, textColor, false);
	}
}
