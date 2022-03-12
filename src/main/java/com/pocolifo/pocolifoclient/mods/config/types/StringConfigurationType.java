package com.pocolifo.pocolifoclient.mods.config.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationType;

public class StringConfigurationType implements ConfigurationType<String> {
	private String value;

	@Override
	public JsonElement serialize() {
		return new JsonPrimitive(this.value);
	}

	@Override
	public void deserialize(JsonElement element) {
		this.value = element.getAsString();
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (String) value;
	}
}
