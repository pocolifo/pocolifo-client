package com.pocolifo.pocolifoclient.mods;

import java.util.Collection;
import java.util.Collections;

import com.google.gson.JsonObject;

import net.minecraft.client.settings.KeyBinding;

public interface Mod {
	String getModName();

	String getDescription();

	boolean isEnabled();

	void enable();

	void disable();

	default Collection<KeyBinding> getKeybindings() {
		return Collections.emptyList();
	}

	JsonObject serialize() throws Exception;

	void deserialize(JsonObject element) throws Exception;
}
