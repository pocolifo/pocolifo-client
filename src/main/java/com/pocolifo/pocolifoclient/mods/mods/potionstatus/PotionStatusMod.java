package com.pocolifo.pocolifoclient.mods.mods.potionstatus;

import java.awt.Color;

import com.pocolifo.pocolifoclient.mods.AbstractBaseRenderableMod;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.util.Fonts;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionStatusMod extends AbstractBaseRenderableMod {
	public static final ResourceLocation potions = new ResourceLocation("textures/gui/container/inventory.png");
	public static final int PE_ICON_SIZE = 18;

	private ClientColor effectNameColor = new ClientColor(Color.WHITE);
	private ClientColor timeLeftColor = new ClientColor(Color.WHITE);

	public PotionStatusMod() {
		super("Potion Status", "Quickly discover what potion effects you have");
	}

	@Override
	public void render() {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		float y = 0;

		for (PotionEffect potionEffect : player.getActivePotionEffects()) {

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_CURRENT_BIT, GL11.GL_POINTS);

			renderIcon(potionEffect, this.getPosition().getRenderX(), this.getPosition().getRenderY() + y);
			Fonts.sharp.write(getFullName(potionEffect),
					this.getPosition().getRenderX() + PE_ICON_SIZE + 3,
					this.getPosition().getRenderY() + y, this.effectNameColor);

			Fonts.sharp.write(getTimeLeft(potionEffect),
					this.getPosition().getRenderX() + PE_ICON_SIZE + 3,
					this.getPosition().getRenderY() + y + 9, this.timeLeftColor);

			float stringWidth = Fonts.sharp.getStringWidth(getFullName(potionEffect));
			this.width = Math.max(this.width, stringWidth + PE_ICON_SIZE + 3);

			y += PE_ICON_SIZE;
		}

		this.height = y;
	}

	public String getTimeLeft(PotionEffect effect) {
		if (effect.getIsPotionDurationMax()) {
			return "**:**";
		} else {
			int i = effect.getDuration() / 20;
			int j = i / 60;
			i = i % 60;
			return i < 10 ? j + ":0" + i : j + ":" + i;
		}
	}

	private Potion getPotion(PotionEffect effect) {
		return Potion.potionTypes[effect.getPotionID()];
	}

	public void renderIcon(PotionEffect effect, float x, float y) {
		if (this.getPotion(effect).hasStatusIcon()) {

			Minecraft.getMinecraft().getTextureManager().bindTexture(potions);

			int statusIndex = this.getPotion(effect).getStatusIconIndex();
			// TODO: Don't initialize gui
			new Gui().drawTexturedModalRect(x, y, statusIndex % 8 * 18, 198 + statusIndex / 8 * 18, 18, 18);
		}
	}

	public String getName(PotionEffect effect) {
		return I18n.format(this.getPotion(effect).getName());
	}

	public String getStringLevel(PotionEffect effect) {
		switch (effect.getAmplifier()) {
			case 0:
				return I18n.format("enchantment.level.1");

			case 1:
				return I18n.format("enchantment.level.2");

			case 2:
				return I18n.format("enchantment.level.3");

			case 3:
				return I18n.format("enchantment.level.4");
		}

		// Add 1 because it starts at 0
		return String.valueOf(effect.getAmplifier() + 1);
	}

	public String getFullName(PotionEffect effect) {
		return this.getName(effect) + " " + this.getStringLevel(effect);
	}
}
