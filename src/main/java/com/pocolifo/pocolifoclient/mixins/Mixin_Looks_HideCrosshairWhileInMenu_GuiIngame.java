package com.pocolifo.pocolifoclient.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuiIngame.class)
public class Mixin_Looks_HideCrosshairWhileInMenu_GuiIngame {
    @Shadow @Final private Minecraft mc;

    @Inject(method = "showCrosshair", at = @At("HEAD"), cancellable = true)
    public void isInGuiScreen(CallbackInfoReturnable<Boolean> cir) {
        if (this.mc.currentScreen != null) cir.setReturnValue(false);
    }
}
