package com.pocolifo.pocolifoclient.mods.config.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationType;

public class NumberConfigurationType implements ConfigurationType<Float> {
	private float value;

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(this.value);
	}

	@Override
	public void deserialize(JsonElement element) {
		this.value = element.getAsFloat();
	}

	@Override
	public Float getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (float) value;
	}
}
