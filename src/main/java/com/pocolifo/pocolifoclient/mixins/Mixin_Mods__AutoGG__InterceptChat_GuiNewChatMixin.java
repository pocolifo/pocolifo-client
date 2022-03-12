package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.pocolifoclient.mods.InstanceHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;

@Mixin(GuiNewChat.class)
public class Mixin_Mods__AutoGG__InterceptChat_GuiNewChatMixin {
	@Shadow @Final private Minecraft mc;

	@Inject(method = "printChatMessageWithOptionalDeletion", at = @At("TAIL"))
	public void onChat(IChatComponent chat, int i, CallbackInfo ci) {
		if (InstanceHolder.autoGGMod.testTriggers(chat.getUnformattedText().trim())) {
			new Thread(() -> {
				try {
					Thread.sleep((long) (InstanceHolder.autoGGMod.delay * 1000L));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				this.mc.thePlayer.sendChatMessage(InstanceHolder.autoGGMod.message);
			}).start();
		}
	}
}
