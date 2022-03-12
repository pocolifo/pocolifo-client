package com.pocolifo.pocolifoclient.mods.mods.performance;

import com.pocolifo.pocolifoclient.mods.AbstractBaseMod;
import com.pocolifo.pocolifoclient.mods.config.ModConfiguration;
import com.pocolifo.pocolifoclient.mods.config.types.BooleanConfigurationType;

public class PerformanceMod extends AbstractBaseMod {
	@ModConfiguration(value = "fast_world_loading", name = "Fast World Loading", type = BooleanConfigurationType.class)
	public boolean fastWorldLoading = true;

	public PerformanceMod() {
		super("Performance", "Optimize your game and increase FPS!");
	}
}
