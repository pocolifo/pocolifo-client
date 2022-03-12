package com.pocolifo.pocolifoclient.mods.gui;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import com.pocolifo.pocolifoclient.mods.gui.settings.GuiModSettings;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.util.Fonts;
import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMoveMods extends GuiScreen {
	private RenderableMod draggingMod;

	private float lastDragX;
	private float lastDragY;

	private GuiButton settingsButton;

	@Override
	public void initGui() {
		this.settingsButton = new GuiButton(1, this.width / 2 - 100, this.height / 2 - 10, "Mod Settings");
		this.buttonList.add(this.settingsButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		boolean alreadyHighlighted = false;

		for (RenderableMod renderableMod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
			if (!renderableMod.isEnabled()) continue;

			float x = renderableMod.getPosition().getRenderX();
			float y = renderableMod.getPosition().getRenderY();
			float width = renderableMod.getWidth();
			float height = renderableMod.getHeight();
			float maxX = x + width;
			float maxY = y + height;

			boolean hovered = mouseX > x && maxX > mouseX && mouseY > y && maxY > mouseY;

			ClientColor renderColor;

			if (!alreadyHighlighted && hovered && (this.draggingMod == null || renderableMod == this.draggingMod)) {
				renderColor = Colors.POCOLIFO_BLUE.color;
				alreadyHighlighted = true;
			} else {
				renderColor = Colors.POCOLIFO_PURPLE.color;
			}

			if (Mouse.isButtonDown(0)) {
				if (this.draggingMod != null) {
					this.draggingMod.getPosition().moveX(mouseX - this.lastDragX);
					this.draggingMod.getPosition().moveY(mouseY - this.lastDragY);
				}

				if (hovered && this.draggingMod == null) {
					this.draggingMod = renderableMod;
				}
			} else {
				this.draggingMod = null;
			}

			Geometry.drawFullRectangle(x, y, width, height, new ClientColor(renderColor.red, renderColor.green, renderColor.blue, 0.25f));
			Geometry.drawLinedRectangle(x, y, width, height, 0.5f, renderColor);

			Fonts.sharp.write(renderableMod.getModName(), x, y + height, Colors.WHITE.color);

			this.lastDragX = mouseX;
			this.lastDragY = mouseY;
		}

		// todo alpha2
//		Geometry.drawRoundedRectangle(10d, 10d, 10d, 10d, 3f, 4f, Colors.WHITE.color);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void actionPerformed(GuiButton guiButton) {
		if (guiButton.id == this.settingsButton.id) {
			this.mc.displayGuiScreen(new GuiModSettings());
		}
	}
}
