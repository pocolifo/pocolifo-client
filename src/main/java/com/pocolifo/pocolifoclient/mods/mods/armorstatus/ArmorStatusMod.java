package com.pocolifo.pocolifoclient.mods.mods.armorstatus;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.pocolifo.pocolifoclient.mods.AbstractBaseRenderableMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.NumberConfigurationType;
import com.pocolifo.pocolifoclient.mods.config.types.StringConfigurationType;
import com.pocolifo.pocolifoclient.render.ClientColor;
import com.pocolifo.pocolifoclient.util.Fonts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ArmorStatusMod extends AbstractBaseRenderableMod {
	public static final int SIZE = 16;

	@ModConfiguration(value = "display_unbreakable_text", name = "Show When Unbreakable", type = BooleanConfigurationType.class)
	public boolean showUnbreakableText = true;

	@ModConfiguration(value = "unbreakable_text", name = "Unbreakable Text", type = StringConfigurationType.class)
	public String unbreakableText = "Unbreakable";

	@ModConfiguration(value = "armor_text_margin", name = "Armor Text Margin", type = NumberConfigurationType.class)
	public float armorTextMargin = 4;

	@ModConfiguration(value = "show_total_durability", name = "Show Total Durability", type = BooleanConfigurationType.class)
	public boolean showTotalDurability = true;


	public ArmorStatusMod() {
		super("Armor Status", "Displays armor you have equipped and it's durability");
	}

	@Override
	public float getHeight() {
		return SIZE * 4;
	}

	@Override
	public void render() {
		GlStateManager.popMatrix();
		float width = 0;
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;

		List<ItemStack> armor = getArmor(thePlayer);

		for (ItemStack stack : armor) {

			if (stack != null) {

				int y = (int) (this.getPosition().getRenderY() + (armor.indexOf(stack) + 1) * -SIZE + SIZE * armor.size());

				Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, (int) this.getPosition().getRenderX(), y);

				if (!stack.isItemStackDamageable()) {
					if (!this.showUnbreakableText) {
						continue;
					}

					float x = this.armorTextMargin + SIZE;
					width = Math.max(width, x + Fonts.sharp.getStringWidth(this.unbreakableText));
					Fonts.sharp.write(this.unbreakableText, this.getPosition().getRenderX() + x, y, new ClientColor(Color.WHITE));

				} else {

					int usesLeft = stack.getMaxDamage() - stack.getItemDamage();

					String s = usesLeft + (this.showTotalDurability ? "/" + stack.getMaxDamage() : "");

					float x = this.armorTextMargin + SIZE;
					width = Math.max(width, x + Fonts.sharp.getStringWidth(s));

					Fonts.sharp.write(s, this.getPosition().getRenderX() + x, y, new ClientColor(Color.WHITE));
				}
			}

			this.width = width;
		}
		GlStateManager.pushMatrix();
	}

	private List<ItemStack> getArmor(EntityPlayer player) {
		return Arrays.asList(player.inventory.armorInventory);
	}
}
