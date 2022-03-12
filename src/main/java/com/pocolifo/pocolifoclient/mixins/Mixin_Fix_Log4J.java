package com.pocolifo.pocolifoclient.mixins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.lookup.JndiLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/*
 * Yes, the repos for Minecraft have been updated, but this is just to make absolutely
 * sure that Log4Shell is patched.
 */
@Mixin(JndiLookup.class)
public class Mixin_Fix_Log4J {
	/**
	 * @author youngermax
	 * @reason Fix Log4Shell!
	 */
	@Overwrite
	public String lookup(String key) {
		return null;
	}

	/**
	 * @author youngermax
	 * @reason Fix Log4Shell!
	 */
	@Overwrite
	public String lookup(LogEvent event, String key) {
		return null;
	}
}
