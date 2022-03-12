package com.pocolifo.pocolifoclient.launch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.launchwrapper.Launch;

public class LaunchArgumentInterceptor {
	public static List<String> arguments;

	public static void main(String[] args) {
		arguments = new LinkedList<>(Arrays.asList(args));

		Launch.main(args);
	}
}
