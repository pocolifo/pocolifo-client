package com.pocolifo.pocolifoclient.mixins;

import java.awt.Color;

import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.launch.BuildProperties;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.render.Colors;
import com.pocolifo.pocolifoclient.util.Fonts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiMainMenu.class)
public abstract class Mixin_Looks_RedesignedMenu_GuiMainMenu {
	@Shadow protected abstract void renderSkybox(int mouseX, int mouseY, float partialTicks);
	@Shadow protected abstract boolean func_183501_a();
	@Shadow private GuiScreen field_183503_M;

	/**
	 * @author youngermax
	 */
	@Overwrite
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GlStateManager.disableAlpha();
		this.renderSkybox(mouseX, mouseY, partialTicks);
		GlStateManager.enableAlpha();
		Accessor_GuiScreen guiScreen = (Accessor_GuiScreen) this;
		Accessor_Gui gui = (Accessor_Gui) this;

		gui.invokeDrawGradientRect(0, 0, guiScreen.getWidth(), guiScreen.getHeight(), -2130706433, 16777215);
		gui.invokeDrawGradientRect(0, 0, guiScreen.getWidth(), guiScreen.getHeight(), 0, -2147483648);

		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("pmc/logo/512.png"));

		final int width = 512;
		final int height = 512;
		final int scaledWidth = 128;
		final int scaledHeight = 128;

		int x = guiScreen.getWidth() / 2 - scaledWidth / 2;
		int y = scaledHeight / 2 - Fonts.title.getFontHeight();

		Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, width, height, scaledWidth, scaledHeight, width, height);

		Fonts.title.writeCentered(BuildProperties.NAME.toUpperCase(),
				guiScreen.getWidth() / 2f,
				y + scaledHeight + Fonts.regular.getFontHeight(),
				Colors.WHITE.color, true);

//		Fonts.title.writeCentered(PocolifoClient.NAME,
//				guiScreen.getWidth() / 2f + 1f,
//				guiScreen.getHeight() / 4f + 2f,
//				new ClientColor(Color.GRAY));
//
//		Fonts.title.writeCentered(PocolifoClient.NAME,
//				guiScreen.getWidth() / 2f,
//				guiScreen.getHeight() / 4f,
//				new ClientColor(Color.WHITE));

		String minecraft = BuildProperties.NAME + " " + BuildProperties.VERSION;

		if (Minecraft.getMinecraft().isDemo()) {
			minecraft = minecraft + " Demo";
		}

		Fonts.regular.write(minecraft, 10, guiScreen.getHeight() -
				Fonts.regular.getFontHeight() - 10, new ClientColor(Color.WHITE), true);

		String copyright = "Copyright Mojang AB. Do not distribute!";
		Fonts.regular.write(copyright, guiScreen.getWidth() - Fonts.regular.getStringWidth(copyright) - 10,
				guiScreen.getHeight() - Fonts.regular.getFontHeight() - 10, new ClientColor(Color.WHITE), true);

		int i;

		for(i = 0; i < guiScreen.getButtonList().size(); ++i) {
			guiScreen.getButtonList().get(i).drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
		}

		for(i = 0; i < guiScreen.getLabelList().size(); ++i) {
			guiScreen.getLabelList().get(i).drawLabel(Minecraft.getMinecraft(), mouseX, mouseY);
		}

		if (this.func_183501_a()) {
			this.field_183503_M.drawScreen(mouseX, mouseY, partialTicks);
		}
	}
}
