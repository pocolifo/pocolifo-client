package com.pocolifo.pocolifoclient.launch;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;

@RequiredArgsConstructor
@Data
public class LaunchEnvironment {
	public static boolean optiFine;
	public static boolean prodEnvironment;
	public static File gameDirectory;
	public static File assetsDirectory;

	public static void discoverInjectionStage() {
		optiFine = classExists("optifine.OptiFineTweaker");
		prodEnvironment = !classExists("com.pocolifo.pocolifoclient.launch.IsInProduction");
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

	public static void printOutEnvironment(PrintStream stream) {
		for (Field field : LaunchEnvironment.class.getFields()) {
			field.setAccessible(true);
			String val;

			try {
				val = field.get(LaunchEnvironment.class).toString();
			} catch (IllegalAccessException e) {
				val = "(missing)";
			}

			stream.printf("%s: %s%n", field.getName(), val);
		}
	}
}
