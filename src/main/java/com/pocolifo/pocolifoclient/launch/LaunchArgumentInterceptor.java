package com.pocolifo.pocolifoclient.launch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.pocolifo.obfuscator.annotations.Pass;
import com.pocolifo.obfuscator.annotations.PassOption;
import net.minecraft.launchwrapper.Launch;

@Pass(value = "RemapNamesPass", options = {
		@PassOption(key = "remapClassNames", value = "false")
})
public class LaunchArgumentInterceptor {
	public static List<String> arguments;

	public static void main(String[] args) {
		arguments = new LinkedList<>(Arrays.asList(args));

		Launch.main(args);
	}
}
