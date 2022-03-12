package com.pocolifo.pocolifoclient.mods.gui.settings;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.mods.Mod;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.render.geometry.Geometry;
import com.pocolifo.pocolifoclient.ui.api.Component;
import com.pocolifo.pocolifoclient.ui.impl.ButtonComponent;
import com.pocolifo.pocolifoclient.ui.impl.LabelComponent;
import com.pocolifo.pocolifoclient.ui.impl.ScrollableContainerComponent;
import com.pocolifo.pocolifoclient.util.Fonts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiModSettings extends GuiScreen {
	public static final ClientColor BACKGROUND = new ClientColor(0f, 0f, 0f, 0.5f);
	public static final ClientColor ENABLED = new ClientColor(0f, 0.6f, 0f, 0.5f);
	public static final ClientColor DISABLED = new ClientColor(0.6f, 0f, 0f, 0.5f);

	public ModListingContainer modListingContainer;
	float margin = 10f;

	@Override
	public void initGui() {
		this.createContainer();
	}

	@Override
	public void onResize(Minecraft minecraft, int width, int height) {
		super.onResize(minecraft, width, height);
		this.createContainer();
	}

	private void createContainer() {
		float boxX = this.width / 4f;
		float boxY = this.height / 4f;
		float w = boxX * 2 / 4;

		float modsPerRow = 3;
		float margin = 5f;

		float add = boxX / 2f - (w + (modsPerRow * margin)) / 2f;

		this.modListingContainer = new ModListingContainer(boxX, boxY + 30, boxX * 2, boxY * 2 - 30 - margin, Colors.TRANSPARENT.color);

		int rx = 0;
		int ry = 0;
		for (Mod mod : PocolifoClient.getInstance().getModLoader().getMods()) {
			this.modListingContainer.getComponents().add(
					new ModListingComponent(
							mod.getModName(),
							add + rx * (w + margin),
							ry * (w + margin),
							w, w,
							mod.isEnabled() ? ENABLED : DISABLED,
							Colors.WHITE.color,
							mod
					));

			rx++;
			rx %= modsPerRow;

			if (rx == 0) ry++;
		}

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		float boxX = this.width / 4f;
		float boxY = this.height / 4f;

		Geometry.drawFullRoundedRectangle(boxX, boxY, boxX * 2, boxY * 2, 10f, 10f, BACKGROUND);

		this.modListingContainer.render();

		Fonts.title.write("MOD SETTINGS", this.width / 4f + margin, this.height / 4f + margin, Colors.WHITE.color);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
