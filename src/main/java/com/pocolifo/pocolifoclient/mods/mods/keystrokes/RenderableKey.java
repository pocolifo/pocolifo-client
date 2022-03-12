package com.pocolifo.pocolifoclient.mods.mods.keystrokes;

import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.util.Fonts;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;

@Getter
public class RenderableKey {
	private final KeyBinding keyBinding;
	private final KeystrokesMod mod;
	private final int gridX;
	private final int gridY;

	private final String character;

	public RenderableKey(KeyBinding keyBinding, KeystrokesMod mod, int gridX, int gridY) {
		this.keyBinding = keyBinding;
		this.mod = mod;
		this.gridX = gridX;
		this.gridY = gridY;

		this.character = Keyboard.getKeyName(keyBinding.getKeyCode());
	}

	public void render() {
		float renderX = mod.getPosition().getRenderX() + gridX * (mod.keyWidth + mod.margin);
		float renderY = mod.getPosition().getRenderY() + gridY * (mod.keyHeight + mod.margin);

		Geometry.drawFullRoundedRectangle(renderX, renderY, mod.keyWidth, mod.keyHeight, 5f, 2f, keyBinding.isKeyDown() ? mod.keyBackgroundColorPressed : mod.keyBackgroundColor);
		Fonts.sharp.writeCentered(character, renderX + mod.keyWidth / 2 - mod.margin / 4, renderY + mod.keyHeight / 2, keyBinding.isKeyDown() ? mod.keyTextPressed : mod.keyTextColor);
	}
}
