package com.pocolifo.pocolifoclient.mods.templatemods;

import com.pocolifo.pocolifoclient.mods.AbstractBaseRenderableMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.ColorConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.NumberConfigurationType;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.util.Fonts;

public abstract class AbstractDraggableTextMod extends AbstractBaseRenderableMod {
	@ModConfiguration(value = "horizontal_padding", name = "Horizontal Padding", type = NumberConfigurationType.class)
	private float paddingX = 6f;

	@ModConfiguration(value = "vertical_padding", name = "Vertical Padding", type = NumberConfigurationType.class)
	private float paddingY = 6f;

	@ModConfiguration(value = "background_color", name = "Background Color", type = ColorConfigurationType.class)
	public ClientColor backgroundColor = new ClientColor(0, 0, 0, 0.5f);

	@ModConfiguration(value = "background_roundness", name = "Background Roundness", type = NumberConfigurationType.class)
	public float backgroundRoundness = 10f;

	@ModConfiguration(value = "background_detail", name = "Background Detail", type = NumberConfigurationType.class)
	public float backgroundDetail = 16f;

	@ModConfiguration(value = "text_color", name = "Text Color", type = ColorConfigurationType.class)
	public ClientColor textColor = new ClientColor(1f, 1f, 1f);

	private String lastText;
	private float textX;
	private float textY;

	public AbstractDraggableTextMod(String name, String description) {
		super(name, description);
	}

	@Override
	public void render() {
		String text = this.getText();

		if (text == null) return;

		if (!text.equals(this.lastText)) {
			float textWidth = Fonts.sharp.getStringWidth(text);
			float textHeight = Fonts.sharp.getFontHeight();

			this.width = this.paddingX * 2 + textWidth;
			this.height = this.paddingY * 2 + textHeight;

			this.textX = this.width / 2f - textWidth / 2f;
			this.textY = this.height / 2f - textHeight / 2f;

			this.lastText = text;
		}

		Geometry.drawFullRoundedRectangle(this.getPosition().getRenderX(), this.getPosition().getRenderY(), this.width, this.height, this.backgroundRoundness, this.backgroundDetail, this.backgroundColor);
		Fonts.sharp.write(text, this.getPosition().getRenderX() + this.textX, this.getPosition().getRenderY() + this.textY, this.textColor);
	}

	protected abstract String getText();
}
