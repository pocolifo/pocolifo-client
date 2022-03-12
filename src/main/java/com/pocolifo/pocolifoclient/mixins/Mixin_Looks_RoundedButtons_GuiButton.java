package com.pocolifo.pocolifoclient.mixins;

import java.awt.Color;

import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.util.Fonts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

@Mixin(GuiButton.class)
public abstract class Mixin_Looks_RoundedButtons_GuiButton {
	private static final ClientColor NORMAL = new ClientColor(0, 0, 0, 0.7f);
	private static final ClientColor HOVERING = new ClientColor(0, 0, 0, 0.5f);

	@Shadow public boolean visible;
	@Shadow public int xPosition;
	@Shadow public int yPosition;
	@Shadow protected int width;
	@Shadow protected int height;
	@Shadow protected boolean hovered;
	@Shadow public String displayString;

	@Shadow protected abstract void mouseDragged(Minecraft minecraft, int i, int i1);

	/**
	 * @author youngermax
	 */
	@Overwrite
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width
					&& mouseY < this.yPosition + this.height;

			Geometry.drawFullRoundedRectangle(this.xPosition, this.yPosition, this.width, this.height, 6f, 32f, hovered ? HOVERING : NORMAL);
			this.mouseDragged(mc, mouseX, mouseY);

			Fonts.regular.writeCentered(this.displayString, this.xPosition + this.width / 2f, this.yPosition + this.height / 2f, new ClientColor(Color.WHITE));
		}
	}
}
