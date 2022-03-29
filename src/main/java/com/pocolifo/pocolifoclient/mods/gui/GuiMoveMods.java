package com.pocolifo.pocolifoclient.mods.gui;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.RenderableMod;
import com.pocolifo.pocolifoclient.mods.gui.settings.GuiModSettings;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.impl.ButtonComponent;
import com.pocolifo.pocolifoclient.util.Fonts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class GuiMoveMods extends GuiScreen {
	public static final int SCALE_BOX_SIZE = 6;

	private RenderableMod draggingMod;
	private int draggingCorner = -1;

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
		boolean hoveredAny = false;

		for (RenderableMod renderableMod : PocolifoClient.getInstance().getModLoader().getRenderableMods()) {
			if (!renderableMod.isEnabled()) continue;

			float x = renderableMod.getPosition().getRenderX();
			float y = renderableMod.getPosition().getRenderY();
			float width = renderableMod.getWidth() * renderableMod.getPosition().getScale();
			float height = renderableMod.getHeight() * renderableMod.getPosition().getScale();
			float maxX = x + width;
			float maxY = y + height;

			boolean hovered = mouseX > x && maxX > mouseX && mouseY > y && maxY > mouseY;

			if (hovered && !hoveredAny) hoveredAny = true;

			ClientColor renderColor;

			if (!alreadyHighlighted && hovered && (this.draggingMod == null || renderableMod == this.draggingMod)) {
				renderColor = Colors.POCOLIFO_BLUE.color;
				alreadyHighlighted = true;
			} else {
				renderColor = Colors.POCOLIFO_PURPLE.color;
			}

			if (Mouse.isButtonDown(0)) {
				if (this.draggingMod != null) {
					if (draggingCorner != -1) {
						this.draggingMod.getPosition().changeScale((mouseX - this.lastDragX) / 100);
					} else {
						this.draggingMod.getPosition().moveX(mouseX - this.lastDragX);
						this.draggingMod.getPosition().moveY(mouseY - this.lastDragY);
					}
				}

				if (hovered && this.draggingMod == null) {
					this.draggingMod = renderableMod;
				}
			} else {
				this.draggingMod = null;
				this.draggingCorner = -1;
			}

			Geometry.drawFullRectangle(x, y, width, height, new ClientColor(renderColor.red, renderColor.green, renderColor.blue, 0.25f));
			Geometry.drawLinedRectangle(x, y, width, height, 0.5f, renderColor);

			if (hovered) {
				// top left
				if (mouseX > x && x + SCALE_BOX_SIZE > mouseX && mouseY > y && y + SCALE_BOX_SIZE > mouseY) {
					draggingCorner = 1;
				}

				Geometry.drawFullRectangle(x, y, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

				// top right
				if (mouseX > x + width - SCALE_BOX_SIZE && x + width > mouseX && mouseY > y && mouseY > y && y + SCALE_BOX_SIZE > mouseY) {
					draggingCorner = 2;
				}

				Geometry.drawFullRectangle(x + width - SCALE_BOX_SIZE, y, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

				// bottom left
				if (mouseX > x && x + SCALE_BOX_SIZE > mouseX && mouseY > y + height - SCALE_BOX_SIZE && y + height > mouseY) {
					draggingCorner = 3;
				}

				Geometry.drawFullRectangle(x, y + height - SCALE_BOX_SIZE, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);

				// bottom right
				if (mouseX > x + width - SCALE_BOX_SIZE && x + width > mouseX && mouseY > y + height - SCALE_BOX_SIZE && y + height > mouseY) {
					draggingCorner = 4;
				}

				Geometry.drawFullRectangle(x + width - SCALE_BOX_SIZE, y + height - SCALE_BOX_SIZE, SCALE_BOX_SIZE, SCALE_BOX_SIZE, Colors.WHITE.color);
			}

			Fonts.sharp.write(renderableMod.getModName(), x, y + height, Colors.WHITE.color);

			this.lastDragX = mouseX;
			this.lastDragY = mouseY;
		}

		Fonts.regular.write(draggingCorner + "", 0f, 0f, Colors.WHITE.color);

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
