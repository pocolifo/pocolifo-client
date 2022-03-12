package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public abstract class Mixin_Mods__OldAnimations__ChangeTransform_ItemRenderer {
	private float partialTicks;

	@Shadow @Final private Minecraft mc;

	@Shadow private float prevEquippedProgress;

	@Shadow private float equippedProgress;

	@Shadow protected abstract void rotateArroundXAndY(float v, float v1);

	@Shadow protected abstract void setLightMapFromPlayer(AbstractClientPlayer abstractClientPlayer);

	@Shadow protected abstract void rotateWithPlayerRotations(EntityPlayerSP entityPlayerSP, float v);

	@Shadow private ItemStack itemToRender;

	@Shadow protected abstract void renderItemMap(AbstractClientPlayer abstractClientPlayer, float v, float v1, float v2);

	@Shadow protected abstract void transformFirstPersonItem(float v, float v1);

	@Shadow protected abstract void performDrinking(AbstractClientPlayer abstractClientPlayer, float v);

	@Shadow protected abstract void doBlockTransformations();

	@Shadow protected abstract void doBowTransformations(float v, AbstractClientPlayer abstractClientPlayer);

	@Shadow protected abstract void doItemUsedTransformations(float v);

	@Shadow public abstract void renderItem(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType);

	@Shadow protected abstract void renderPlayerArm(AbstractClientPlayer abstractClientPlayer, float v, float v1);

	/**
	 * @author youngermax
	 * @reason Old animations
	 * Didn't want to overwrite, but this is a workaround/hack for the first release
	 * TODO: unhack this
	 */
	@Overwrite
	public void renderItemInFirstPerson(float partialTicks) {
		float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
		AbstractClientPlayer var3 = this.mc.thePlayer;
		float var4 = var3.getSwingProgress(partialTicks);

		float transformFirstPersonItemVar2 = InstanceHolder.oldAnimationsMod.isEnabled() ? var4 : 0;

		float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * partialTicks;
		float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * partialTicks;
		this.rotateArroundXAndY(var5, var6);
		this.setLightMapFromPlayer(var3);
		this.rotateWithPlayerRotations((EntityPlayerSP)var3, partialTicks);
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();
		if (this.itemToRender != null) {
			if (this.itemToRender.getItem() == Items.filled_map) {
				this.renderItemMap(var3, var5, var2, var4);
			} else if (var3.getItemInUseCount() > 0) {
				EnumAction itemUseAction = this.itemToRender.getItemUseAction();

				if (itemUseAction == EnumAction.NONE) {
					this.transformFirstPersonItem(var2, InstanceHolder.oldAnimationsMod.isEnabled() && InstanceHolder.oldAnimationsMod.oldNone ? transformFirstPersonItemVar2 : 0f);
				} else if (itemUseAction == EnumAction.EAT) {
					this.performDrinking(var3, partialTicks);
					this.transformFirstPersonItem(var2, InstanceHolder.oldAnimationsMod.isEnabled() && InstanceHolder.oldAnimationsMod.oldEating ? transformFirstPersonItemVar2 : 0f);
				} else if (itemUseAction == EnumAction.DRINK) {
					this.performDrinking(var3, partialTicks);
					this.transformFirstPersonItem(var2, InstanceHolder.oldAnimationsMod.isEnabled() && InstanceHolder.oldAnimationsMod.oldDrinking ? transformFirstPersonItemVar2 : 0f);
				} else if (itemUseAction == EnumAction.BLOCK) {
					this.transformFirstPersonItem(var2, InstanceHolder.oldAnimationsMod.isEnabled() && InstanceHolder.oldAnimationsMod.oldBlocking ? transformFirstPersonItemVar2 : 0f);
					this.doBlockTransformations();
				} else if (itemUseAction == EnumAction.BOW) {
					this.transformFirstPersonItem(var2, InstanceHolder.oldAnimationsMod.isEnabled() && InstanceHolder.oldAnimationsMod.oldBow ? transformFirstPersonItemVar2 : 0f);
					this.doBowTransformations(partialTicks, var3);
				}

			} else {
				this.doItemUsedTransformations(var4);
				this.transformFirstPersonItem(var2, var4);
			}

			this.renderItem(var3, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
		} else if (!var3.isInvisible()) {
			this.renderPlayerArm(var3, var2, var4);
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
	}
}
