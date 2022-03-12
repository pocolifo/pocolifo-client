package com.pocolifo.pocolifoclient.mixins;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiSlot;

@Mixin(GuiSlot.class)
public abstract class Mixin_Looks_SmoothScroll_GuiSlot {
	@Shadow @Final protected int slotHeight;

	@Shadow protected float amountScrolled;
	@Shadow protected int initialClickY;
	@Shadow protected int mouseY;
	@Shadow protected float scrollMultiplier;

	private float scroll;

	/**
	 * @author youngermax
	 */
	@Inject(method = "handleMouseInput", at = @At("TAIL"))
	public void handleMouseInput(CallbackInfo ci) {
		if (this.initialClickY >= 0) {
			this.scroll -= (float)(this.mouseY - this.initialClickY) * this.scrollMultiplier;
		}

		this.scroll -= Mouse.getEventDWheel() / (float) this.slotHeight;
	}



	// TODO: Smooth scroll @ 30 fps regardless of game fps
	@Inject(method = "drawScreen", at = @At("TAIL"))
	public void smoothScroll(int i, int i1, float v, CallbackInfo ci) {
		this.amountScrolled += this.scroll;
		this.scroll *= 0.5f;
	}
}
