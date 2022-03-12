package com.pocolifo.pocolifoclient.launch;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;

@RequiredArgsConstructor
public class LaunchEnvironment {
	private static LaunchEnvironment instance;

	public final boolean optiFine;
	public final boolean prodEnvironment;
	public final File gameDirectory;
	public final File assetsDirectory;

	public static void discoverPreMixinStage(File gameDirectory, File assetsDirectory) {
		boolean hasOptiFine = classExists("optifine.OptiFineTweaker");
		boolean isInProd = !classExists("com.pocolifo.pocolifoclient.launch.IsInProduction");

		instance = new LaunchEnvironment(hasOptiFine, isInProd, gameDirectory, assetsDirectory);
	}

	private static boolean classExists(String cls) {
		try {
			Class.forName(cls);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public static LaunchEnvironment getInstance() {
		return instance;
	}

	public void printOutEnvironment(PrintStream stream) {
		for (Field field : this.getClass().getFields()) {
			field.setAccessible(true);
			String val;

			try {
				val = field.get(this).toString();
			} catch (IllegalAccessException e) {
				val = "(missing)";
			}

			stream.printf("%s: %s%n", field.getName(), val);
		}
	}
}
