package com.pocolifo.pocolifoclient.mods.config;

import com.google.gson.JsonElement;

public interface ConfigurationType<T> {
	JsonElement serialize();

	void deserialize(JsonElement element);

	T getValue();

	void setValue(Object value);
}
