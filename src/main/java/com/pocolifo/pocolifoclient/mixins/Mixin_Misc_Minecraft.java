package com.pocolifo.pocolifoclient.mixins;

import com.pocolifo.commons.OperatingSystemCommons;
import com.pocolifo.pocolifoclient.PocolifoClient;
import com.pocolifo.pocolifoclient.launch.BuildProperties;
import com.pocolifo.pocolifoclient.splash.AbstractSplashScreen;
import com.pocolifo.pocolifoclient.splash.loading.LoadingSplashScreen;
import com.pocolifo.pocolifoclient.util.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class Mixin_Misc_Minecraft {
	@Shadow protected abstract void resize(int i, int i1);

	@Shadow public int displayWidth;

	@Shadow public int displayHeight;

	@Inject(method = "startGame", at = @At("HEAD"))
	public void updateTrustStore(CallbackInfo ci) {
		// use the system trust store to our letsencrypt certificate will be trusted
		// --> we need to do this because Mojang ships an old version of java that doesn't trust letsencrypt
		// I KNOW YOU CAN REPLACE THIS WITH A SWITCH STATEMENT
		OperatingSystemCommons.OperatingSystems os = OperatingSystemCommons.getHostOperatingSystem();

		if (os == OperatingSystemCommons.OperatingSystems.WINDOWS) {
			System.setProperty("javax.net.ssl.trustStoreType", "WINDOWS-ROOT");
		} else if (os == OperatingSystemCommons.OperatingSystems.MACOS) {
			System.setProperty("javax.net.ssl.trustStoreType", "KeychainStore");
		}
	}

	@Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;drawSplashScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V", shift = At.Shift.BEFORE))
	public void loadFonts(CallbackInfo ci) {
		try {
			Fonts.loadFonts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Inject(method = "startGame", at = @At(value = "TAIL"))
	public void fixOptiFineViewport(CallbackInfo ci) {
		this.resize(this.displayWidth, this.displayHeight);
	}

	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	public void shutdown(CallbackInfo ci) {
		PocolifoClient.getInstance().shutdown();
	}

	@Redirect(
			method = "createDisplay",
			at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V")
	)
	public void setPocolifoClientTitle(String oldTitle) {
		Display.setTitle(String.format("%s %s", BuildProperties.NAME, BuildProperties.VERSION));
	}

	/**
	 * @author youngermax
	 * @reason use custom loading screen
	 *
	 * TODO: look into {@code SharedDrawable} for multithreaded goodness
	 * @see SharedDrawable
	 */
	@Overwrite
	private void drawSplashScreen(TextureManager textureManager) {
		AbstractSplashScreen splashScreen = new LoadingSplashScreen();

		splashScreen.prepare();

		while (splashScreen.keepRendering()) {
			splashScreen.render();
		}

		splashScreen.close();
	}
}
