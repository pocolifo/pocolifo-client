package com.pocolifo.pocolifoclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.resources.I18n;

@Mixin(GuiMainMenu.class)
public class Mixin_Clean_RemoveRealms_GuiMainMenu {
	/**
	 * @author youngermax
	 */
	@Overwrite
	private void addSingleplayerMultiplayerButtons(int startY, int btnHeight) {
		Accessor_GuiScreen gui = (Accessor_GuiScreen) this;

		// move one button space down
		startY += btnHeight;

		gui.getButtonList().add(new GuiButton(1, gui.getWidth() / 2 - 100, startY, I18n.format("menu.singleplayer")));
		gui.getButtonList().add(new GuiButton(2, gui.getWidth() / 2 - 100, startY + btnHeight, I18n.format("menu.multiplayer")));
	}

}
