package com.pocolifo.pocolifoclient.mods.config.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationType;

public class BooleanConfigurationType implements ConfigurationType<Boolean> {
	private boolean value;

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(this.value);
	}

	@Override
	public void deserialize(JsonElement element) {
		this.value = element.getAsBoolean();
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (boolean) value;
	}
}
