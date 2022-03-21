package com.pocolifo.pocolifoclient.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PocolifoClientTweaker implements ITweaker {
	private final List<String> args = new ArrayList<>();

	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
		this.args.addAll(args);

		if (gameDir != null && gameDir.exists()) {
			this.args.add("--gameDir");
			this.args.add(gameDir.getAbsolutePath());
		}

		if (assetsDir != null && assetsDir.exists()) {
			this.args.add("--assetsDir");
			this.args.add(assetsDir.getAbsolutePath());
		}

		if (profile != null) {
			this.args.add("--version");
			this.args.add(profile);
		}

		LaunchEnvironment.gameDirectory = gameDir;
		LaunchEnvironment.assetsDirectory = assetsDir;
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		MixinBootstrap.init();
		Mixins.addConfiguration("pocolifoclient.mixins.json");

		MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();
		environment.setSide(MixinEnvironment.Side.CLIENT);
		LaunchEnvironment.discoverInjectionStage();

		if (LaunchEnvironment.prodEnvironment) {
			environment.setObfuscationContext("notch");
		} else {
			LaunchEnvironment.printOutEnvironment(System.out);
		}
	}

	@Override
	public String getLaunchTarget() {
		return MixinBootstrap.getPlatform().getLaunchTarget();
	}

	@Override
	public String[] getLaunchArguments() {
		return LaunchEnvironment.optiFine ? new String[0] : this.args.toArray(new String[0]);
	}
}
