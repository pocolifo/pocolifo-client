package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import com.pocolifo.pocolifoclient.util.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;

@Mixin(RenderEntityItem.class)
public class Mixin_Mods__ItemPhysics__RenderItem__RenderEntityItem {
	private double transformY;
	private EntityItem item;

	@Inject(method = "func_177077_a", at = @At("HEAD"))
	public void setItemInfo(EntityItem item, double var2, double var3, double var4, float var5, IBakedModel model, CallbackInfoReturnable<Integer> cir) {
		this.transformY = 0.05f * model.getItemCameraTransforms().ground.scale.y + var3;
		this.item = item;

		if (!this.item.onGround) {
			this.item.rotationPitch += (Math.abs(this.item.motionX) + Math.abs(this.item.motionY) + Math.abs(this.item.motionZ)) * 0.7;
		} else {
			this.item.rotationPitch = 90f;
		}
	}

	@Redirect(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 0))
	private void itemPhysicsTranslate(float translateX, float translateY, float translateZ) {
		if (InstanceHolder.itemPhysicsMod.isEnabled()) {
			GlStateManager.translate(translateX, this.transformY, translateZ);
		} else {
			GlStateManager.translate(translateX, translateY, translateZ);
		}
	}

	@Redirect(method = "func_177077_a", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
	private void itemPhysicsRotate(float rotateDeg, float rotateX, float rotateY, float rotateZ) {
		if (InstanceHolder.itemPhysicsMod.isEnabled()) {
			if (this.item.onGround) {
				GlStateManager.rotate(90f, 1f, 0f, 0f);
				GlStateManager.rotate(this.item.hoverStart * 30, 0f, 0f, 0.25f);
			} else {
				float x = MathUtil.contain_float((float) this.item.motionX, -1f, 1f);
				float y = MathUtil.contain_float((float) this.item.motionY, -1f, 1f);
				float z = MathUtil.contain_float((float) this.item.motionZ, -1f, 1f);

				GlStateManager.rotate(this.item.rotationPitch, x, y, z);
			}
		} else {
			GlStateManager.rotate(rotateDeg, rotateX, rotateY, rotateZ);
		}
	}
}
