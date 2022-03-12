package com.pocolifo.pocolifoclient.mods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

public abstract class AbstractBaseRenderableMod extends AbstractBaseMod implements RenderableMod {
	// lombok handles position getter
	@Getter
	private final ModPosition position = new ModPosition();

	protected float width;
	protected float height;

	public AbstractBaseRenderableMod(String name, String description) {
		super(name, description);
	}

	@Override
	public float getWidth() {
		return this.width;
	}

	@Override
	public float getHeight() {
		return this.height;
	}

	@Override
	public JsonObject serialize() throws ReflectiveOperationException {
		JsonObject saveWithPosition = new JsonObject();
		JsonElement regularSaveData = super.serialize();

		saveWithPosition.add("data", regularSaveData);
		saveWithPosition.add("position", this.getPosition().serialize());

		return saveWithPosition;
	}

	@Override
	public void deserialize(JsonObject object) throws Exception {
		if (object.has("data")) {
			super.deserialize(object.get("data").getAsJsonObject());
		}

		if (object.has("position")) {
			this.getPosition().deserialize(object.get("position"));
		}
	}
}
