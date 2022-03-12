package com.pocolifo.pocolifoclient.mods.config.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pocolifo.pocolifoclient.mods.config.ConfigurationType;
import com.pocolifo.pocolifoclient.render.ClientColor;

public class ColorConfigurationType implements ConfigurationType<ClientColor> {
	private ClientColor value;

	@Override
	public JsonElement serialize() {
		JsonObject object = new JsonObject();

		object.add("r", new JsonPrimitive(this.value.red));
		object.add("g", new JsonPrimitive(this.value.green));
		object.add("b", new JsonPrimitive(this.value.blue));
		object.add("a", new JsonPrimitive(this.value.alpha));

		return object;
	}

	@Override
	public void deserialize(JsonElement element) {
		JsonObject object = element.getAsJsonObject();

		this.value = new ClientColor(object.get("r").getAsFloat(), object.get("g").getAsFloat(), object.get("b").getAsFloat(), object.get("a").getAsFloat());
	}

	@Override
	public ClientColor getValue() {
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (ClientColor) value;
	}
}
