package com.pocolifo.pocolifoclient.mods.mods.keystrokes;

import java.util.ArrayList;
import java.util.List;

import com.pocolifo.pocolifoclient.mods.AbstractBaseRenderableMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.ColorConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.NumberConfigurationType;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;

import net.minecraft.client.Minecraft;

public class KeystrokesMod extends AbstractBaseRenderableMod {
	@ModConfiguration(value = "key_bg_clr", name = "Key Background Color", type = ColorConfigurationType.class)
	public ClientColor keyBackgroundColor = new ClientColor(0, 0, 0, 0.5f);

	@ModConfiguration(value = "key_bg_clr_pressed", name = "Key Background Color (Pressed)", type = ColorConfigurationType.class)
	public ClientColor keyBackgroundColorPressed = new ClientColor(255, 255, 255, 0.5f);

	@ModConfiguration(value = "key_tx_clr", name = "Key Text Color", type = ColorConfigurationType.class)
	public ClientColor keyTextColor = Colors.WHITE.color;

	@ModConfiguration(value = "key_tx_clr_pressed", name = "Key Text Color (Pressed)", type = ColorConfigurationType.class)
	public ClientColor keyTextPressed = Colors.BLACK.color;;

	@ModConfiguration(value = "key_width", name = "Key Width", type = NumberConfigurationType.class)
	public float keyWidth = 32;

	@ModConfiguration(value = "key_height", name = "Key Height", type = NumberConfigurationType.class)
	public float keyHeight = 32;

	@ModConfiguration(value = "margin", name = "Margin", type = NumberConfigurationType.class)
	public float margin = 4;

	private final List<RenderableKey> keys;

	public KeystrokesMod() {
		super("Keystrokes", "Flex which keys you're pressing");

		this.keys = new ArrayList<>();

		this.keys.add(new RenderableKey(Minecraft.getMinecraft().gameSettings.keyBindForward, this, 1, 0));
		this.keys.add(new RenderableKey(Minecraft.getMinecraft().gameSettings.keyBindLeft, this, 0, 1));
		this.keys.add(new RenderableKey(Minecraft.getMinecraft().gameSettings.keyBindBack, this, 1, 1));
		this.keys.add(new RenderableKey(Minecraft.getMinecraft().gameSettings.keyBindRight, this, 2, 1));
	}

	@Override
	public void render() {
		this.width = (keyWidth + margin) * 3 - margin; // 3 columns
		this.height = (keyHeight + margin) * 2 - margin; // 2 rows

		for (RenderableKey key : keys) {
			key.render();
		}
	}
}
